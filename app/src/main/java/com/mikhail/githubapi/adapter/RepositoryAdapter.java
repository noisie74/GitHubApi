package com.mikhail.githubapi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhail.githubapi.R;
import com.mikhail.githubapi.model.Items;
import com.mikhail.githubapi.util.AppUtils;

import java.util.List;

/**
 * Adapter for repository object
 */
public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {

    private List<Items> repositories;
    public Context context;
    public static OnItemClickListener listener;


    public RepositoryAdapter(List<Items> repositories) {
        this.repositories = repositories;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView headline, date, star;

        public ViewHolder(final View itemView) {
            super(itemView);

            headline = (TextView) itemView.findViewById(R.id.repos_headline);
            date = (TextView) itemView.findViewById(R.id.date);
            star = (TextView) itemView.findViewById(R.id.star);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_repo_layout, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Items data = repositories.get(position);
        holder.headline.setText(data.getName());
        holder.date.setText(AppUtils.formatInputDate(data.getCreatedAt()));
        holder.star.setText(String.valueOf(data.getStargazersCount()));

    }

    @Override
    public int getItemCount() {
        return repositories.size();

    }

}
