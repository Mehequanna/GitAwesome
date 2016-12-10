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
    @Bind(R.id.searchMeetupButton) Button mSearchMeetupButton;
    @Bind(R.id.aboutButton) Button mAboutButton;
    @Bind(R.id.languageEditText) EditText mLanguageEditText;
    @Bind(R.id.zipEditText) EditText mZipEditText;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mLoginButton.setOnClickListener(this);
        mSearchMeetupButton.setOnClickListener(this);
        mSearchGitButton.setOnClickListener(this);
        mAboutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mLoginButton) {
            Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        if (v == mSearchMeetupButton) {
            Toast.makeText(LandingActivity.this, "Meetup Search Function Coming Soon!", Toast.LENGTH_LONG).show();
        }

        if (v == mSearchGitButton) {
            if (TextUtils.isEmpty(mLanguageEditText.getText().toString().trim())) {
                Toast.makeText(LandingActivity.this, "Please enter a language.", Toast.LENGTH_LONG).show();
            } else if (!(TextUtils.isEmpty(mLanguageEditText.getText().toString().trim()))) {
                if (!(TextUtils.isEmpty(mZipEditText.getText().toString().trim()))) {
                    Toast.makeText(LandingActivity.this, "No zip needed for git searches.", Toast.LENGTH_SHORT).show();
                }
                String language = mLanguageEditText.getText().toString().trim();
                addLanguageToSharedPreferences(language);
                Intent intent = new Intent(LandingActivity.this, GitsActivity.class);
                intent.putExtra("language", language);
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

//    For use when meetup API is added
//    private void addZipToSharedPreferences(String zip) {
//        mEditor.putString(Constants.PREFERENCES_NONUSER_ZIP_KEY, zip).apply();
//    }
}
