package com.mehequanna.gitawesome.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    public static final String TAG = "log";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);

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
        } else if (v == mSearchMeetupButton) {
            Toast.makeText(LandingActivity.this, "Meetup Search Function Coming Soon!", Toast.LENGTH_LONG).show();
        } else if (v == mSearchGitButton) {
            Toast.makeText(LandingActivity.this, "Git Search Function Coming Soon!", Toast.LENGTH_LONG).show();
        } else if (v == mAboutButton) {
            Toast.makeText(LandingActivity.this, "About Page Coming Soon!", Toast.LENGTH_LONG).show();
        }
    }
}
