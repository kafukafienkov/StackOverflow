package com.bartek.stackoverflow.rest;

import com.google.gson.annotations.SerializedName;

/**
 * author: Bartek
 */
public class PostOwner {
    @SerializedName("profile_image")
    private String profileImage;

    @SerializedName("display_name")
    private String displayName;

    public String getProfileImage() {
        return profileImage;
    }

    public String getDisplayName() {
        return displayName;
    }
}
