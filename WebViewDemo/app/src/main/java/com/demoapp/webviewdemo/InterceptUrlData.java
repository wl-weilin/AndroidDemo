package com.demoapp.webviewdemo;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demoapp.webviewdemo.WebViewInterceptUrl.UrlRule;

public class InterceptUrlData {
    private static final String TAG = "InterceptList";
    public static HashMap<String, ArrayList<UrlRule>> iflyUrlRules = new HashMap<>();

    static {
        String packageName;

        packageName = "com.demoapp.webviewdemo";
        iflyUrlRules.put(packageName,
                new ArrayList<>(Arrays.asList(
                        new UrlRule("https://www.csdn.net/"),
                        new UrlRule("https://www.douban.com/"),
                        new UrlRule("https://www.taobao.com/")
                )));

        packageName = "com.ximalaya.ting.kid";
        iflyUrlRules.put(packageName,
                new ArrayList<>(Arrays.asList(
                        new UrlRule("https://m.ximalaya.com/xmkp-fe-service/policy/xxm-ting-privacy-policy")
                )));

        packageName = "com.yaerxing.fkst";
        iflyUrlRules.put(packageName,
                new ArrayList<>(Arrays.asList(
                        new UrlRule("https://www.yaerxing.com/shuati/privacyAgreement")
                )));

        packageName = "com.sevenplus.chinascience";
        iflyUrlRules.put(packageName,
                new ArrayList<>(Arrays.asList(
                        new UrlRule("https://zt.kepuchina.cn/app/privacy-policy.html")
                )));

        packageName = "com.sogou.translator";
        iflyUrlRules.put(packageName,
                new ArrayList<>(Arrays.asList(
                        new UrlRule( "https://fanyi.sogou.com/app/userAgreement"),
                        new UrlRule( "https://fanyi.sogou.com/app/privacyNew")
                )));

        packageName = "com.baidu.homework";
        iflyUrlRules.put(packageName,
                new ArrayList<>(Arrays.asList(
                        new UrlRule( "https://base-vue.zuoyebang.com/static/hy/base-vue/privacy-treaty.html")
                )));
    }

    public static Map<String, List<Map<String, String>>> hashMap = new HashMap<>();

    static {
        for (String packageName : InterceptUrlData.iflyUrlRules.keySet()) {
            ArrayList<UrlRule> urlRules = InterceptUrlData.iflyUrlRules.get(packageName);
            List<Map<String, String>> arrayList = new ArrayList<>();
            for (UrlRule curRule : urlRules) {
                HashMap<String, String> map = new HashMap<>();
                map.put("url", curRule.mUrl);
                map.put("isJumpToBrowser", String.valueOf(curRule.isJumpToBrowser));
                map.put("isBack", String.valueOf(curRule.isBack));
                arrayList.add(map);
            }
            hashMap.put(packageName, arrayList);
        }

        for (String packageName : InterceptUrlData.hashMap.keySet()) {
            List<Map<String, String>> urlRules = InterceptUrlData.hashMap.get(packageName);
            for (Map<String, String> curRule : urlRules) {
                Log.d(TAG, "pacakgeName=" + curRule.get("package") + "; " +
                        "url=" + curRule.get("url") + "; " +
                        "isJumpToBrowser=" + curRule.get("isJumpToBrowser") + "; " +
                        "isBack=" + curRule.get("isBack")
                );
            }
        }
        Log.d(TAG, String.valueOf(hashMap));
    }


}
