package com.mikhail.githubapi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhail.githubapi.R;

import java.util.List;

/**
 * Created by Mikhail on 6/4/16.
 */
public class ModelObjectAdapter<T> extends RecyclerView.Adapter<ModelObjectAdapter.ViewHolder>  {

    private List<T> modelObject;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView repositoriesTextView;
        public ViewHolder(TextView v) {
            super(v);
            repositoriesTextView = v;
        }
    }

    public ModelObjectAdapter(List<T> modelObject) {
        this.modelObject = modelObject;
    }

    @Override
    public ModelObjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_layout, parent, false);

        ViewHolder vh = new ViewHolder((TextView)v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ModelObjectAdapter.ViewHolder holder, int position) {
        T modelObject = this.modelObject.get(position);
        holder.repositoriesTextView.setText(modelObject.toString());
    }

    @Override
    public int getItemCount() {
        return modelObject.size();
    }
}
