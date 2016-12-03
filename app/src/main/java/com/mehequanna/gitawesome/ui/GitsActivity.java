package com.mehequanna.gitawesome.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.mehequanna.gitawesome.R;
import com.mehequanna.gitawesome.models.Repo;
import com.mehequanna.gitawesome.services.GitService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GitsActivity extends AppCompatActivity {
//    @Bind(R.id.textView2) TextView mTextView;
    public ArrayList<Repo> mRepos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gits);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String language = intent.getStringExtra("language");

        getRepos(language);
    }

    private void getRepos(String language) {
        final GitService gitService = new GitService();
        gitService.findLanguageRepos(language, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mRepos = gitService.processResults(response);

            }
        });
    }

}
