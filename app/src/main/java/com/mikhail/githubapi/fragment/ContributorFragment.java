package com.mikhail.githubapi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhail.githubapi.R;

/**
 * Created by Mikhail on 6/6/16.
 */
public class ContributorFragment extends Fragment {

    protected View v;
    protected TextView contributorName, contribution;
    protected ImageView contributorImage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_layout, container, false);
        contributorName = (TextView) v.findViewById(R.id.contributor_name);
        contribution = (TextView) v.findViewById(R.id.contribution);
        contributorImage = (ImageView) v.findViewById(R.id.img_contributor);

        return v;
    }
}
