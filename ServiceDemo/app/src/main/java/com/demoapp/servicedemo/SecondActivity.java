package com.demoapp.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.Previous).setOnClickListener(v -> {
            newActivity();
        });
    }

    private void newActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}