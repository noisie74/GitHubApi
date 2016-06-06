package com.mikhail.githubapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mikhail on 6/4/16.
 */
public class Repo {

    @SerializedName("total_count")
    private int totalCount;
    @SerializedName("contributors_url")
    private String contributorURL;
    private List<Items> items;

    public List<Items> getItems() {
        return items;
    }

    public String getContributorURL() {
        return contributorURL;
    }
}
