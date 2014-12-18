package com.bartek.stackoverflow.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bartek on 2014-12-07.
 */
public class DataHandler {

    private PostOwner owner;
    @SerializedName("title")
    private String postTitle;
    @SerializedName("answer_count")
    private String answerCount;

    public PostOwner getOwner() {
        return owner;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String answerCount() {
        return answerCount;
    }
}
