package com.bartek.stackoverflow.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bartek.stackoverflow.R;
import com.bartek.stackoverflow.model.SearchResults;
import com.bartek.stackoverflow.rest.DataHandler;
import com.bartek.stackoverflow.rest.StackOverflowApi;
import com.squareup.picasso.Picasso;

import java.util.List;

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
        loadData();
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
                .setEndpoint("https://api.stackexchange.com/2.2")
                .build();
//                        "&intitle="+ stringReceived + "&tagged" + stringReceived).build();

        StackOverflowApi service = restAdapter.create(StackOverflowApi.class);
        service.getJsonData(stringReceived, new Callback<SearchResults>() {
            private DataAdapter listAdapter;

            @Override
            public void success(SearchResults results, Response response) {
                setListAdapter(new DataAdapter(SecondActivity.this, R.layout.item_row, results.getItems()));
                System.out.println();
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println();
            }

            public void setListAdapter(DataAdapter listAdapter) {
                this.listAdapter = listAdapter;
            }
        });

    }

    class DataAdapter extends ArrayAdapter<DataHandler> {

        private final Context context;
        private final int resourceId;


        public DataAdapter(Context context, int resource, List<DataHandler> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resourceId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View row = LayoutInflater.from(context).inflate(resourceId, parent, false);
            final DataHandler dateHandler = getItem(position);

            ImageView userImage = (ImageView) row.findViewById(R.id.user_image);
            Picasso.with(context)
                    .load(dateHandler.getOwner().getProfileImage())
                    .into(userImage);

            TextView userName = (TextView) row.findViewById(R.id.user_name);
            userName.setText(dateHandler.getTags().toString());

            TextView score = (TextView) row.findViewById(R.id.answers);
            score.setText(dateHandler.getScore());

            TextView title = (TextView) row.findViewById(R.id.title);
            title.setText(dateHandler.getPostTitle());

            return row;
        }
    }

}
