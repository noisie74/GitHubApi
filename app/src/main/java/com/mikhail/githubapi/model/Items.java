package com.mikhail.githubapi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mikhail on 6/4/16.
 */
public class Items {

    private String name;
    @SerializedName("stargazers_count")
    private int stargazersCount;
    @SerializedName("created_at")
    private String createdAt;
    private Contributor owner;

    public String getName() {
        return name;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Contributor getOwner() {
        return owner;
    }
}
