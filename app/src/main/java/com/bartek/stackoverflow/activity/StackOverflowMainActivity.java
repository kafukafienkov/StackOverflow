package com.bartek.stackoverflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.bartek.stackoverflow.R;

public class StackOverflowMainActivity extends ActionBarActivity implements View.OnClickListener {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stack_overflow_main, menu);
        menu.findItem(R.id.action_refresh).setVisible(false);
        menu.findItem(R.id.action_back).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
}
