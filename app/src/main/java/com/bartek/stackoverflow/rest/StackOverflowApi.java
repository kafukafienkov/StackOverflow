package com.bartek.stackoverflow.rest;

import com.bartek.stackoverflow.model.SearchResults;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Bartek on 2014-12-07.
 */
public interface StackOverflowApi {

    public static final String API_URL = "https://api.stackexchange.com/2.2";
//    public static final String KEY = "ekZxs*4VlUsQl8E986b6nA((";

    @GET("/search?pagesize=100&order=desc&sort=activity&site=stackoverflow")
    public void getJsonData(@Query("intitle") String intitle,
//                            @Query("tagged") String tagged,
                            Callback<SearchResults> callback);

}
