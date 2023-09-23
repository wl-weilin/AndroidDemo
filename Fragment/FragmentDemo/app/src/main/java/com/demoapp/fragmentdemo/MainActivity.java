package com.demoapp.fragmentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

/**
 * R.layout.activity_main有两种布局
 * 一个是手机尺寸，一个是平板尺寸layout-large
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = "FragmentDemo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}