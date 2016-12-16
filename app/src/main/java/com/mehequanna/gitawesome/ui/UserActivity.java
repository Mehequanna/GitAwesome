package com.mehequanna.gitawesome.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mehequanna.gitawesome.Constants;
import com.mehequanna.gitawesome.R;
import com.mehequanna.gitawesome.models.GitUser;
import com.mehequanna.gitawesome.services.GitService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.userTextView) TextView mUserTextView;
    @Bind(R.id.profileImageView) ImageView mProfileImageView;
    @Bind(R.id.locationTextView) TextView mLocationTextView;
    @Bind(R.id.bioTextView) TextView mBioTextView;
    @Bind(R.id.reposTextView) TextView mReposTextView;
    @Bind(R.id.followersTextView) TextView mFollowersTextView;

    @Bind(R.id.zipEditText) EditText mZipEditText;
    @Bind(R.id.languageEditText) EditText mLanguageEditText;
    @Bind(R.id.searchGitButton) Button mSearchGitButton;
    @Bind(R.id.searchMeetupButton) Button mSearchMeetupButton;
    @Bind(R.id.savedGithubButton) Button mSavedGithubButton;
    @Bind(R.id.savedMeetupsButton) Button mSavedMeetupsButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mUserZip;
    private String mUsername;

    Context context;

    public ArrayList<GitUser> mUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        context = this;

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    String lowerUsername = user.getDisplayName().substring(1);
                    mUsername = user.getDisplayName().substring(0, 1).toUpperCase() + lowerUsername;
                    Log.d("Firebase Username", mUsername);

                    getSupportActionBar().setTitle(mUsername + " is Awesome!");

                    findUser(mUsername);
                } else {
                    getSupportActionBar().setTitle("Who are you?");
                }
            }
        };

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserZip = mSharedPreferences.getString(Constants.PREFERENCES_USER_ZIP_KEY, null);
        Log.d("Shared Pref Zip", mUserZip);

        mSearchGitButton.setOnClickListener(this);
        mSearchMeetupButton.setOnClickListener(this);
        mSavedGithubButton.setOnClickListener(this);
        mSavedMeetupsButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mSearchGitButton) {
            if (TextUtils.isEmpty(mLanguageEditText.getText().toString().trim())) {
                Toast.makeText(UserActivity.this, "Using previously searched language.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(UserActivity.this, GitsActivity.class);
                startActivity(intent);
            }

            if (!(TextUtils.isEmpty(mLanguageEditText.getText().toString().trim()))) {

                if (!(TextUtils.isEmpty(mZipEditText.getText().toString().trim()))) {
                    Toast.makeText(UserActivity.this, "No zip needed for git searches.", Toast.LENGTH_SHORT).show();
                }

                String language = mLanguageEditText.getText().toString().trim();

                if(!(language).equals("")) {
                    addLanguageToSharedPreferences(language);
                }

                Intent intent = new Intent(UserActivity.this, GitsActivity.class);
                startActivity(intent);
            }
        }

        if (v == mSavedGithubButton) {
            Intent intent = new Intent(UserActivity.this, SavedRepoListActivity.class);
            startActivity(intent);
        }

        if (v == mSavedMeetupsButton) {
            Toast.makeText(UserActivity.this, "Saved Meetup Function Coming Soon!", Toast.LENGTH_LONG).show();
        }

        if (v == mSearchMeetupButton) {
            Toast.makeText(UserActivity.this, "Search Meetup Function Coming Soon!", Toast.LENGTH_LONG).show();
        }
    }

    private void addLanguageToSharedPreferences(String language) {
        mEditor.putString(Constants.PREFERENCES_USER_LANGUAGE_KEY, language).apply();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(UserActivity.this, LandingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void findUser(String username) {
        final GitService gitService = new GitService();
        gitService.findUserInfo(username, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mUsers = gitService.processUserResult(response);

                UserActivity.this.runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        // This code shows an error message if there are no repos for the recycler view to show.
                        if (mUsers.size() > 0) {
                            GitUser currentUser = mUsers.get(0);

                            mUserTextView.setText(currentUser.getLogin());

                            // avatar
                            Picasso.with(context).load(currentUser.getAvatar_url()).resize(100,100).into(mProfileImageView);

                            mLocationTextView.setText(currentUser.getLocation());
                            mBioTextView.setText(currentUser.getBio());

                            String publicRepos = "Repos: " + currentUser.getPublic_repos();
                            mReposTextView.setText(publicRepos);

                            String followers = "Followers: " + currentUser.getFollowers();
                            mFollowersTextView.setText(followers);

                        } else {
                            Toast.makeText(UserActivity.this, "No Github user by that name.", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
    }
}
