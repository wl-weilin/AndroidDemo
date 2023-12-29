package com.demoapp.configurationdemo;

import static android.content.pm.ActivityInfo.CONFIG_LOCALE;
import static android.content.pm.ActivityInfo.CONFIG_ORIENTATION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "ConfigurationDemoMain";
    private Configuration oldConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oldConfig = getResources().getConfiguration();
        startService(new Intent(this, MyService.class));

        findViewById(R.id.config_orientation).setOnClickListener(v -> {
            Configuration config = getResources().getConfiguration();
            String orientation = null;
            switch (config.orientation) {
                case Configuration.ORIENTATION_LANDSCAPE:
                    orientation = "横屏";
                    break;
                case Configuration.ORIENTATION_PORTRAIT:
                    orientation = "竖屏";
                    break;
                case Configuration.ORIENTATION_UNDEFINED:
                    orientation = "unknow";
                    break;
                case Configuration.ORIENTATION_SQUARE:
                    break;
            }
            Log.d(TAG, "屏幕方向：" + orientation);
        });

        findViewById(R.id.config_locale).setOnClickListener(v -> {
            Configuration config = getResources().getConfiguration();
            String locale = String.valueOf(config.getLocales());
            Log.d(TAG, "地区：" + locale);
        });

        findViewById(R.id.config_dpi).setOnClickListener(v -> {
            Configuration config = getResources().getConfiguration();
            String str = String.valueOf(config.densityDpi);
            Log.d(TAG, "显示密度：" + str);
        });

        findViewById(R.id.config_screensize).setOnClickListener(v -> {
            Configuration config = getResources().getConfiguration();
            Log.d(TAG, "屏幕宽*高(dp)：" +
                    config.screenWidthDp + "*" + config.screenHeightDp);
            Log.d(TAG, "屏幕宽*高(px)：" +
                    config.screenWidthDp * config.densityDpi / 160 + "*" + config.screenHeightDp * config.densityDpi / 160);
        });

        findViewById(R.id.config_fontScale).setOnClickListener(v -> {
            Configuration config = getResources().getConfiguration();
            Log.d(TAG, "字体缩放比例：" + config.fontScale);
        });

        findViewById(R.id.config_uimode).setOnClickListener(v -> {
            Configuration config = getResources().getConfiguration();
            ConfigString.getUIMode(config);
        });

        findViewById(R.id.other_config).setOnClickListener(v -> {
            getOtherConfig();
        });

        findViewById(R.id.button1).setOnClickListener(v -> {
            startActivity(new Intent(this,SecondActivity.class));
        });
    }

    public void getOtherConfig() {
        Configuration config = getResources().getConfiguration();

        // 是否广色域或HDR
        ConfigString.getColorMode(config);

        // 键盘类型
        String keyboard = config.keyboard == Configuration.KEYBOARD_NOKEYS ? "无键盘"
                : config.keyboard == Configuration.KEYBOARD_QWERTY ? "普通键盘"
                : config.keyboard == Configuration.KEYBOARD_12KEY ? "数字键盘"
                : "未知键盘类型";
        Log.d(TAG, "键盘类型：" + keyboard);

        Log.d(TAG, "键盘无障碍功能：" + config.keyboardHidden);
        Log.d(TAG, "布局方向：" + config.getLayoutDirection());
        Log.d(TAG, "移动信号国家码：" + config.mcc);
        Log.d(TAG, "移动信号地区码：" + config.mnc);
        Log.d(TAG, "屏幕布局：" + config.screenLayout);
        Log.d(TAG, "实体屏幕尺寸：" + config.smallestScreenWidthDp);

        // 获得系统上方向导航的设备类型
        String naviName = config.navigation == Configuration.NAVIGATION_NONAV ? "没有方向控制"
                : config.navigation == Configuration.NAVIGATION_WHEEL ? "滚轮控制方向"
                : config.navigation == Configuration.NAVIGATION_DPAD ? "方向键控制方向"
                : "轨迹球控制方向";
        Log.d(TAG, "方向导航：" + naviName);

        // 获得系统触摸屏的触摸方式
        String touchName = config.touchscreen == Configuration.TOUCHSCREEN_FINGER ? "接受手指的触摸屏"
                : config.touchscreen == Configuration.TOUCHSCREEN_STYLUS ? "触摸笔式的触摸屏"
                : "无触摸屏";
        Log.d(TAG, "触摸方式：" + touchName);

        Log.d(TAG, "界面模式：" + config.uiMode);

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "MainActivity onConfigurationChanged");

        // 有变化的config在相应的掩码位置为1
        int diff = newConfig.diff(oldConfig);
        Log.d(TAG, "ConfigDiff = 0x" + Integer.toHexString(diff));
        Log.d(TAG, "newConfig = " + newConfig);

        // 屏幕方向改变
        if ((diff & CONFIG_ORIENTATION) != 0) {
            Log.d(TAG, "屏幕方向改变！");
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Log.d(TAG, "变更为横屏！");
            }
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Log.d(TAG, "变更为竖屏！");
            }
        }

        // 语言改变
        if ((diff & CONFIG_LOCALE) != 0) {
            Log.d(TAG, "语言改变！");
        }

        oldConfig = new Configuration(newConfig);
//        new Exception(TAG).printStackTrace();
    }
}