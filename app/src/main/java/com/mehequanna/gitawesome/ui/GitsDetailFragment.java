package com.mehequanna.gitawesome.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mehequanna.gitawesome.Constants;
import com.mehequanna.gitawesome.R;
import com.mehequanna.gitawesome.models.Repo;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GitsDetailFragment extends Fragment implements View.OnClickListener {
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

        if (!mRepo.getDescription().equals("null")) {
            mDescriptionTextView.setText(mRepo.getDescription());
        }

        mCreatedTextView.setText(newCreated);
        mUpdatedTextView.setText(newUpdated);
        mStargazersTextView.setText(newStargazers);
        mGithubTextView.setText(mRepo.getWebsite());

        mGithubTextView.setOnClickListener(this);

        mSaveGitButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mGithubTextView) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mRepo.getWebsite()));
            startActivity(webIntent);
        }

        if (v == mSaveGitButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user != null) {
                String uid = user.getUid();

                DatabaseReference repoRef = FirebaseDatabase
                        .getInstance()
                        .getReference(Constants.FIREBASE_CHILD_REPOS)
                        .child(uid);

                DatabaseReference pushRef = repoRef.push();
                String pushId = pushRef.getKey();
                mRepo.setPushId(pushId);
                pushRef.setValue(mRepo);

//                repoRef.push().setValue(mRepo);



                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
            } else if (user == null) {
                Toast.makeText(getContext(), "You must be logged in to do that!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
