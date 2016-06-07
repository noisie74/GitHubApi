package com.mikhail.githubapi;

import android.content.Context;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;


import com.mikhail.githubapi.adapter.RepositoryAdapter;
import com.mikhail.githubapi.fragment.ContributorFragment;
import com.mikhail.githubapi.fragment.RepositoryFragment;
import com.mikhail.githubapi.model.Items;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String REPOS_DATE = "created:>=";
    public static final String REPOS_RATING = "star";
    public static final String REPOS_SORT = "desc";
    public static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFragment();
    }

    private void setFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        RepositoryFragment repositoryFragment = new RepositoryFragment();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frag_container, repositoryFragment);
        fragmentTransaction.commit();

    }

}


