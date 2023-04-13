package com.demoapp.appdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(v -> {
            openActivity();
        });
    }

    private void openActivity() {
        Intent intent1 = new Intent();
        intent1.setClass(this, SecondActivity.class);
        startActivity(intent1);
    }
}