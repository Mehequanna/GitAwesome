package com.mehequanna.gitawesome.services;

import android.util.Log;

import com.mehequanna.gitawesome.Constants;
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

/**
 * Created by stephenemery on 12/2/16.
 */

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
}
