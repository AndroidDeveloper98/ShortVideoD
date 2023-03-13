package com.example.shortvideod.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.databinding.DataBindingUtil;

import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ActivityWebviewBinding;


public class WebActivity extends BaseActivity {
    ActivityWebviewBinding binding;
    Intent i;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview);

        binding.webView.getSettings().setJavaScriptEnabled(true);

        loadUrl();

    }


    public void loadUrl() {
        binding.webView.getSettings().setSupportZoom(false);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.getSettings().setSupportMultipleWindows(true);
        binding.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        binding.webView.getSettings().setAllowFileAccess(false);
        binding.webView.getSettings().setBuiltInZoomControls(true);
        binding.webView.getSettings().setDisplayZoomControls(false);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl("https://www.myntra.com/");
                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                Log.d(">>>>", "onPageFinished: ");
            }
        });

        binding.webView.loadUrl("https://www.myntra.com/");

    }


}