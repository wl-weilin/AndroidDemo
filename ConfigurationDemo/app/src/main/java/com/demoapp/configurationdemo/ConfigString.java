package com.demoapp.configurationdemo;

import android.content.res.Configuration;
import android.util.Log;

public class ConfigString {
    private final static String TAG = "ConfigurationDemoMain";

    static public void getUIMode(Configuration current){
        String uimode = null;
        switch (current.uiMode & Configuration.UI_MODE_TYPE_MASK) {
            case Configuration.UI_MODE_TYPE_UNDEFINED:
                uimode = "未定义";
                break;
            case Configuration.UI_MODE_TYPE_NORMAL:
                uimode = "normal";
                break;
            case Configuration.UI_MODE_TYPE_DESK:
                uimode = "desk";
                break;
            case Configuration.UI_MODE_TYPE_CAR:
                uimode = "car";
                break;
            case Configuration.UI_MODE_TYPE_TELEVISION:
                uimode = "television";
                break;
            case Configuration.UI_MODE_TYPE_APPLIANCE:
                uimode = "appliance";
                break;
            case Configuration.UI_MODE_TYPE_WATCH:
                uimode = "watch";
                break;
            case Configuration.UI_MODE_TYPE_VR_HEADSET:
                uimode = "vrheadset";
                break;
            default:
                uimode = "unknow";
                break;
        }
        Log.d(TAG, "UI模式(设备类型)：" + uimode);

        switch (current.uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_NO:
                uimode = "浅色";
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                uimode = "深色";
                break;
            default:
                uimode = "unknow";
                break;
        }
        Log.d(TAG, "UI模式(颜色)：" + uimode);
    }

    static public void getColorMode(Configuration current){
        String colormode = null;
        switch (current.colorMode & Configuration.COLOR_MODE_WIDE_COLOR_GAMUT_MASK) {
            case Configuration.COLOR_MODE_WIDE_COLOR_GAMUT_NO:
                colormode = "非广色域";
                break;
            case Configuration.COLOR_MODE_WIDE_COLOR_GAMUT_YES:
                colormode = "广色域";
                break;
            default:
                colormode = "unknow";
                break;
        }
        Log.d(TAG, "色域：" + colormode);

        switch (current.colorMode & Configuration.COLOR_MODE_HDR_MASK) {
            case Configuration.COLOR_MODE_HDR_NO:
                colormode = "非HDR";
                break;
            case Configuration.COLOR_MODE_HDR_YES:
                colormode = "HDR";
                break;
            default:
                colormode = "unknow";
                break;
        }
        Log.d(TAG, "是否HDR：" + colormode);
    }
}
