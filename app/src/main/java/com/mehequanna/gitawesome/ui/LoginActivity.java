package com.mehequanna.gitawesome.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.mehequanna.gitawesome.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.signInButton) Button mSignInButton;
    @Bind(R.id.loginEditText) EditText mLogInEditText;
    @Bind(R.id.passwordEditText) EditText mPasswordEditText;
    @Bind(R.id.hiddenTextView) TextView mHiddenTextView;
    @Bind(R.id.registerTextView) TextView mRegisterTextView;
    public static final String TAG = LoginActivity.class.getSimpleName();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mSignInButton.setOnClickListener(this);
        mRegisterTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mRegisterTextView) {
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            startActivity(intent);
            finish();
        }
        if (v == mSignInButton) {
            loginFormValidation();
        }
    }

    private void loginFormValidation() {
        String email = mLogInEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        if (email.equals("")) {
            mLogInEditText.setError("Please enter your email");
            return;
        }
        if (password.equals("")) {
            mPasswordEditText.setError("Password cannot be blank");
            return;
        }
    }
}
