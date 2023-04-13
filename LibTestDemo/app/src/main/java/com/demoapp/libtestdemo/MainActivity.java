package com.demoapp.libtestdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.demoapp.mylibrary.LibActivity;
import com.demoapp.mylibrary.MyLibClass;

public class MainActivity extends AppCompatActivity {
    String TAG = "LibTestDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(v -> {
            new MyLibClass().print();
            Log.d(TAG,"Button");
        });

        findViewById(R.id.button2).setOnClickListener(v -> {
            Intent intent = new Intent(this, LibActivity.class);
            startActivity(intent);
        });
    }
}