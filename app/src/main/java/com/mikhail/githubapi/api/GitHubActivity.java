package com.mikhail.githubapi.api;

import android.os.Bundle;
import android.util.Log;

import com.mikhail.githubapi.MainActivity;
import com.mikhail.githubapi.adapter.ModelObjectAdapter;
import com.mikhail.githubapi.adapter.RecyclerViewAdapter;
import com.mikhail.githubapi.model.Items;
import com.mikhail.githubapi.model.Repo;
import com.mikhail.githubapi.provider.GitHubAPIService;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Mikhail on 6/4/16.
 */
//public class GitHubActivity  {
//
//    public static final String REPO_DATES = "created:>2016-06-01";
//    public static final String REPO_RATING = "stars";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        reposApiCall();
//
//    }
//
//    private void reposApiCall() {
//        GitHubAPIService.GitHubRx gitHub = GitHubAPIService.createRx();
//
//        Observable<List<Repo>> observable = gitHub.repositories(REPO_DATES);
//
//        observable.subscribeOn(Schedulers.newThread()).
//                observeOn(AndroidSchedulers.mainThread()).
//                subscribe(new Subscriber<List<Repo>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("GitHubActivity", "Call failed");
//
//                    }
//
//                    @Override
//                    public void onNext(List<Repo> repositories) {
//                        Log.d("RetrofitActivity", "Call made");
////                        ModelObjectAdapter modelObjectAdapter = new ModelObjectAdapter(repos);
//                        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(repositories);
////                        recyclerViewAdapter.updateData(repos);
//                        recyclerView.setAdapter(recyclerViewAdapter);
//                        recyclerViewAdapter.notifyDataSetChanged();
//                    }
//                });
//    }
//}
