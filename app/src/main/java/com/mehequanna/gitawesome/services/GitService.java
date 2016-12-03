package com.mehequanna.gitawesome.services;

import android.util.Log;

import com.mehequanna.gitawesome.Constants;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 * Created by stephenemery on 12/2/16.
 */

public class GitService {
    public static void findRepos(String username, Callback callback) {
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(Constants.GIT_CLIENT_ID, Constants.GIT_CLIENT_SECRET);


    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new SigningInterceptor(consumer))
            .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GIT_BASE_URL).newBuilder();
        urlBuilder.fragment(username);
        urlBuilder.fragment(Constants.GIT_REPOS);
        String url = urlBuilder.build().toString();

        Log.d("log", "url: " + url);

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
