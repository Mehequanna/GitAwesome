package com.mehequanna.gitawesome.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.mehequanna.gitawesome.Constants;
import com.mehequanna.gitawesome.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = CreateAccountActivity.class.getSimpleName();

    @Bind(R.id.createUserButton) Button mCreateUserButton;
    @Bind(R.id.loginTextView) TextView mLoginTextView;
    @Bind(R.id.confirmPasswordEditText) EditText mConfirmPassword;
    @Bind(R.id.passwordEditText) EditText mPasswordEditText;
    @Bind(R.id.emailEditText) EditText mEmailEditText;
    @Bind(R.id.zipEditText) EditText mZipEditText;
    @Bind(R.id.usernameEditText) EditText mUsernameEditText;
    @Bind(R.id.nameEditText) EditText mNameEditText;

    private String mName;
    private String mUsername;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private ProgressDialog mAuthProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        createAuthStateListener();
        createAuthProgressDialog();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mLoginTextView.setOnClickListener(this);
        mCreateUserButton.setOnClickListener(this);
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
    public void onClick(View view) {
        if (view == mLoginTextView) {
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        if (view == mCreateUserButton) {
            createNewUser();
        }
    }

    private void createNewUser() {
        mName = mNameEditText.getText().toString().trim();
        final String email = mEmailEditText.getText().toString().trim();
        String zip = mZipEditText.getText().toString().trim();
        addUserZipToSharedPreferences(zip);

        mUsername = mUsernameEditText.getText().toString().trim();
//        addUsernameToSharedPreferences(mUsername);

        String password = mPasswordEditText.getText().toString().trim();
        String confirmPassword = mConfirmPassword.getText().toString().trim();

        boolean validEmail = isValidEmail(email);
        boolean validName = isValidName(mName);
        boolean validZip = isValidZip(zip);
        boolean validUsername = isValidUsername(mUsername);
        boolean validPassword = isValidPassword(password, confirmPassword);
        if(!validEmail || !validName || !validZip || !validUsername || !validPassword) return;

        mAuthProgressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mAuthProgressDialog.dismiss();

                        if (task.isSuccessful()) {
                            createFirebaseUserProfile(task.getResult().getUser());
                            Log.d(TAG, "Authentication Successful");
                        } else {
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean isValidEmail(String email) {
        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            mEmailEditText.setError("Please enter a valid email address.");
            return false;
        }
        return isGoodEmail;
    }

    private boolean isValidName(String name) {
        if (name.equals("")) {
            mNameEditText.setError("Please enter your name.");
            return false;
        }
        return true;
    }

    private boolean isValidUsername(String username) {
        if (username.equals("")) {
            mUsernameEditText.setError("Please enter your github username.");
            return false;
        }
        return true;
    }

    private boolean isValidZip(String zip) {
        if (zip.equals("")) {
            mZipEditText.setError("Please enter your zip code.");
            return false;
        }
        if (!zip.matches("^\\d{5}(?:[-\\s]\\d{4})?$")) {
            mZipEditText.setError("Please enter a valid zip code.");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password, String confirmPassword) {
        if (password.length() < 6) {
            mPasswordEditText.setError("Please create a password containing at least 6 characters");
            return false;
        } else if (!password.equals(confirmPassword)) {
            mPasswordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    private void createAuthStateListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(CreateAccountActivity.this, UserActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }

        };
    }

    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle(getString(R.string.create_dialog));
        mAuthProgressDialog.setMessage(getString(R.string.create_dialog2));
        mAuthProgressDialog.setCancelable(false);
    }

    private void createFirebaseUserProfile(final FirebaseUser user) {

        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(mUsername)
                .build();

        user.updateProfile(addProfileName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, user.getDisplayName());
                        }
                    }

                });
    }

    private void addUserZipToSharedPreferences(String zip) {
        mEditor.putString(Constants.PREFERENCES_USER_ZIP_KEY, zip).apply();
    }

    private void addUsernameToSharedPreferences(String username) {
        mEditor.putString(Constants.PREFERENCES_USER_USERNAME_KEY, username).apply();
    }
}
