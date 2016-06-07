package com.mikhail.githubapi;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mikhail.githubapi.adapter.RepositoryAdapter;
import com.mikhail.githubapi.fragment.ContributorFragment;
import com.mikhail.githubapi.model.Items;
import com.mikhail.githubapi.model.Repo;
import com.mikhail.githubapi.provider.GitHubAPIService;
import com.mikhail.githubapi.util.AppUtils;

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

public class MainActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;
    private RepositoryAdapter repositoryAdapter;
    private List<Items> gitHubData;
    private SwipeRefreshLayout swipeContainer;
    public Context context;
    public static final String REPOS_DATE = "created:>=";
    public static final String REPOS_RATING = "star";
    public static final String REPOS_SORT= "desc";
    public static final String TAG = "MainActivity";
    private FragmentManager fragmentManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        fragmentManager = getSupportFragmentManager();

        checkNetwork();
        setPullRefresh();
        reposApiCall();
        initRecyclerView();
        reposClickListener();
    }

    /**
     * set recycler view adapter
     */
    private void initRecyclerView() {

        gitHubData = new ArrayList<>();
        repositoryAdapter = new RepositoryAdapter(gitHubData);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(repositoryAdapter);
    }

    private void reposApiCall() {
        GitHubAPIService.GitHubRx gitHub = GitHubAPIService.createRx();

        Observable<Response<Repo>> observable =
                gitHub.repositories(REPOS_DATE + AppUtils.getLastWeekDate(),
                REPOS_RATING, REPOS_SORT);

        observable.observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<Response<Repo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Call failed!");

                    }

                    @Override
                    public void onNext(Response<Repo> repositories) {
                        Log.d(TAG, "Call success!");

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

            Toast.makeText(MainActivity.this, R.string.no_internet, Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MainActivity.this, R.string.no_internet, Toast.LENGTH_LONG).show();
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
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.relative_layout, contributorFragment);
                    fragmentTransaction.commit();
                }
            });
        }


    }

}


