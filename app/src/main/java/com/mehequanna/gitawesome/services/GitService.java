package com.mehequanna.gitawesome.services;

import android.util.Log;

import com.mehequanna.gitawesome.Constants;
import com.mehequanna.gitawesome.models.GitUser;
import com.mehequanna.gitawesome.models.Repo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GitService {
    public static void findLanguageRepos(String language, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        String searchLanguage = "language:" + language;

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GIT_LANGUAGE_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.Q_QUERY_PARAMETER, searchLanguage);
        urlBuilder.addQueryParameter(Constants.SORT_QUERY_PARAMETER, "stars");
        urlBuilder.addQueryParameter(Constants.ORDER_QUERY_PARAMETER, "desc");
        urlBuilder.addQueryParameter(Constants.GIT_API_QUERY, Constants.GIT_API_KEY);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Repo> processResults(Response response) {
        ArrayList<Repo> languageRepos = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject gitJSON = new JSONObject(jsonData);
                JSONArray languageJSON = gitJSON.getJSONArray("items");

                for (int i= 0; i < languageJSON.length(); i++) {
                    JSONObject languageResults = languageJSON.getJSONObject(i);

                    String name = languageResults.getString("name");
                    String description = languageResults.getString("description");
                    String avatar = languageResults.getJSONObject("owner").getString("avatar_url");
                    String html = languageResults.getString("html_url");
                    String stargazers = languageResults.getString("stargazers_count");
                    String created = languageResults.getString("created_at");
                    String updated = languageResults.getString("updated_at");

                    Repo repo = new Repo(name, description, avatar, html, stargazers, created, updated);
                    languageRepos.add(repo);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return languageRepos;

    }

    public static void findUserInfo(String username, Callback callback) {
        OkHttpClient client2 = new OkHttpClient.Builder().build();

        String firebaseUsername = username;

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GIT_BASE_URL + firebaseUsername).newBuilder();
        urlBuilder.addQueryParameter(Constants.GIT_API_QUERY, Constants.GIT_API_KEY);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client2.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<GitUser> processUserResult(Response response) {
        ArrayList<GitUser> gitUsers = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject userJSON = new JSONObject(jsonData);
//                JSONObject languageResults = languageJSON.getJSONObject(i);

                String login = userJSON.getString("login");
                Log.d("logs", "processUserResult: login: " + login);
                String avatar_url = userJSON.getString("avatar_url");
                Log.d("logs", "processUserResult: avatar: " + avatar_url);
                String html_url = userJSON.getString("html_url");
                Log.d("logs", "processUserResult: html: " + html_url);
                String location = userJSON.getString("location");
                Log.d("logs", "processUserResult: location: " + location);
                String bio = userJSON.getString("bio");
                Log.d("logs", "processUserResult: bio: " + bio);
                String public_repos = userJSON.getString("public_repos");
                Log.d("logs", "processUserResult: repos: " + public_repos);
                String followers = userJSON.getString("followers");
                Log.d("logs", "processUserResult: followers: " + followers);

                GitUser gitUser = new GitUser(login, avatar_url, html_url, location, bio, public_repos, followers);
                gitUsers.add(0, gitUser);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gitUsers;
    }
}
