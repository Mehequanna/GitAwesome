package com.mehequanna.gitawesome.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehequanna.gitawesome.Constants;
import com.mehequanna.gitawesome.R;
import com.mehequanna.gitawesome.models.Repo;
import com.mehequanna.gitawesome.ui.GitsDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseRepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mView;
    Context mContext;

    public FirebaseRepoViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindGits(Repo repo) {
        ImageView repoImageView = (ImageView) mView.findViewById(R.id.reposImageView);
        TextView repoNameTextView = (TextView) mView.findViewById(R.id.repoNameTextView);
        TextView repoCreatedTextView = (TextView) mView.findViewById(R.id.repoCreatedTextView);
        TextView repoUpdatedTextView = (TextView) mView.findViewById(R.id.repoUpdatedTextView);
        TextView repoStarsTextView = (TextView) mView.findViewById(R.id.repoStarsTextView);

        Picasso.with(mContext).load(repo.getAvatar())
                .resize(100,100)
                .into(repoImageView);

        repoNameTextView.setText(repo.getName());
        repoCreatedTextView.setText(repo.getCreatedAt());
        String createdAt = TextUtils.substring(repo.getCreatedAt(), 0, 10);
        repoUpdatedTextView.setText("Created: " + createdAt);
        repoStarsTextView.setText("Stars: " + repo.getStargazers());
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Repo> repos = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_REPOS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    repos.add(snapshot.getValue(Repo.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, GitsDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("repos", Parcels.wrap(repos));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
