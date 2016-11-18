package com.mehequanna.gitawesome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.loginButton) Button mLoginButton;
    @Bind(R.id.searchButton) Button mSearchButton;
    @Bind(R.id.languageEditText) EditText mLanguageEditText;
    @Bind(R.id.zipEditText) EditText mZipEditText;
    public static final String TAG = "log";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);

        mLoginButton.setOnClickListener(this);
        mSearchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mLoginButton) {
            Log.i(TAG, "Login button Clicked");
        } else if (v == mSearchButton) {
            Log.i(TAG, "Search button Clicked");
        }
    }
}
