package com.mehequanna.gitawesome.models;

import org.parceler.Parcel;

@Parcel
public class Repo {
    private String Name;
    private String Description;
    private String Avatar;
    private String Website;
    private String Stargazers;
    private String CreatedAt;
    private String UpdatedAt;

    public Repo() {}

    public Repo(String Name, String Description, String Avatar, String Website, String Stargazers, String CreatedAt, String UpdatedAt) {
        this.Name = Name;
        this.Description = Description;
        this.Avatar = Avatar;
        this.Website = Website;
        this.Stargazers = Stargazers;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
    }


    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getAvatar() {
        return Avatar;
    }

    public String getWebsite() {
        return Website;
    }

    public String getStargazers() {
        return Stargazers;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }
}
