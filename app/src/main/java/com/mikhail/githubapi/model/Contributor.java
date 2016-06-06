package com.mikhail.githubapi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mikhail on 6/5/16.
 */
public class Contributor {

    @SerializedName("login")
    private String userLogin;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("html_url")
    private String htmlUrl;
    @SerializedName("contributions")
    private long contribution;


    public String getUserLogin() {
        return userLogin;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }
}
