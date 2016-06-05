package com.mikhail.githubapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mikhail.githubapi.adapter.RecyclerViewAdapter;
import com.mikhail.githubapi.model.Repo;
import com.mikhail.githubapi.provider.GitHubAPIService;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;
    public static final String REPO_DATES = "created:>2016-06-01";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reposApiCall();

    }

    private void reposApiCall() {
        GitHubAPIService.GitHubRx gitHub = GitHubAPIService.createRx();

        Observable<List<Repo>> observable = gitHub.repositories("created");

        observable.observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<List<Repo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("GitHubActivity", "Call failed");

                    }

                    @Override
                    public void onNext(List<Repo> repositories) {
                        Log.d("RetrofitActivity", "Call made");
//                        ModelObjectAdapter modelObjectAdapter = new ModelObjectAdapter(repos);
                        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(repositories);
//                        recyclerViewAdapter.updateData(repos);
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                });
    }
}


