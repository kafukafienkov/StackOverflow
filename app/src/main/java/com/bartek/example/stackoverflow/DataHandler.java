package com.bartek.example.stackoverflow;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bartek on 2014-12-07.
 */
public class DataHandler {

    @SerializedName("tags")
    private String tags;

    @SerializedName("owner")
    private Owner owner;

    public String getTags() {
        return tags;
    }

    public String getDisplayedName() {
        return owner.displayedName;
    }

    public String getReputation() {
        return String.valueOf(owner.reputation);
    }

    public String getThreadTitle() {
        return owner.threadTitle;
    }

    public String getThreadLink() {
        return owner.threadLink;
    }

    public String getImageUrl() {
        return owner.imageUrl;
    }

    public String getProfileLink() {
        return owner.profileLink;
    }

    class Owner {

        @SerializedName("display_name")
        private String displayedName;

        @SerializedName("reputation")
        private int reputation;

        @SerializedName("title")
        private String threadTitle;

        @SerializedName("link")
        private String threadLink;

        @SerializedName("profile_image")
        private String imageUrl;

        @SerializedName("link")
        private String profileLink;
    }
}
