package com.demoapp.activitydemo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class FourthActivity extends AppCompatActivity {

    String TAG = "FourthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        Log.d(TAG, "onCreate");

        findViewById(R.id.Previous).setOnClickListener(v -> {

        });

    }

}