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
import android.widget.TextView;
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

        StackOverflowApi service = restAdapter.create(StackOverflowApi.class);
        service.getJsonData(stringReceived, stringReceived, new Callback<SearchResults>() {

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
        });
    }

    public class DataAdapter extends ArrayAdapter<DataHandler> {

        private final Context context;
        private String linkUrl;

        public DataAdapter(Context context, int resource, List<DataHandler> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final DataHandler dateHandler = getItem(position);

            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService
                            (Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.item_row, parent, false);

            ImageView userImage = (ImageView) rowView.findViewById(R.id.user_image);
            userImage.setImageResource(R.drawable.ic_launcher);
            Picasso.with(context)
                    .load(dateHandler.getOwner().getProfileImage())
                    .placeholder(getResources().getDrawable(R.drawable.ic_launcher))
                    .into(userImage);

            TextView userName = (TextView) rowView.findViewById(R.id.user_name);
            userName.setText(dateHandler.getOwner().getDisplayName());

            TextView title = (TextView) rowView.findViewById(R.id.title);
            title.setText(dateHandler.getPostTitle());

            TextView answerCount = (TextView) rowView.findViewById(R.id.answer_count);
            answerCount.setText("Answers: " + dateHandler.answerCount());

            linkUrl = dateHandler.getLinkUrl();

            rowView.setClickable(true);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BrowserActivity.class);
                    intent.putExtra("secondActivityKey", linkUrl);
                    startActivity(intent);
                    System.out.println(linkUrl);
                }
            });

            return rowView;
        }
    }
}





