package com.bartek.example.stackoverflow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Bartek on 2014-12-04.
 */
public class SecondActivity extends Activity {

    private String stringReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        receiveDataToSearchFor();

    }

    private void receiveDataToSearchFor() {
        Intent intentReceive = getIntent();
        stringReceived = intentReceive.getStringExtra("key");
        TextView textView = new TextView(this);
        textView.setText("You are looking for: " + stringReceived);

        setContentView(textView);
    }


    private void loadData() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.stackexchange.com/2.2" +
                        "/search?order=desc&sort=activity&site=stackoverflow")
                .build();
//                        "&intitle="+ stringReceived + "&tagged" + stringReceived).build();

        StackOverflowApi service = restAdapter.create(StackOverflowApi.class);
        service.getJsonData(stringReceived, stringReceived, new Callback<DataHandler>() {
            @Override
            public void success(DataHandler dataHandler, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}
