package com.mehequanna.gitawesome.models;

import org.parceler.Parcel;

@Parcel
public class Repo {
    private String name;
    private String description;
    private String avatar;
    private String website;
    private String stargazers;
    private String createdAt;
    private String updatedAt;
    private String pushId;

    public Repo() {}

    public Repo(String name, String description, String avatar, String website, String stargazers, String createdAt, String updatedAt) {
        this.name = name;
        this.description = description;
        this.avatar = avatar;
        this.website = website;
        this.stargazers = stargazers;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getWebsite() {
        return website;
    }

    public String getStargazers() {
        return stargazers;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getPushId() {
        return pushId;
    }
}
