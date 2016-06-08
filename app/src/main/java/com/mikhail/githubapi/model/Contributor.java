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


//    public final String login;
//    public final int contributions;
//
//    public Contributor(String login, int contributions) {
//        this.login = login;
//        this.contributions = contributions;
//    }

//    @Override
//    public String toString() {
//        return String.format("%5d %s", contributions, login);
//    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public long getContribution() {
        return contribution;
    }
}
