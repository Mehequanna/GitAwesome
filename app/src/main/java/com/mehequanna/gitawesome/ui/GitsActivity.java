package com.mehequanna.gitawesome.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.mehequanna.gitawesome.R;
import com.mehequanna.gitawesome.adapters.GitsListAdapter;
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
    @Bind(R.id.gitsRecyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.errorTextView) TextView mErrorTextView;
    private GitsListAdapter mAdapter;

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

                GitsActivity.this.runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        // This code show an error message if there are no repos for the recycler view to show.
                        if (mRepos.size() > 0) {
                            mAdapter = new GitsListAdapter(getApplicationContext(), mRepos);
                            mRecyclerView.setAdapter(mAdapter);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GitsActivity.this);
                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setHasFixedSize(true);
                        } else {
                            mErrorTextView.setText(R.string.errorMessage);
                        }

                    }
                });

            }
        });
    }

}
