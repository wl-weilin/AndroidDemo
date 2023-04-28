package com.demoapp.binderserverdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    String TAG = "BinderServerDemoMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.register).setOnClickListener(v -> {

        });

        findViewById(R.id.button2).setOnClickListener(v -> {
            Log.i(TAG, "Hello");

        });

    }
}