package com.mehequanna.gitawesome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.signInButton) Button mSignInButton;
    @Bind(R.id.loginEditText) EditText mLogInEditText;
    @Bind(R.id.passwordEditText) EditText mPasswordEditText;
    @Bind(R.id.hiddenTextView) TextView mHiddenTextView;
    public static final String TAG = "log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mSignInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // This code checks that they user has filled all fields and used the correct login credentials.
        if (v == mSignInButton) {
            if (TextUtils.isEmpty(mPasswordEditText.getText().toString().trim()) && TextUtils.isEmpty(mLogInEditText.getText().toString().trim())) {
                Toast.makeText(LoginActivity.this, "Please enter a username and password", Toast.LENGTH_SHORT).show();
                mHiddenTextView.setVisibility(v.VISIBLE);
            } else if (TextUtils.isEmpty(mPasswordEditText.getText().toString().trim())) {
                Toast.makeText(LoginActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                mHiddenTextView.setVisibility(v.VISIBLE);
            } else if (TextUtils.isEmpty(mLogInEditText.getText().toString().trim())) {
                Toast.makeText(LoginActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                mHiddenTextView.setVisibility(v.VISIBLE);
            } else if (mPasswordEditText.getText().toString().trim().equals("password") && mLogInEditText.getText().toString().trim().equals("user")) {
                Toast.makeText(LoginActivity.this, "Signed In!", Toast.LENGTH_SHORT).show();
                mHiddenTextView.setVisibility(v.VISIBLE);
            } else if (!(mPasswordEditText.getText().toString().trim().equals("password") && mLogInEditText.getText().toString().trim().equals("user"))) {
                Toast.makeText(LoginActivity.this, "Incorrect Login", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
