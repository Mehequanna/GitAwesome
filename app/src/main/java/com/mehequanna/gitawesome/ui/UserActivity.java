package com.mehequanna.gitawesome.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.mehequanna.gitawesome.Constants;
import com.mehequanna.gitawesome.R;
import com.mehequanna.gitawesome.models.GitUser;
import com.mehequanna.gitawesome.services.GitService;
import com.mehequanna.gitawesome.util.DetectGestures;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "UserActivity";
    @Bind(R.id.userTextView) TextView mUserTextView;
    @Bind(R.id.profileImageView) ImageView mProfileImageView;
    @Bind(R.id.locationTextView) TextView mLocationTextView;
    @Bind(R.id.bioTextView) TextView mBioTextView;
    @Bind(R.id.reposTextView) TextView mReposTextView;
    @Bind(R.id.followersTextView) TextView mFollowersTextView;

    //Search/Button Binds
    @Bind(R.id.languageEditText) EditText mLanguageEditText;
    @Bind(R.id.searchGitButton) Button mSearchGitButton;
    @Bind(R.id.savedGithubButton) Button mSavedGithubButton;

    //Overlay Binds
    @Bind(R.id.overlayCheckbox) CheckBox mOverlayCheckbox;
    @Bind(R.id.overlayDismissTextView) TextView mOverlayDismissTextView;
    @Bind(R.id.overlayMoreImageView) ImageView mOverlayMoreImageView;
    @Bind(R.id.overlayMoreTextView) TextView mOverlayMoreTextView;
    @Bind(R.id.overlayPictureTextView) TextView mOverlayPictureTextView;
    @Bind(R.id.overlaySearchImageView) ImageView mOverlaySearchImageView;
    @Bind(R.id.overlaySearchTextView) TextView mOverlaySearchTextView;
    @Bind(R.id.overlayTextView) TextView mOverlayTextView;

    private String mNoOverlay;
    private String mSharedOverlay;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mUsername;

    Context context;

    public ArrayList<GitUser> mUsers = new ArrayList<>();

    private GestureDetector mOverlayGestureDetector;
    private GestureDetector mPictureGestureDetector;

    MediaPlayer wee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        context = this;
        wee = MediaPlayer.create(this, R.raw.wee);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedOverlay = mSharedPreferences.getString(Constants.PREFERENCES_OVERLAY, null);

        if (mSharedOverlay.equals("true")) {
            mOverlayTextView.setVisibility(View.INVISIBLE);
            mOverlayCheckbox.setVisibility(View.INVISIBLE);
            mOverlayDismissTextView.setVisibility(View.INVISIBLE);
            mOverlayMoreImageView.setVisibility(View.INVISIBLE);
            mOverlayMoreTextView.setVisibility(View.INVISIBLE);
            mOverlayPictureTextView.setVisibility(View.INVISIBLE);
            mOverlaySearchImageView.setVisibility(View.INVISIBLE);
            mOverlaySearchTextView.setVisibility(View.INVISIBLE);
        }

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    mUsername = user.getDisplayName();

                    getSupportActionBar().setTitle(mUsername + " is Awesome!");

                    findUser(mUsername);
                } else {
                    getSupportActionBar().setTitle("Who are you?");
                }
            }
        };

        DetectGestures pictureGestureDetector = new DetectGestures(){
            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mUsers.get(0).getHtml_url()));
                startActivity(webIntent);
                wee.start();
                return true;
            }
        };

        DetectGestures overlayGestureDetector = new DetectGestures() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                mOverlayTextView.setVisibility(View.INVISIBLE);
                mOverlayCheckbox.setVisibility(View.INVISIBLE);
                mOverlayDismissTextView.setVisibility(View.INVISIBLE);
                mOverlayMoreImageView.setVisibility(View.INVISIBLE);
                mOverlayMoreTextView.setVisibility(View.INVISIBLE);
                mOverlayPictureTextView.setVisibility(View.INVISIBLE);
                mOverlaySearchImageView.setVisibility(View.INVISIBLE);
                mOverlaySearchTextView.setVisibility(View.INVISIBLE);
                return true;
            }
        };

        mOverlayGestureDetector = new GestureDetector(this, overlayGestureDetector);
        mOverlayTextView.setOnTouchListener(this);
        mPictureGestureDetector = new GestureDetector(this, pictureGestureDetector);
        mProfileImageView.setOnTouchListener(this);

        mSearchGitButton.setOnClickListener(this);
        mSavedGithubButton.setOnClickListener(this);
        mOverlayCheckbox.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view == mOverlayTextView) {
            mOverlayGestureDetector.onTouchEvent(motionEvent);
            return true;
        }

        if (view == mProfileImageView) {
            mPictureGestureDetector.onTouchEvent(motionEvent);
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == mSearchGitButton) {
            if (TextUtils.isEmpty(mLanguageEditText.getText().toString().trim())) {
                Toast.makeText(UserActivity.this, "Using previously searched language.", Toast.LENGTH_SHORT).show();

                mLanguageEditText.setText("");

                Intent intent = new Intent(UserActivity.this, GitsActivity.class);
                startActivity(intent);
            }

            if (!(TextUtils.isEmpty(mLanguageEditText.getText().toString().trim()))) {

                String language = mLanguageEditText.getText().toString().trim();

                if(!(language).equals("")) {
                    addLanguageToSharedPreferences(language);
                }

                mLanguageEditText.setText("");

                Intent intent = new Intent(UserActivity.this, GitsActivity.class);
                startActivity(intent);
            }
        }

        if (v == mSavedGithubButton) {
            Intent intent = new Intent(UserActivity.this, SavedRepoListActivity.class);
            startActivity(intent);
        }

        if (v == mOverlayCheckbox) {
            final CheckBox checkBox = (CheckBox) findViewById(R.id.overlayCheckbox);
            if (checkBox.isChecked()) {
                mNoOverlay = "true";
                addOverlayToSharedPreferences(mNoOverlay);
            }
            if (!checkBox.isChecked()) {
                mNoOverlay = "false";
                addOverlayToSharedPreferences(mNoOverlay);
            }
        }

    }

    private void addLanguageToSharedPreferences(String language) {
        mEditor.putString(Constants.PREFERENCES_USER_LANGUAGE_KEY, language).apply();
    }

    private void addOverlayToSharedPreferences(String overlay) {
        mEditor.putString(Constants.PREFERENCES_OVERLAY, overlay).apply();
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
        ButterKnife.bind(this);

        MenuItem menuItem = menu.findItem(R.id.action_change_user);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                updateUsernameFirebase(query);
                findUser(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        if (id == R.id.action_help) {
            helpOverlay();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void helpOverlay() {
        mOverlayTextView.setVisibility(View.VISIBLE);
        mOverlayCheckbox.setVisibility(View.VISIBLE);
        mOverlayDismissTextView.setVisibility(View.VISIBLE);
        mOverlayMoreImageView.setVisibility(View.VISIBLE);
        mOverlayMoreTextView.setVisibility(View.VISIBLE);
        mOverlayPictureTextView.setVisibility(View.VISIBLE);
        mOverlaySearchImageView.setVisibility(View.VISIBLE);
        mOverlaySearchTextView.setVisibility(View.VISIBLE);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(UserActivity.this, LandingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void updateUsernameFirebase(String query) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(query)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserActivity.this, "User profile updated.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                            Picasso.with(context).load(currentUser.getAvatar_url()).resize(250,250).into(mProfileImageView);

                            if (!currentUser.getLocation().equals("null")) {
                                mLocationTextView.setText(currentUser.getLocation());
                            }

                            if (!currentUser.getBio().equals("null")) {
                                mBioTextView.setText(currentUser.getBio());
                            }

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
