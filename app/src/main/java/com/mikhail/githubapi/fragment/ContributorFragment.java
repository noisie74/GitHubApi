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
import android.widget.Toast;

import com.mikhail.githubapi.R;
import com.mikhail.githubapi.adapter.ContributorAdapter;
import com.mikhail.githubapi.model.Contributor;
import com.mikhail.githubapi.provider.GitHubAPIService;
import com.mikhail.githubapi.util.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mikhail.githubapi.util.AppUtils.isConnected;
import com.mikhail.githubapi.interfaces.ControlActionBar;
import com.mikhail.githubapi.util.Filter;


/**
 * Created by Mikhail on 6/6/16.
 */
public class ContributorFragment extends Fragment  {

    protected View v;
    protected Context context;
    private ContributorAdapter contributorAdapter;
    private SwipeRefreshLayout swipeContainer;
    private List<Contributor> contributors;
    protected RecyclerView recyclerView;
    private String[] selectedRepository;
    public ControlActionBar controlActionBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        setViews();
        getClickedRepository();
        initRecyclerView(v);
        getContributors();
        setControlBarTitle();

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            controlActionBar = (ControlActionBar) getActivity();
        } catch (ClassCastException ex) {
            throw new ClassCastException();
        }
    }

    private void setControlBarTitle(){
        controlActionBar.setActionBarTitle(selectedRepository[1]);

    }

    private void getClickedRepository() {
        Bundle clickedRepository = getArguments();
        selectedRepository = clickedRepository.getStringArray(Constants.SELECTED_REPOSITORY);
    }

    private void initRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contributorAdapter = new ContributorAdapter(contributors);

    }

    private void setViews() {
        context = getContext();
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        swipeContainer.setRefreshing(false);

    }

    private void getContributors() {
        GitHubAPIService.GitHub github = GitHubAPIService.create();

        Call<List<Contributor>> call = github.contributors(selectedRepository[0], selectedRepository[1]);
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                Log.d(Constants.CONTR_TAG, "Call success!");
                List<Contributor> contributors = response.body();

                Collection<Contributor> frequentContributors = Filter.isFrequentContributor(contributors);
                contributorAdapter = new ContributorAdapter(new ArrayList(frequentContributors));
                recyclerView.setAdapter(contributorAdapter);
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

    private void setPullRefresh() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isConnected(context)) {
                    Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_LONG).show();
                    swipeContainer.setRefreshing(false);
                } else {
                    getContributors();
                }

            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.darker_gray,
                android.R.color.white);

    }


}
