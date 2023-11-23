package com.demoapp.activitydemo;


import static android.content.pm.ActivityInfo.CONFIG_LOCALE;
import static android.content.pm.ActivityInfo.CONFIG_ORIENTATION;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private final String TAG = "ActivityDemoMain";
    public static int REQUEST_CODE = 1;
    ActivityResultLauncher<Intent> launcher;
    private Configuration oldConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ActivityDemo);
        setContentView(R.layout.activity_main);

        oldConfig = getResources().getConfiguration();
//        registerReturn();

        findViewById(R.id.activity_local).setOnClickListener(v -> {
            Log.i(TAG,"内存使用上限:"+Runtime.getRuntime().maxMemory() / 1024 / 1024 + "MB");
            Log.i(TAG, "已申请内存:" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB");
            Log.i(TAG, "已申请但未使用:" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "MB");
//            openLocalActivity();
        });

        findViewById(R.id.activity_cross_app).setOnClickListener(v -> {
            openOtherApp();
        });

        findViewById(R.id.activity_return).setOnClickListener(v -> {
            openActivityReturn();
        });

        findViewById(R.id.activity_implicit).setOnClickListener(v -> {
            Intent intent1 = new Intent();
            intent1.setAction(Intent.ACTION_MAIN);
//            Uri uri = Uri.parse("content://com.demoapp.contentproviderdemo.provider/book");
//            intent1.setData(uri);
            startActivity(intent1);
        });

        findViewById(R.id.button1).setOnClickListener(v -> {
            File file1 = new File("./../demoapp");
            Log.i(TAG, file1.getAbsolutePath());
        });
    }

    private void openLocalActivity() {
        // 方法1
        Intent intent1 = new Intent();
        intent1.setClass(this, SecondActivity.class);
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // 方法2
        Intent intent2 = new Intent(this, SecondActivity.class);

        //方法3
        Intent intent3 = new Intent();
        ComponentName componentName = ComponentName.createRelative(this, ".ThirdActivity");
        intent3.setComponent(componentName);

        startActivity(intent1);
    }

    private void openOtherApp() {
        // 方法1
        Intent intent1 = new Intent();
        intent1.setClassName("com.android.camera",
                "com.android.camera.Camera");

        // 方法2
        Intent intent2 = new Intent();
        ComponentName componentName = ComponentName.createRelative("com.demoapp.dialogdemo",
                "com.demoapp.dialogdemo.MainActivity");
        intent2.setComponent(componentName);
//        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent1);
    }

    private void registerReturn() {
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == SecondActivity.RESULT_CODE) {
                            Log.d(TAG, "return = " + result.getData().getStringExtra("key"));
                        }
                    }
                });
    }

    // 有返回值的Activity
    private void openActivityReturn() {
        Intent intent = new Intent(this, SecondActivity.class);
//        startActivityForResult(intent, REQUEST_CODE); //回调到onActivityResult()
        launcher.launch(intent);    //回调到registerReturn()中
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == SecondActivity.RESULT_CODE) {
            Log.d(TAG, data.getStringExtra("key"));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
//        new Exception(TAG).printStackTrace();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged");

        // 有变化的config在相应的掩码位置为1
        int diff = newConfig.diff(oldConfig);
        Log.d(TAG, "ConfigDiff = 0x" + Integer.toHexString(diff));

        if ((diff & CONFIG_ORIENTATION) != 0) {
            Log.d(TAG, "屏幕方向改变！");

            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Log.d(TAG, "变更为横屏！");
            }
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Log.d(TAG, "变更为竖屏！");
            }
        }

        if ((diff & CONFIG_LOCALE) != 0) {
            Log.d(TAG, "语言改变！");
        }

        oldConfig = new Configuration(newConfig);
//        new Exception(TAG).printStackTrace();
    }


}

