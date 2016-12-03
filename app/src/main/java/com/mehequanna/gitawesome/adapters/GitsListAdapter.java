package com.mehequanna.gitawesome.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mehequanna.gitawesome.R;
import com.mehequanna.gitawesome.models.Repo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    @Override
    public GitsListAdapter.GitsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gits_list_item, parent, false);
        GitsViewHolder viewHolder = new GitsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GitsListAdapter.GitsViewHolder holder, int position) {
        holder.bindGits(mRepos.get(position));
    }

    @Override
    public int getItemCount() {
        return mRepos.size();
    }

    public class GitsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.reposImageView) ImageView mReposImageView;
        @Bind(R.id.repoNameTextView) TextView mRepoNameTextView;
        @Bind(R.id.repoCreatedTextView) TextView mRepoCreatedTextView;
        @Bind(R.id.repoUpdatedTextView) TextView mRepoUpdatedTextView;
        @Bind(R.id.repoStarsTextView) TextView mRepoStarsTextView;

        private Context mContext;

        public GitsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindGits(Repo repo) {
            Picasso.with(mContext).load(repo.getAvatar()).resize(100,100).into(mReposImageView);
            mRepoNameTextView.setText(repo.getName());
            mRepoCreatedTextView.setText("Created: " + repo.getCreatedAt());
            mRepoUpdatedTextView.setText("Created: " + repo.getUpdatedAt());
            mRepoStarsTextView.setText("Stars: " + repo.getStargazers());
        }

        @Override
        public void onClick(View v) {
            Log.d("click listener", "working!");
            int itemPosition = getLayoutPosition();
//            Toast.makeText(mContext, mRepos.get(itemPosition).getName(), Toast.LENGTH_SHORT).show();
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mRepos.get(itemPosition).getWebsite()));
                mContext.startActivity(webIntent);
        }
    }
}
