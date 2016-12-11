package com.mehequanna.gitawesome.models;

import org.parceler.Parcel;

@Parcel
public class Repo {
    private String mName;
    private String mDescription;
    private String mAvatar;
    private String mWebsite;
    private String mStargazers;
    private String mCreatedAt;
    private String mUpdatedAt;

    public Repo() {}

    public Repo(String mName, String mDescription, String mAvatar, String mWebsite, String mStargazers, String mCreatedAt, String mUpdatedAt) {
        this.mName = mName;
        this.mDescription = mDescription;
        this.mAvatar = mAvatar;
        this.mWebsite = mWebsite;
        this.mStargazers = mStargazers;
        this.mCreatedAt = mCreatedAt;
        this.mUpdatedAt = mUpdatedAt;
    }


    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public String getStargazers() {
        return mStargazers;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }
}
