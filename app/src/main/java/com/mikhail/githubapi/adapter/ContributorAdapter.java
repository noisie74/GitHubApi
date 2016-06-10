package com.mikhail.githubapi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.mikhail.githubapi.R;
import com.mikhail.githubapi.model.Contributor;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for contributor object
 */
public class ContributorAdapter extends RecyclerView.Adapter<ContributorAdapter.ViewHolder> {
    private List<Contributor> contributors;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView contributorImageView;
        public final TextView contributorNameTextView, contributionsTextView,
                contributorURL;


        public ViewHolder(View v) {
            super(v);
            contributorImageView = (ImageView) v.findViewById(R.id.img_contributor);
            contributorNameTextView = (TextView) v.findViewById(R.id.contributor_name);
            contributionsTextView = (TextView) v.findViewById(R.id.contribution);
            contributorURL = (TextView) v.findViewById(R.id.contributor_url);

        }
    }

    public ContributorAdapter(List<Contributor> contributors) {
        this.contributors = contributors;
    }

    @Override
    public ContributorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_contributor_layout, parent, false);

        context = parent.getContext();


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Contributor data = this.contributors.get(position);

        holder.contributorNameTextView.setText(data.getUserLogin());
        holder.contributionsTextView.setText(context.getString(R.string.contributions) + data.getContribution());
        holder.contributorURL.setText(data.getHtmlUrl());
        Picasso.with(context)
                .load(data.getAvatarUrl())
                .placeholder(R.drawable.githubholder)
                .into(holder.contributorImageView);
    }

    @Override
    public int getItemCount() {
        return contributors.size();
    }
}
