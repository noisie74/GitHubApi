package com.mikhail.githubapi.activity;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.mikhail.githubapi.R;
import com.mikhail.githubapi.fragment.ContributorFragment;
import com.mikhail.githubapi.fragment.RepositoryFragment;
import com.mikhail.githubapi.interfaces.ControlActionBar;


public class MainActivity extends AppCompatActivity implements ControlActionBar {

    private ContributorFragment contributorFragment;
    private RepositoryFragment repositoryFragment;
    private FrameLayout fragContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragContainer = (FrameLayout) findViewById(R.id.frag_container);
        contributorFragment = new ContributorFragment();
        repositoryFragment = new RepositoryFragment();
        setFragment();

    }

    private void setFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_container, repositoryFragment, RepositoryFragment.class.getName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);


    }

    @Override
    public void onBackPressed() {

        if (contributorFragment != null) {
            setFragment();
            contributorFragment = null;
        }
        super.onBackPressed();
    }
}


