package com.mikhail.githubapi.model;

/**
 * Created by Mikhail on 6/4/16.
 */
public class Repo {

    public final String name;

    public Repo(String name, String url) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}
