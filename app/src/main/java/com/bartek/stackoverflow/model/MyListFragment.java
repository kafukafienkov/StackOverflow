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

    public static final String KEY_SECOND = "com.bartek.stackoverflow.model";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loadData();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void loadData() {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(StackOverflowApi.API_URL)
                .build();

        StackOverflowApi service = restAdapter.create(StackOverflowApi.class);
        String dataToSearchFor = getActivity().getIntent().getStringExtra(StackOverflowMainActivity.KEY_FIRST);
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
                    getActivity().finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast toast = Toast.makeText(getActivity(),
                        "ERROR WHILE LOADING DATA",
                        Toast.LENGTH_SHORT);
                toast.show();
                getActivity().finish();
            }
        });
    }

    static class ViewHolder {

        ImageView userImage;
        TextView userName;
        TextView title;
        TextView answerCount;
    }

    public class DataAdapter extends ArrayAdapter<DataHandler> {

        private final Context context;

        public DataAdapter(Context context, int resource, List<DataHandler> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;

            if (convertView == null) {
                LayoutInflater inflater =
                        (LayoutInflater) context.getSystemService
                                (Context.LAYOUT_INFLATER_SERVICE);

                convertView = inflater.inflate(R.layout.item_row, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.userImage = (ImageView) convertView.findViewById(R.id.user_image);
                viewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                viewHolder.answerCount = (TextView) convertView.findViewById(R.id.answer_count);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final DataHandler dateHandler = getItem(position);

            viewHolder.userImage.setImageResource(R.drawable.ic_launcher);
            Picasso.with(context)
                    .load(dateHandler.getOwner().getProfileImage())
                    .placeholder(getResources().getDrawable(R.drawable.ic_launcher))
                    .into(viewHolder.userImage);
            viewHolder.userName.setText(dateHandler.getOwner().getDisplayName());
            viewHolder.title.setText(dateHandler.getPostTitle());
            viewHolder.answerCount.setText(context.getString(R.string.answers) + dateHandler.answerCount());

            final String linkUrl = dateHandler.getLinkUrl();

            convertView.setClickable(true);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BrowserActivity.class);
                    intent.putExtra(KEY_SECOND, linkUrl);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}
