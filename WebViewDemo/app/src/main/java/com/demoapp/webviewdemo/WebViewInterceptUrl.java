package com.demoapp.webviewdemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;

import com.demoapp.webviewdemo.InterceptUrlManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @hide
 */
class WebViewInterceptUrl {
    private static final String TAG = "WebViewInterceptUrl";
    private static boolean DEBUG = Log.isLoggable(TAG, Log.DEBUG);
    private static String mDefaultBrowser = "com.toycloud.app.greenbrowser";
    private static volatile WebViewInterceptUrl mInstance;
    List<UrlRule> mUrlRules = new ArrayList<>();

    private WebViewInterceptUrl() {
    }

    public static WebViewInterceptUrl getInstance() {
        if (mInstance == null) {
            synchronized (WebViewInterceptUrl.class) {
                if (mInstance == null) {
                    mInstance = new WebViewInterceptUrl();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context, InterceptUrlManager manager) {
        mDefaultBrowser = getDefaultBrowser(context);
        String urlStrategy = manager.getInterceptRuleList(context.getPackageName());
        convert(urlStrategy);
    }

    // 将String转换为UrlRule
    public void convert(String urlStrategy) {
        List<Map<String, String>> mapList = restoreUrlStrategy(urlStrategy);
        for (Map<String, String> map : mapList) {
            UrlRule urlRule = new UrlRule();
            urlRule.mUrl = map.get("url");
            urlRule.isJumpToBrowser = map.get("isJumpToBrowser").equals("true");
            urlRule.isBack = map.get("isBack").equals("true");
            mUrlRules.add(urlRule);
        }
    }

    private List<Map<String, String>> restoreUrlStrategy(String urlStrategy) {
        List<Map<String, String>> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(urlStrategy);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Iterator<String> keys = jsonObject.keys();
                Map<String, String> map = new HashMap<>();
                while (keys.hasNext()) {
                    String key = keys.next();
                    map.put(key, (String) jsonObject.get(key));
                }
                list.add(map);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private Map<String, String> strToMap(String strMap) {
        Map<String, String> map = new HashMap<>();
        strMap = strMap.replaceAll("[{}]", "");
        String[] pairs = strMap.split(", ");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                map.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        return map;
    }

    private String getDefaultBrowser(Context context) {
        if (context == null) {
            return mDefaultBrowser;
        }
        String defaultBrowser = null;
        final Intent intent = new Intent();
        intent.setData(Uri.parse("https://www.example.com"));
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo != null && resolveInfo.activityInfo != null) {
            defaultBrowser = resolveInfo.activityInfo.packageName;
        }
        defaultBrowser = defaultBrowser == null ? mDefaultBrowser : defaultBrowser;
        if (DEBUG) {
            Log.d(TAG, "The default browser is " + defaultBrowser);
        }
        return defaultBrowser;
    }

    private Activity getActivityContext(Context context) {
        if (context == null)
            return null;
        else if (context instanceof Activity)
            return (Activity) context;
        else if (context instanceof ContextWrapper)
            return getActivityContext(((ContextWrapper) context).getBaseContext());

        return null;
    }

    public boolean isInterceptUrl(Context context, String url) {
        if (DEBUG) {
            Log.d(TAG, "Access url=" + url);
        }
        if (context == null) {
            if (DEBUG) {
                Log.d(TAG, "isInterceptUrl() context == null");
            }
            return false;
        }

        String packageName = context.getPackageName();
        if (mUrlRules == null || mUrlRules.size() == 0 || packageName == null ||
                url == null || mDefaultBrowser.equals(packageName)) {
            return false;
        }
        for (UrlRule urlRule : mUrlRules) {
            if (url.startsWith(urlRule.mUrl)) {
                return true;
            }
        }
        return false;
    }

    public void interceptByBrower(Context context, String url) {
        if (DEBUG) {
            Log.d(TAG, "Intercept url=" + url);
        }
        UrlRule curUrlRule = null;
        for (UrlRule urlRule : mUrlRules) {
            if (url.startsWith(urlRule.mUrl)) {
                curUrlRule = urlRule;
            }
        }
        if (curUrlRule == null || !curUrlRule.isJumpToBrowser) {
            return;
        }
        boolean isBack = curUrlRule.isBack;
        WeakReference<Context> weakContext = new WeakReference<>(context);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Context context = weakContext.get();
                if (context == null) {
                    if (DEBUG) {
                        Log.d(TAG, "interceptByBrower() context == null");
                    }
                    return;
                }
                Intent intent = new Intent();
                Uri uri = Uri.parse(url);
                intent.setData(uri);
                context.startActivity(intent);
                if (isBack) {
                    Activity activity = getActivityContext(context);
                    if (activity != null) {
                        KeyEvent keyDownEvent = new KeyEvent(0, 0,
                                KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK, 0);
                        activity.dispatchKeyEvent(keyDownEvent);
                        KeyEvent keyUpEvent = new KeyEvent(0, 0,
                                KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK, 0);
                        activity.dispatchKeyEvent(keyUpEvent);
                    }
                }
            }
        }, 200);
    }

    public static class UrlRule {
        public String mUrl = null;
        // 是否跳转到默认浏览器
        public boolean isJumpToBrowser = true;
        // 是否返回到原APP界面
        public boolean isBack = true;

        public UrlRule() {
        }

        public UrlRule(String url) {
            mUrl = url;
        }
    }
}
