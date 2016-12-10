package com.mehequanna.gitawesome.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.userTextView) TextView mUserTextView;
    @Bind(R.id.profileImageView) ImageView mProfileImageView;
    @Bind(R.id.locationTextView) TextView mLocationTextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle(user.getDisplayName() + " is Awesome!");
                } else {
                    getSupportActionBar().setTitle("Who are you?");
                }
            }
        };

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUsername = mSharedPreferences.getString(Constants.PREFERENCES_USER_USERNAME_KEY, null);
        Log.d("Shared Pref Username", mUsername);
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
            Toast.makeText(UserActivity.this, "Saved Github Search Function Coming Soon!", Toast.LENGTH_LONG).show();
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
}
