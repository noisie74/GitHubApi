package com.mikhail.githubapi;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.mikhail.githubapi.fragment.RepositoryFragment;



public class MainActivity extends AppCompatActivity {

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


