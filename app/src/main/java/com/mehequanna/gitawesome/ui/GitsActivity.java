package com.mehequanna.gitawesome.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mehequanna.gitawesome.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GitsActivity extends AppCompatActivity {
    @Bind(R.id.textView2) TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gits);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String language = "Language: " + intent.getStringExtra("language");

        mTextView.setText(language);
    }
}
