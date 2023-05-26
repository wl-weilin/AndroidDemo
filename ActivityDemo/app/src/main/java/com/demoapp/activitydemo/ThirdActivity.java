package com.demoapp.activitydemo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    String TAG = "ThirdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG, "onCreate");

        findViewById(R.id.Previous).setOnClickListener(v -> {

        });

        findViewById(R.id.button1).setOnClickListener(v -> {

        });

    }

}
