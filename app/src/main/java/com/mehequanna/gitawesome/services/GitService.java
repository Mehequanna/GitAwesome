package com.mehequanna.gitawesome.services;

import android.util.Log;

import com.mehequanna.gitawesome.Constants;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by stephenemery on 12/2/16.
 */

public class GitService {
    public static void findRepos(String language, Callback callback) {
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
}
