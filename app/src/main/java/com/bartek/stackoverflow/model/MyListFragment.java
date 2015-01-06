package com.bartek.stackoverflow.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bartek.stackoverflow.R;
import com.bartek.stackoverflow.activity.BrowserActivity;
import com.bartek.stackoverflow.activity.StackOverflowMainActivity;
import com.bartek.stackoverflow.rest.DataHandler;
import com.bartek.stackoverflow.rest.StackOverflowApi;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * author: Bartek
 */
public class MyListFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loadData();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void loadData() {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(StackOverflowApi.API_URL)
                .build();

        StackOverflowApi service = restAdapter.create(StackOverflowApi.class);
        String dataToSearchFor = getActivity().getIntent().getStringExtra("key");
        service.getJsonData(dataToSearchFor, new Callback<SearchResults>() {

            @Override
            public void success(SearchResults results, Response response) {
                int itemsListSize = results.getItems().size();
                if (itemsListSize != 0) {
                    setListAdapter(new DataAdapter(getActivity(),
                            R.layout.item_row, results.getItems()));

                    Toast toast = Toast.makeText(
                            getActivity(), "LOADING DATA", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(
                            getActivity(), "NO RESULTS FOUND", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    goBackToMainActivity();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast toast = Toast.makeText(getActivity(),
                        "FAILURE - CHECK YOUR INTERNET CONNECTION",
                        Toast.LENGTH_SHORT);
                toast.show();

                goBackToMainActivity();
            }
        });
    }

    private void goBackToMainActivity() {
        Intent intent = new Intent(getActivity(), StackOverflowMainActivity.class);
        startActivity(intent);
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
                }
            });

            return rowView;
        }
    }
}
