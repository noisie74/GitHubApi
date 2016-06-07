package com.mikhail.githubapi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mikhail.githubapi.R;
import com.mikhail.githubapi.adapter.RepositoryAdapter;
import com.mikhail.githubapi.model.Items;
import com.mikhail.githubapi.model.Repo;
import com.mikhail.githubapi.provider.GitHubAPIService;
import com.mikhail.githubapi.util.AppUtils;
import com.mikhail.githubapi.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.mikhail.githubapi.util.AppUtils.isConnected;

/**
 * Created by Mikhail on 6/7/16.
 */
public class RepositoryFragment extends Fragment {

    protected RecyclerView recyclerView;
    private RepositoryAdapter repositoryAdapter;
    private List<Items> gitHubData;
    private SwipeRefreshLayout swipeContainer;
    public Context context;
    private View v;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        context = getContext();

        initRecyclerView(v);
        setPullRefresh();
        checkNetwork();
        reposApiCall();
        reposClickListener();

        return v;
    }

    private void initRecyclerView(View v) {

        gitHubData = new ArrayList<>();
        repositoryAdapter = new RepositoryAdapter(gitHubData);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(repositoryAdapter);
    }

    private void reposApiCall() {
        GitHubAPIService.GitHubRx gitHub = GitHubAPIService.createRx();

        Observable<Response<Repo>> observable =
                gitHub.repositories(Constants.REPOS_DATE + AppUtils.getLastWeekDate(),
                        Constants.REPOS_RATING, Constants.REPOS_SORT);

        observable.observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<Response<Repo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(Constants.REPOS_TAG, "Call failed!");

                    }

                    @Override
                    public void onNext(Response<Repo> repositories) {
                        Log.d(Constants.REPOS_TAG, "Call success!");

                        callSuccess(repositories);

                    }
                });
    }

    private void callSuccess(Response<Repo> repositories){

        gitHubData = repositories.body().getItems();
        repositoryAdapter = new RepositoryAdapter(gitHubData);
        recyclerView.setAdapter(repositoryAdapter);
        repositoryAdapter.notifyDataSetChanged();
        swipeContainer.setRefreshing(false);
    }

    /**
     * check network connection
     */
    private void checkNetwork() {

        if (!isConnected(this.context)) {

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);
                }
            }, 1600);

            Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_LONG).show();
            swipeContainer.setRefreshing(false);
        } else {
            setPullRefresh();
        }
    }

    private void setPullRefresh() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isConnected(context)) {
                    Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_LONG).show();
                    swipeContainer.setRefreshing(false);
                } else {
                    reposApiCall();
                }

            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.darker_gray,
                android.R.color.white);

    }

    private void reposClickListener(){

        if (repositoryAdapter != null){

            repositoryAdapter.setOnItemClickListener(new RepositoryAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {

                    Items item = gitHubData.get(position);

                    ContributorFragment contributorFragment = new ContributorFragment();

                    Bundle args = new Bundle();
                    args.putInt("contributorFragment", position);
                    contributorFragment.setArguments(args);
                    contributorFragment.setUrl(item.getContributorURL());
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container, contributorFragment);
                    fragmentTransaction.commit();
                    Log.d("RepositoryFragment", "Fragment transition on click!");
                }
            });
        }


    }
}
