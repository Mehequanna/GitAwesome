package com.mehequanna.gitawesome.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.mehequanna.gitawesome.models.Repo;

import java.util.ArrayList;

/**
 * Created by stephenemery on 12/2/16.
 */

public class GitsListAdapter extends RecyclerView.Adapter<GitsListAdapter.GitsViewHolder> {
    private ArrayList<Repo> mRepos = new ArrayList<>();
    private Context mContext;

    public GitsListAdapter(Context context, ArrayList<Repo> repos) {
        mContext = context;
        mRepos = repos;
    }

}
