package com.mehequanna.gitawesome.models;

public class GitUser {
    private String login;
    private String avatar_url;
    private String html_url;
    private String location;
    private String bio;
    private String public_repos;
    private String followers;


    public GitUser(String login, String avatar_url, String html_url, String location, String bio, String public_repos, String followers) {
        this.login = login;
        this.avatar_url = avatar_url;
        this.html_url = html_url;
        this.location = location;
        this.bio = bio;
        this.public_repos = public_repos;
        this.followers = followers;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getLocation() {
        return location;
    }

    public String getBio() {
        return bio;
    }

    public String getPublic_repos() {
        return public_repos;
    }

    public String getFollowers() {
        return followers;
    }

    @Override
    public String toString() {
        return "GitUser{" +
                "login='" + login + '\'' +
                ", followers='" + followers + '\'' +
                '}';
    }
}
