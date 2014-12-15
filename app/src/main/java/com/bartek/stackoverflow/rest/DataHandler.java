package com.bartek.stackoverflow.rest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bartek on 2014-12-07.
 */
public class DataHandler {


    @SerializedName("tags")
    private List<String> tags;
    private PostOwner owner;
    @SerializedName("title")
    private String postTitle;
    @SerializedName("score")
    private int score;

    public List<String> getTags() {
        return tags;
    }

    public PostOwner getOwner() {
        return owner;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public int getScore() {
        return score;
    }
}
