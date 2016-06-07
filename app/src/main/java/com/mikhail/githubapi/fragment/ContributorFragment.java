package com.mikhail.githubapi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhail.githubapi.MainActivity;
import com.mikhail.githubapi.R;
import com.mikhail.githubapi.adapter.ContributorAdapter;
import com.mikhail.githubapi.adapter.RepositoryAdapter;
import com.mikhail.githubapi.model.Contributor;
import com.mikhail.githubapi.model.Repo;
import com.mikhail.githubapi.provider.GitHubAPIService;
import com.mikhail.githubapi.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Mikhail on 6/6/16.
 */
public class ContributorFragment extends Fragment {

    protected View v;
    protected TextView contributorName, contribution;
    protected ImageView contributorImage;
    private ContributorAdapter contributorAdapter;
    private List<Contributor> contributors;
    private RecyclerView contributorRecyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hit API to get Contributors for the selected repository if network
        getContributors(getArguments().getInt("contributorFragment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_layout, container, false);
        contributorName = (TextView) v.findViewById(R.id.contributor_name);
        contribution = (TextView) v.findViewById(R.id.contribution);
        contributorImage = (ImageView) v.findViewById(R.id.img_contributor);


        contributorRecyclerView = (RecyclerView) v.findViewById(R.id.contributor_recycler_view);
        RecyclerView.LayoutManager contributorLayoutManager = new LinearLayoutManager(getActivity());
        contributorRecyclerView.setLayoutManager(contributorLayoutManager);

        contributors = new ArrayList<>();

        // specify an adapter to hold contributor list
        contributorAdapter = new ContributorAdapter(getActivity());
        contributorRecyclerView.setAdapter(contributorAdapter);

        return v;
    }


    private void getContributors() {


        GitHubAPIService.GitHubRx gitHub = GitHubAPIService.createRx();

        Observable<Response<Repo>> observable =
                gitHub.repositories(MainActivity.REPOS_DATE + AppUtils.getLastWeekDate(),
                        MainActivity.REPOS_RATING, MainActivity.REPOS_SORT);

        observable.observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<Response<Repo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(MainActivity.TAG, "Call failed!");

                    }

                    @Override
                    public void onNext(Response<Repo> repositories) {
                        Log.d(MainActivity.TAG, "Call success!");

                        callSuccess(repositories);

                    }
                });
    }


    private void callSuccess(Response<Repo> repositories, int position){

        contributors = repositories.body().getContributorURL();

        String url = contributors.get(position).getHtmlUrl() ;


        contributorAdapter = new ContributorAdapter();
        contributorRecyclerView.setAdapter(contributorAdapter);
        contributorAdapter.notifyDataSetChanged();
    }
}
