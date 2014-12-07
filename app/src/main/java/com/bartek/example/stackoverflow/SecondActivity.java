package com.bartek.example.stackoverflow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Bartek on 2014-12-04.
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);

        receiveDataToSearchFor();

    }

    private void receiveDataToSearchFor() {
        Intent intentReceive = getIntent();
        String stringReceived = intentReceive.getStringExtra("key");
        TextView textView = new TextView(this);
        textView.setText(stringReceived.toString());

        setContentView(textView);
    }
}
