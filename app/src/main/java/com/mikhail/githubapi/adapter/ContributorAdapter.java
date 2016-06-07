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
import com.mikhail.githubapi.model.Items;
import com.mikhail.githubapi.util.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mikhail on 6/7/16.
 */
public class ContributorAdapter extends RecyclerView.Adapter<ContributorAdapter.ViewHolder> {

    private final Context context;
    private List<Contributor> contributors;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public final ImageView contributorImageView;
        public final TextView contributorNameTextView;
        public final TextView contributionsTextView;


        public ViewHolder(View view) {
            super(view);

            contributorImageView = (ImageView) view.findViewById(R.id.img_contributor);
            contributorNameTextView = (TextView) view.findViewById(R.id.contributor_name);
            contributionsTextView = (TextView) view.findViewById(R.id.contribution);

        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContributorAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ContributorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contributor data = contributors.get(position);
        holder.contributorNameTextView.setText(data.getUserLogin());
        holder.contributionsTextView.setText("Contributions: " + data.getContribution());
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
