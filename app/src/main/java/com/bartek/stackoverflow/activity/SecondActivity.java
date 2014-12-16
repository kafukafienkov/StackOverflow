package com.bartek.stackoverflow.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

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
public class SecondActivity extends ListActivity {

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
    }


    private void loadData() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(StackOverflowApi.API_URL)
                .build();
//                        "&intitle="+ stringReceived + "&tagged" + stringReceived).build();

        StackOverflowApi service = restAdapter.create(StackOverflowApi.class);
        service.getJsonData(stringReceived, stringReceived, new Callback<SearchResults>() {
//            private DataAdapter listAdapter;

            @Override
            public void success(SearchResults results, Response response) {
                setListAdapter(new DataAdapter(SecondActivity.this,
                        R.layout.item_row, results.getItems()));

                Toast toast = Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast toast = Toast.makeText(getApplicationContext(), "FAILURE", Toast.LENGTH_SHORT);
                toast.show();
            }

//            public void setListAdapter(DataAdapter listAdapter) {
//                this.listAdapter = listAdapter;
//            }
        });

    }

    public class DataAdapter extends ArrayAdapter<DataHandler> {

        private final Context context;
        private final int resourceId;

        public DataAdapter(Context context, int resource, List<DataHandler> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resourceId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

//            position = View.generateViewId();
            //TODO introduce auto ID generation

            final DataHandler dateHandler = getItem(position);

            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService
                            (Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.item_row, parent, false);

//            TextView userName = (TextView) rowView.findViewById(R.id.user_name);
//            userName.setText(dateHandler.getOwner().getDisplayName());
//
//            TextView title = (TextView) rowView.findViewById(R.id.title);
//            title.setText(dateHandler.getPostTitle());

            ImageView userImage = (ImageView) rowView.findViewById(R.id.user_image);
            Picasso.with(context)
                    .load(dateHandler.getOwner().getProfileImage())
                    .into(userImage);

//            TextView score = (TextView) rowView.findViewById(R.id.answers);
//            score.setText(dateHandler.getScore());
//
//            TextView tags = (TextView) rowView.findViewById(R.id.tags);
//            tags.setText(dateHandler.getTags().toString());

            return rowView;
        }
    }
}
