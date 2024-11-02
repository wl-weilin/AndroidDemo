package com.demoapp.screendemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(v -> {

        });

        findViewById(R.id.screen_size).setOnClickListener(v -> {
            ScreenUtils.getRealScreenSize(this);
        });

        findViewById(R.id.physical_resolution).setOnClickListener(v -> {
            ScreenUtils.getRealScreenPixel(this);
        });

        findViewById(R.id.other_height).setOnClickListener(v -> {
            ScreenUtils.getStatusBarHeight(this);
            ScreenUtils.getNavigationBarHeight(this);
        });

        findViewById(R.id.available_resolution).setOnClickListener(v -> {
            ScreenUtils.getAvailablePixel(this);
        });

        findViewById(R.id.get_density).setOnClickListener(v -> {
            ScreenUtils.getDensity(this);
        });

        findViewById(R.id.get_xy_dpi).setOnClickListener(v -> {
            ScreenUtils.getXYDpi(this);
        });

        findViewById(R.id.get_diagonal_dpi).setOnClickListener(v -> {
            ScreenUtils.getDiagonalDpi(this);
        });

    }
}