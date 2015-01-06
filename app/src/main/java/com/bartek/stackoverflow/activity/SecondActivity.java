package com.bartek.stackoverflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.bartek.stackoverflow.R;

/**
 * Created by Bartek on 2014-12-04.
 */
public class SecondActivity extends ActionBarActivity {

    private String stringReceived;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stack_overflow_main, menu);
        menu.findItem(R.id.action_back).setVisible(true);
        menu.findItem(R.id.action_refresh).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                introduceFragment();
                return true;
            case R.id.action_back:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        stringReceived = receiveDataToSearchFor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        introduceFragment();
    }

    public void introduceFragment() {
        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                .replace(R.id.my_fragment_holder, new MyListFragment(), "MY_LIST").commit();
    }


    public String receiveDataToSearchFor() {
        Intent intentReceive = getIntent();
        stringReceived = intentReceive.getStringExtra("key");
        return stringReceived;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}