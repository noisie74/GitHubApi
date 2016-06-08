package com.mikhail.githubapi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhail.githubapi.R;
import com.mikhail.githubapi.adapter.ContributorAdapter;
import com.mikhail.githubapi.adapter.ContributorObjectAdapter;
import com.mikhail.githubapi.model.Contributor;
import com.mikhail.githubapi.model.Items;
import com.mikhail.githubapi.provider.GitHubAPIService;
import com.mikhail.githubapi.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.mikhail.githubapi.util.AppUtils.isConnected;

/**
 * Created by Mikhail on 6/6/16.
 */
public class ContributorFragment extends Fragment {

    protected View v;
    protected Context context;
    private ContributorAdapter contributorAdapter;
    private ContributorObjectAdapter contributorObjectAdapter;
    private SwipeRefreshLayout swipeContainer;
    private List<Contributor> contributors;
    private List<Items> gitHubItems;
    protected RecyclerView recyclerView;
    private String url;
    private int position;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hit API to get Contributors for the selected repository if network
//        getContributors(getArguments().getInt("contributorFragment" ));

         Bundle args = getArguments();

//        getContributors();

//          getContributors(args.getInt("contributorFragment",position ));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        context = getContext();

        contributors = new ArrayList<>();
        initRecyclerView(v);
        swipeContainer.setRefreshing(false);

//        setPullRefresh();
        getContr();


        // specify an adapter to hold contributor list

        return v;
    }

    private void initRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
//        RecyclerView.LayoutManager contributorLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contributorObjectAdapter = new ContributorObjectAdapter(contributors);
//        contributorAdapter = new ContributorAdapter(contributors);
        recyclerView.setAdapter(contributorAdapter);
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);

    }

    private void getContributors() {


        GitHubAPIService.GitHubRx gitHub = GitHubAPIService.createRx();

        Observable<Response<Contributor>> observable =
                gitHub.contributors("vic317yeh",
                        "One-Click-to-Be-Pro");

        observable.observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<Response<Contributor>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(Constants.CONTR_TAG, "Call failed!");

                    }

                    @Override
                    public void onNext(Response<Contributor> contributors) {
                        Log.d(Constants.CONTR_TAG, "Call success!");

//                        Collections.addAll();

                        contributorAdapter = new ContributorAdapter((List<Contributor>) contributors.body());
                        recyclerView.setAdapter(contributorAdapter);
                        contributorAdapter.notifyDataSetChanged();
                    }
                });
    }



    private void getContr(){
        GitHubAPIService.GitHub github = GitHubAPIService.create();

        Call<List<Contributor>> call = github.contributors("square", "retrofit");

        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                List<Contributor> contributors = response.body();
                Log.d(Constants.CONTR_TAG, "Call success!");

                contributorObjectAdapter = new ContributorObjectAdapter(contributors);
                recyclerView.setAdapter(contributorObjectAdapter);
                swipeContainer.setRefreshing(false);
                setPullRefresh();
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
              swipeContainer.setRefreshing(false);
                Log.d(Constants.CONTR_TAG, "Call failed!");


            }
        });
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private void setPullRefresh() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isConnected(context)) {
                    Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_LONG).show();
                    swipeContainer.setRefreshing(false);
                } else {
                    getContr();
                }

            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.darker_gray,
                android.R.color.white);

    }

}
