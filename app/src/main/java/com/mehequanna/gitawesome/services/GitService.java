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
            Log.d("log", "jsondata" + response.body().string());
            if (response.isSuccessful()) {
                JSONObject gitJSON = new JSONObject(jsonData);
                JSONArray languageJSON = gitJSON.getJSONArray("items");


                JSONObject languageResults = languageJSON.getJSONObject(0);

                String name = languageResults.getString("name");
                Log.d("log", "Name: " + name);

                String description = languageResults.getString("description");
                Log.d("log", "Description: " + description);

                String avatar = languageResults.getJSONObject("owner").getString("avatar_url");
                Log.d("log", "Avatar: " + avatar);

                String html = languageResults.getJSONObject("owner").getString("html_url");
                Log.d("log", "Website: " + html);

                String stargazers = languageResults.getString("stargazers_count");
                Log.d("log", "Stargazers: " + stargazers);

                String created = languageResults.getString("created_at");
                Log.d("log", "Created: " + created);

                String updated = languageResults.getString("updated_at");
                Log.d("log", "Updated: " + updated);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return languageRepos;

    }
}
