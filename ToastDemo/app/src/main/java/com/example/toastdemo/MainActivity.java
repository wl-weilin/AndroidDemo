package com.example.toastdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String TAG = "ToastDemo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(v -> {
            showTosst();
        });
    }

    // 显示普通Toast
    public void showTosst(){
        Toast.makeText(getApplicationContext(), "一个Toast", Toast.LENGTH_SHORT).show();
    }
}

