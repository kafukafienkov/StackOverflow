package com.bartek.example.stackoverflow;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bartek on 2014-12-07.
 */
public class FoundDataList {

    @SerializedName("items")
    private List<DataHandler> data;

    public List<DataHandler> getData() {
        return data;
    }
}
