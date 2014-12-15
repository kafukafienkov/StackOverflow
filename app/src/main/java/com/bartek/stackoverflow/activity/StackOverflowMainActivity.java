package com.bartek.stackoverflow.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bartek.stackoverflow.R;


public class StackOverflowMainActivity extends Activity implements View.OnClickListener {

// last ok 19:55 15/12/2014

    private String dataToSearch;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_overflow_main);
        initButtonOnClickListener();
    }

    private void initButtonOnClickListener() {
        findViewById(R.id.search_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        editText = (EditText) findViewById(R.id.search_edit_text);
        dataToSearch = editText.getText().toString();

        passDataToSearchFor();
    }

    private void passDataToSearchFor() {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("key", getDataToSearch());
        startActivity(intent);
    }

    public String getDataToSearch() {
        return dataToSearch;
    }
}
