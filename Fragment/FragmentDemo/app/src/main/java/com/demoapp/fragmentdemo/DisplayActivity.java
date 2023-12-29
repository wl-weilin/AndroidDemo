package com.demoapp.fragmentdemo;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

public class DisplayActivity extends FragmentActivity {
    private final String TAG = "DisplayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.display_activity);
    }
}
