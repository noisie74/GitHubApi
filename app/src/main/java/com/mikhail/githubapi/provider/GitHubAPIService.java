package com.mikhail.githubapi.provider;


import com.mikhail.githubapi.model.Repo;

import java.util.List;
import java.util.logging.Level;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Mikhail on 6/4/16.
 */
public class GitHubAPIService {

    public static final String API_URL = "https://api.github.com/search/";


    public static GitHubRx createRx() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitHubAPIService.GitHubRx.class);
    }

    public interface GitHubRx {
        @GET("repositories")
        Observable<Response<Repo>> repositories(
                @Query("q") String query,
                @Query("sort") String rating,
                @Query("order") String orderBy);
    }


}

