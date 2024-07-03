package com.demoapp.interfacedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.iflytek.hwc.appcontrol.AppControlManager;

public class SecondActivity extends AppCompatActivity {
    String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.button1).setOnClickListener(v -> {
            Log.i(TAG, "click");
            AppControlManager acm = new AppControlManager();
            acm.finishTask(this);
        });
    }
}