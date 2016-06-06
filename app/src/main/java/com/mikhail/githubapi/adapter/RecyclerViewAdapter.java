package com.mikhail.githubapi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhail.githubapi.R;
import com.mikhail.githubapi.model.Items;
import com.mikhail.githubapi.model.Repo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail on 4/17/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Items> repositories;
    Context context;

    public RecyclerViewAdapter(List<Items> repositories) {
        this.repositories = repositories;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView headline, date, star;

        public ViewHolder(final View itemView) {
            super(itemView);

            headline = (TextView) itemView.findViewById(R.id.repos_headline);
            date = (TextView) itemView.findViewById(R.id.date);
            star = (TextView) itemView.findViewById(R.id.star);


        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_layout, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Items data = repositories.get(position);
        holder.headline.setText(data.getName());
        holder.date.setText(data.getCreatedAt());
        holder.star.setText(String.valueOf(data.getStargazersCount()));

    }

    @Override
    public int getItemCount() {
        return repositories.size();

    }
}

