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

import com.iflytek.hwc.appcontrol.AppControlManager;
import com.iflytek.hwc.audiostatistics.AudioStatisticsManager;
import com.iflytek.hwc.flipstructure.FlipStructureManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String TAG = "NewFKInterface";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.button1).setOnClickListener(v -> {
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1 = Intent.createChooser(intent1, "ABC");
            startActivity(intent1);

//            try {
//                AppControlManager acm = new AppControlManager();
//                boolean res=acm.inFreeformWindowingMode();
//                Log.i(TAG, String.valueOf(res));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        });

        findViewById(R.id.button2).setOnClickListener(v -> {
//            AppControlManager acm = new AppControlManager();
//            acm.finishTask(this);
            try {
                AppControlManager acm = new AppControlManager();
                boolean res=acm.setFreeformWindowPosition(0);
                Log.i(TAG, String.valueOf(res));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.button3).setOnClickListener(v -> {
            try {
                AppControlManager acm = new AppControlManager();
                boolean res=acm.setFreeformWindowPosition(1);
                Log.i(TAG, String.valueOf(res));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.button4).setOnClickListener(v -> {
            try {
                AppControlManager acm = new AppControlManager();
                boolean res=acm.setFreeformWindowPosition(2);
                Log.i(TAG, String.valueOf(res));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.button5).setOnClickListener(v -> {
            try {
                AppControlManager acm = new AppControlManager();
                boolean res=acm.setFreeformWindowPosition(3);
                Log.i(TAG, String.valueOf(res));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}