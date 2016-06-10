package com.mikhail.githubapi.activity;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.mikhail.githubapi.R;
import com.mikhail.githubapi.fragment.RepositoryFragment;
import com.mikhail.githubapi.interfaces.ControlActionBar;
import com.mikhail.githubapi.util.Constants;


public class MainActivity extends AppCompatActivity implements ControlActionBar {

    private FrameLayout fragContainer;
    public static ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragContainer();
        setFragment();
        showProgress(true);
    }

    /**
     * set Fragment with top repositories
     */
    private void setFragment() {
        RepositoryFragment repositoryFragment = new RepositoryFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_container, repositoryFragment, Constants.REPOS_TAG);
        fragmentTransaction.commit();

    }

    private void setFragContainer() {
        fragContainer = (FrameLayout) findViewById(R.id.frag_container);

    }

    @Override
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);

    }

    @Override
    public void onBackPressed() {
        getSupportActionBar().setTitle(getString(R.string.app_title));
        showProgress(true);
        super.onBackPressed();
    }

    private void showProgress(boolean isShow) {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        if (progressBar != null) {
            progressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);

        }

    }
}


