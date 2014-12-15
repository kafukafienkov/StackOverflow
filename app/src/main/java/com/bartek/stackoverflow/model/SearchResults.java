package com.bartek.stackoverflow.model;

import com.bartek.stackoverflow.rest.DataHandler;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author: Bartek
 */
public class SearchResults {
    @SerializedName("items")
    private List<DataHandler> items;

    public List<DataHandler> getItems() {
        return items;
    }
}
