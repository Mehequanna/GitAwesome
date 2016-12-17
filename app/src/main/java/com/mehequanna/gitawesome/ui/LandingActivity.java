package com.mehequanna.gitawesome.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mehequanna.gitawesome.Constants;
import com.mehequanna.gitawesome.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.loginButton) Button mLoginButton;
    @Bind(R.id.searchGitButton) Button mSearchGitButton;
    @Bind(R.id.aboutButton) Button mAboutButton;
    @Bind(R.id.languageEditText) EditText mLanguageEditText;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mUsername;
    private String mNonUserLanguage;
    private String mUserLanguage;
    private String mUserOverlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mLoginButton.setOnClickListener(this);
        mSearchGitButton.setOnClickListener(this);
        mAboutButton.setOnClickListener(this);

        // Presets Shared Preferences to avoid null pointer exceptions
        mUsername = mSharedPreferences.getString(Constants.PREFERENCES_USER_USERNAME_KEY, null);
        mNonUserLanguage = mSharedPreferences.getString(Constants.PREFERENCES_NONUSER_LANGUAGE_KEY, null);
        mUserLanguage = mSharedPreferences.getString(Constants.PREFERENCES_USER_LANGUAGE_KEY, null);
        mUserOverlay = mSharedPreferences.getString(Constants.PREFERENCES_OVERLAY, null);

        if (mUserOverlay == null) {
            addOverlayToSharedPreferences("false");
        }

        if (mUsername == null) {
            addUsernameToSharedPreferences("mehequanna");
        }

        if (mNonUserLanguage == null) {
            addLanguageToSharedPreferences("java");
        }

        if (mUserLanguage == null) {
            addUserLanguageToSharedPreferences("php");
        }

    }

    @Override
    public void onClick(View v) {
        if (v == mLoginButton) {
            Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        if (v == mSearchGitButton) {
            if (TextUtils.isEmpty(mLanguageEditText.getText().toString().trim())) {
                Toast.makeText(LandingActivity.this, "Using previously searched language.", Toast.LENGTH_SHORT).show();

                mLanguageEditText.setText("");

                Intent intent = new Intent(LandingActivity.this, GitsActivity.class);
                startActivity(intent);
            }

            if (!(TextUtils.isEmpty(mLanguageEditText.getText().toString().trim()))) {

                String language = mLanguageEditText.getText().toString().trim();

                if(!(language).equals("")) {
                    addLanguageToSharedPreferences(language);
                }

                mLanguageEditText.setText("");

                Intent intent = new Intent(LandingActivity.this, GitsActivity.class);
                startActivity(intent);
            }
        }

        if (v == mAboutButton) {
            Intent intent = new Intent(LandingActivity.this, AboutActivity.class);
            startActivity(intent);
        }
    }

    private void addLanguageToSharedPreferences(String language) {
        mEditor.putString(Constants.PREFERENCES_NONUSER_LANGUAGE_KEY, language).apply();
    }

    private void addUserLanguageToSharedPreferences(String language) {
        mEditor.putString(Constants.PREFERENCES_USER_LANGUAGE_KEY, language).apply();
    }

    private void addUsernameToSharedPreferences(String username) {
        mEditor.putString(Constants.PREFERENCES_USER_USERNAME_KEY, username).apply();
    }

    private void addOverlayToSharedPreferences(String overlay) {
        mEditor.putString(Constants.PREFERENCES_OVERLAY, overlay).apply();
    }
}
