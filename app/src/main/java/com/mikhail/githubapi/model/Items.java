package com.mikhail.githubapi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mikhail on 6/4/16.
 */
public class Items {

    private String name;
    @SerializedName("stargazers_count")
    private int stargazersCount;

    public String getName() {
        return name;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }
}
