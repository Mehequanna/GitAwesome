package com.mehequanna.gitawesome.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mehequanna.gitawesome.R;
import com.mehequanna.gitawesome.adapters.RepoPagerAdapter;
import com.mehequanna.gitawesome.models.Repo;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GitsDetailActivity extends AppCompatActivity {
    @Bind(R.id.gitsViewPager) ViewPager mGitsViewPager;
    private RepoPagerAdapter adapterViewPager;
    ArrayList<Repo> mRepos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gits_detail);
        ButterKnife.bind(this);

        mRepos = Parcels.unwrap(getIntent().getParcelableExtra("repos"));
        Log.d("log", "onCreate: " + mRepos.get(0).getDescription());
        int startPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new RepoPagerAdapter(getSupportFragmentManager(), mRepos);
        mGitsViewPager.setAdapter(adapterViewPager);
        mGitsViewPager.setCurrentItem(startPosition);
    }
}
