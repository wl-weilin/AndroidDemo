package com.demoapp.fragmentdemo;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

public class DisplayActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_activity);
    }
}
