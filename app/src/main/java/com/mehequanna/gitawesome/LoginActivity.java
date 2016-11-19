package com.mehequanna.gitawesome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mSignInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mSignInButton) {
            mHiddenTextView.setVisibility(v.VISIBLE);
            Toast.makeText(LoginActivity.this, "Signed In!", Toast.LENGTH_SHORT).show();
        }
    }
}
