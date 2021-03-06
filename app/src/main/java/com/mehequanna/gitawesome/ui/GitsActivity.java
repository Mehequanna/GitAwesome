package com.mehequanna.gitawesome.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.mehequanna.gitawesome.Constants;
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

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentLanguage;

    public ArrayList<Repo> mRepos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gits);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentLanguage = mSharedPreferences.getString(Constants.PREFERENCES_NONUSER_LANGUAGE_KEY, null);
        if (mRecentLanguage != null) {
            getRepos(mRecentLanguage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                addLanguageToSharedPreferences(query);
                getRepos(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
                        // This code shows an error message if there are no repos for the recycler view to show.
                        if (mRepos.size() > 0) {
                            mAdapter = new GitsListAdapter(getApplicationContext(), mRepos);
                            mRecyclerView.setAdapter(mAdapter);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GitsActivity.this);
                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setHasFixedSize(true);
                        } else {
                            Toast.makeText(GitsActivity.this, R.string.errorMessage, Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });
    }

    private void addLanguageToSharedPreferences(String language) {
        mEditor.putString(Constants.PREFERENCES_NONUSER_LANGUAGE_KEY, language).apply();
    }

}
