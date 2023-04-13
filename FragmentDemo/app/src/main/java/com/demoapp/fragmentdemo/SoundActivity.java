package com.demoapp.fragmentdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

public class SoundActivity extends FragmentActivity {
    private final String TAG = "SoundActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound_activity);
    }
}
