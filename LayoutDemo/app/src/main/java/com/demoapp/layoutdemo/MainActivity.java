package com.demoapp.layoutdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.linear_layout).setOnClickListener(v -> {
            Intent intent=new Intent();
            intent.setClass(this, LinearActivity.class);
            startActivity(intent);
        });
    }
}