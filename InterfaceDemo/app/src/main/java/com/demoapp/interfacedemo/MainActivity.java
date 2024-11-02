package com.demoapp.interfacedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.iflytek.hwc.appcontrol.AppControlManager;
import com.iflytek.hwc.audiostatistics.AudioStatisticsManager;
import com.iflytek.hwc.flipstructure.FlipStructureManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String TAG = "NewFKInterface";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(v -> {
            List<String> list = new ArrayList<>();
            Map<String, String> map = new HashMap<>();
            map.put("url", "https://m.ximalaya.com/xmkp-fe-service/policy/xxm-ting-privacy-policy");
            map.put("isJumpToBrowser", "true");
            map.put("isBack", "true");
            list.add(map.toString());

            boolean res = false;
            try {
                AppControlManager acm = new AppControlManager();
                res = acm.setForbiddenUrls("com.ximalaya.ting.kid", list);
                Log.i(TAG, String.valueOf(res));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.button2).setOnClickListener(v -> {

        });

        findViewById(R.id.button3).setOnClickListener(v -> {

        });

        findViewById(R.id.button4).setOnClickListener(v -> {

        });

        findViewById(R.id.button5).setOnClickListener(v -> {

        });
    }
}