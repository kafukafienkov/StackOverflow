package com.bartek.example.stackoverflow;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Bartek on 2014-12-07.
 */
public interface StackOverflowApi {

    @GET("&intitle=[inTitle]&tagged[tag]")
    void getJsonData(@Path("inTitle") String inTitle,
                     @Path("tah") String tag,
                     Callback<DataHandler> callback);

}
