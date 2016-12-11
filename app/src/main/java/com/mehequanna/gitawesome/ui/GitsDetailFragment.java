package com.mehequanna.gitawesome.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mehequanna.gitawesome.R;
import com.mehequanna.gitawesome.models.Repo;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GitsDetailFragment extends Fragment {
    @Bind(R.id.gitsImageView) ImageView mGitsImageView;
    @Bind(R.id.gitsNameTextView) TextView mGitsNameTextView;
    @Bind(R.id.descriptionTextView) TextView mDescriptionTextView;
    @Bind(R.id.createdTextView) TextView mCreatedTextView;
    @Bind(R.id.updatedTextView) TextView mUpdatedTextView;
    @Bind(R.id.stargazersTextView) TextView mStargazersTextView;
    @Bind(R.id.githubTextView) TextView mGithubTextView;
    @Bind(R.id.saveGitButton) Button mSaveGitButton;

    private Repo mRepo;

    public static GitsDetailFragment newInstance(Repo repo) {
        GitsDetailFragment gitsDetailFragment = new GitsDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("repo", Parcels.wrap(repo));
        gitsDetailFragment.setArguments(args);
        return gitsDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mRepo = Parcels.unwrap(getArguments().getParcelable("repo"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gits_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext()).load(mRepo.getAvatar()).into(mGitsImageView);

        String newStargazers = mRepo.getStargazers() + " stargazers!";
        String newCreated = TextUtils.substring(mRepo.getCreatedAt(), 0, 10);
        String newUpdated = TextUtils.substring(mRepo.getUpdatedAt(), 0, 10);

        mGitsNameTextView.setText(mRepo.getName());
        mDescriptionTextView.setText(mRepo.getDescription());
        mCreatedTextView.setText(newCreated);
        mUpdatedTextView.setText(newUpdated);
        mStargazersTextView.setText(newStargazers);
        mGithubTextView.setText(mRepo.getWebsite());

        return view;
    }
}
