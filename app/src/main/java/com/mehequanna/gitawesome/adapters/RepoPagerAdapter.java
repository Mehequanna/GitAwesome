package com.mehequanna.gitawesome.adapters;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.mehequanna.gitawesome.models.Repo;
import com.mehequanna.gitawesome.ui.GitsDetailFragment;

import java.util.ArrayList;

public class RepoPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Repo> mRepos;

    public RepoPagerAdapter(FragmentManager fm, ArrayList<Repo> repos) {
        super(fm);
        mRepos = repos;
    }

    @Override
    public Fragment getItem(int position) {
        return GitsDetailFragment.newInstance(mRepos.get(position));
    }

    @Override
    public int getCount() {
        return mRepos.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mRepos.get(position).getName();
    }

}
