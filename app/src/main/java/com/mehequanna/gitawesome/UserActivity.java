package com.mehequanna.gitawesome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity {
    @Bind(R.id.userTextView) TextView mUserTextView;
    @Bind(R.id.languagesTextView) TextView mLanguagesTextView;
    @Bind(R.id.profileImageView) ImageView mProfileImageView;
    @Bind(R.id.languageListView) ListView mLanguagesListView;
    @Bind(R.id.locationTextView) TextView mLocationTextView;
    private String[] languagesList = new String[] {"This list", "Will hold", "The users", "Saved", "Languages"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String username = "Current User: \n" + intent.getStringExtra("username");

        mUserTextView.setText(username);

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, languagesList);
        mLanguagesListView.setAdapter(adapter);
    }
}
