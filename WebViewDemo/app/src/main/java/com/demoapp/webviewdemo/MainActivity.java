package com.demoapp.webviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.content.pm.PackageManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "WebViewDemo";
    String url = "https://www.baidu.com/";

    WebView mWebView;
    View webViewLinearLayout;
    WebViewInterceptUrl mWebViewInterceptUrl;
    public List<String> mUrlRules;
    InterceptUrlManager mInterceptUrlManager=new InterceptUrlManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWebView();


        findViewById(R.id.button1).setOnClickListener(v -> {
            mInterceptUrlManager.init(this);
            mWebViewInterceptUrl = WebViewInterceptUrl.getInstance();
            mWebViewInterceptUrl.init(this,mInterceptUrlManager);
        });

        findViewById(R.id.open_url_this_activity).setOnClickListener(v -> {
            ((ViewGroup) findViewById(android.R.id.content)).addView(webViewLinearLayout);

//            if (mWebViewInterceptUrl.isInterceptUrl(mWebView.getContext(), url)) {
//                mWebViewInterceptUrl.interceptByBrower(mWebView.getContext(), url);
//                return;
//            }

            String str = mInterceptUrlManager.getInterceptRuleList(this.getPackageName());
            if (mUrlRules != null) {
                for(String json: mUrlRules){
                    Map<String, String> rule=jsonToMap(json);
                    if(rule.get("url").equals(url)){
                        mWebViewInterceptUrl.interceptByBrower(mWebView.getContext(), url);
                    }
                }
            }
            mWebView.loadUrl(url);

        });

        findViewById(R.id.open_url_other_activity).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(this, WebViewActivity.class);
//            intent.putExtra("URL", url);
            intent.setData(Uri.parse("https://www.baidu.com/"));
            startActivity(intent);
        });

        findViewById(R.id.open_url_default_brower).setOnClickListener(v -> {
            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            startActivity(intent);
        });

        findViewById(R.id.query_default_brower).setOnClickListener(v -> {
            String defaultBrowser = null;
            final Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_VIEW);
//            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://www.example.com"));
            ResolveInfo resolveInfo = this.getPackageManager().resolveActivity(intent,
                    PackageManager.MATCH_DEFAULT_ONLY);
            if (resolveInfo != null && resolveInfo.activityInfo != null) {
                defaultBrowser = resolveInfo.activityInfo.packageName;
            }
            Toast.makeText(this, "默认浏览器：" + defaultBrowser, Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.read_xml).setOnClickListener(v -> {
            mInterceptUrlManager.readInterceptUrlConfig(this);
        });

        findViewById(R.id.create_xml).setOnClickListener(v -> {
            mInterceptUrlManager.writeInterceptUrlConfig(this);
        });

    }

    public void initWebView() {
        // webView =findViewById(R.id.web_view);

        LayoutInflater inflater = LayoutInflater.from(this);
        webViewLinearLayout = inflater.inflate(R.layout.activity_web_view, null);
        mWebView = webViewLinearLayout.findViewById(R.id.web_view);
        mWebView.setWebViewClient(new WebViewClient());

    }

    private Map<String, String> jsonToMap(String json) {
        Gson gson = new Gson();
        Map<String, String> restoredMap = gson.fromJson(json, HashMap.class);
        return restoredMap;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.i(TAG, "执行onKeyUp() Back");
            if (webViewLinearLayout.isShown()) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack(); // 返回上一页面
                    return true;
                } else {
                    ((ViewGroup) findViewById(android.R.id.content)).removeView(webViewLinearLayout);
                    return true;
                }
            }
        }
        return super.onKeyUp(keyCode, event);
    }

//    @Override
//    public void onBackPressed() {
//        Log.i(TAG,"执行onBackPressed()");
//        if (webViewLinearLayout.isShown()) {
//            if (mWebView.canGoBack()) {
//                mWebView.goBack(); // 返回上一页面
//            } else {
//                ((ViewGroup) findViewById(android.R.id.content)).removeView(webViewLinearLayout);
//            }
//        }
//        super.onBackPressed();
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: //即 REQUEST_WRITE=1
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "申请权限成功",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "您拒绝了SD卡访问权限",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}