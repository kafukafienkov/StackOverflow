package com.bartek.stackoverflow.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.bartek.stackoverflow.R;

/**
 * author: Bartek
 */
public class BrowserActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        String url = getUrlToFollow();
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    private String getUrlToFollow() {
        Intent dataReceived = getIntent();
        String urlToFollow = dataReceived.getStringExtra("secondActivityKey");
        return urlToFollow;
    }
}


