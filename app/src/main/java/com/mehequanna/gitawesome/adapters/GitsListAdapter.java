package com.mehequanna.gitawesome.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mehequanna.gitawesome.R;
import com.mehequanna.gitawesome.models.Repo;
import com.mehequanna.gitawesome.ui.GitsDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        @Bind(R.id.repoDescriptionTextView) TextView mRepoDescriptionTextView;
        @Bind(R.id.repoCreatedTextView) TextView mRepoCreatedTextView;
        @Bind(R.id.repoStarsTextView) TextView mRepoStarsTextView;

        private Context mContext;

        public GitsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindGits(Repo repo) {
            Picasso.with(mContext).load(repo.getAvatar()).resize(150,150).into(mReposImageView);
            mRepoNameTextView.setText(repo.getName());

            if (!repo.getDescription().equals("null")) {
                mRepoDescriptionTextView.setText(repo.getDescription());
            }

            String createdAt = TextUtils.substring(repo.getCreatedAt(), 0, 10);
            mRepoCreatedTextView.setText("Created: " + createdAt);
            mRepoStarsTextView.setText("Stars: " + repo.getStargazers());
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, GitsDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("repos", Parcels.wrap(mRepos));
            mContext.startActivity(intent);
        }
    }
}
