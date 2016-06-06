package com.mikhail.githubapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mikhail on 6/4/16.
 */
public class Repo {

    @SerializedName("total_count")
    private int totalCount;
    private List<Items> items;

    public List<Items> getItems() {
        return items;
    }

}
