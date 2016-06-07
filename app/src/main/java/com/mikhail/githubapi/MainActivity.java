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
import com.mikhail.githubapi.fragment.RepositoryFragment;
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
    public Context context;
    public static final String REPOS_DATE = "created:>=";
    public static final String REPOS_RATING = "star";
    public static final String REPOS_SORT = "desc";
    public static final String TAG = "MainActivity";
    private FragmentManager fragmentManager;
    private ContributorFragment contributorFragment;
    private RepositoryFragment repositoryFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        fragmentManager = getSupportFragmentManager();
        contributorFragment = new ContributorFragment();
        repositoryFragment = new RepositoryFragment();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.frag_container, repositoryFragment);
                    fragmentTransaction.commit();

    }


}


