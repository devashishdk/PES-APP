package com.devashishthakur.pesapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;

public class SearchActivity extends AppCompatActivity {

    WebView webview;
    ProgressBar progressBar;
    EditText searchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        webview = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        searchText = (EditText) findViewById(R.id.search);

        if (Build.VERSION.SDK_INT >= 19) {
            webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webview.setVisibility(View.INVISIBLE);
        webview.getSettings().setJavaScriptEnabled(true);

        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);

        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);

                if(progressBar.getProgress() == 100)
                {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        webview.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                //webview.setVisibility(View.GONE);
                //Toast.makeText(MainActivity.this, "Page Started Loading", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                webview.setVisibility(View.VISIBLE);
                //Toast.makeText(MainActivity.this, "Page Loaded", Toast.LENGTH_SHORT).show();
                webview.loadUrl("javascript:(function() { " +

                        "document.getElementsByClassName('header')[0].style.display='gone';})()");

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                webview.setVisibility(View.GONE);
                //Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        String url = "https://www.pesmaster.com/pes-2019/";
        webview.loadUrl(url);
    }

    void onClickSearch(View view)
    {
        String search_text = searchText.getText().toString();
        webview.loadUrl("https://www.pesmaster.com/pes-2019/?q="+search_text);
    }

    @Override
    public void onBackPressed() {
        if(webview.canGoBack())
        {
            webview.goBack();
        }
    }

    void onBack(View view)
    {
        Intent intent = new Intent(SearchActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
