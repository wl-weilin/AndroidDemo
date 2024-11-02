package com.demoapp.webviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class WebViewActivity extends AppCompatActivity {
    String TAG = "WebViewActivity";
    WebView mWebView;
    WebViewInterceptUrl mWebViewInterceptUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        initWebView();

        findViewById(R.id.button1).setOnClickListener(v -> {
            KeyEvent keyDownEvent = new KeyEvent(0, 0,
                    KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK, 0);
            dispatchKeyEvent(keyDownEvent);
            KeyEvent keyUpEvent = new KeyEvent(0, 0,
                    KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK, 0);
            dispatchKeyEvent(keyUpEvent);

        });

        Intent intent = getIntent();
        String url = getUrl(intent);
        Log.i(TAG, url);

//        if (mWebViewInterceptUrl.isInterceptUrl(mWebView.getContext(), url)) {
//            UrlRule urlRule = mWebViewInterceptUrl.getUrlRule();
//            mWebViewInterceptUrl.interceptByBrower(mWebView.getContext(), urlRule);
//            return;
//        }
        mWebView.loadUrl(url);
    }

    public String getUrl(Intent intent) {
        String url = null;
        // 获取setDate()的url
        Uri uri = intent.getData();
        if (uri != null) {
            Log.i(TAG, uri.toString());
            url = String.valueOf(uri);
            return url;
        }

        // 获取putExtra的url
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Log.i(TAG, bundle.toString());
            url = bundle.getString("URL");
        }
        return url;
    }

    public void initWebView() {
        mWebView = findViewById(R.id.web_view);
        mWebView.setWebViewClient(new WebViewClient());
        mWebViewInterceptUrl = WebViewInterceptUrl.getInstance();
    }

//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Log.i(TAG,"执行onBackPressed()");
//        }
//        return super.onKeyUp(keyCode, event);
//    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "执行onBackPressed()");
        super.onBackPressed();
    }
}