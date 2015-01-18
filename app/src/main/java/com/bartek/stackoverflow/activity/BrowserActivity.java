package com.bartek.stackoverflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;

import com.bartek.stackoverflow.R;
import com.bartek.stackoverflow.model.MyListFragment;

/**
 * author: Bartek
 */
public class BrowserActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        String url = getUrlToFollow();
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_stack_overflow_main, menu);
        menu.findItem(R.id.action_refresh).setVisible(false);
        menu.findItem(R.id.action_back).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String getUrlToFollow() {
        Intent dataReceived = getIntent();
        return dataReceived.getStringExtra(MyListFragment.KEY_SECOND);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


