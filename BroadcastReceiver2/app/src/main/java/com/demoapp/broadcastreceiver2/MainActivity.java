package com.demoapp.broadcastreceiver2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String TAG = "BroadcastReceiver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.mybotton1).setOnClickListener(v -> {
            MyReceiver myReceiver = new MyReceiver();
            IntentFilter intentFilter = new IntentFilter("com.example.broadcast.ACTION1");
            registerReceiver(myReceiver, intentFilter);
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.mybotton2).setOnClickListener(v -> {
            MyReceiver myReceiver2 = new MyReceiver();
            IntentFilter intentFilter2 = new IntentFilter("com.example.broadcast.ACTION2");
            registerReceiver(myReceiver2, intentFilter2);
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册
//        unregisterReceiver(myReceiver);
    }
}