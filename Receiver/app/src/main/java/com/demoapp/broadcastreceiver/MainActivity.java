package com.demoapp.broadcastreceiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "BroadcastReceiverMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, this.toString());

        findViewById(R.id.action1).setOnClickListener(v -> {
            MyReceiver myReceiver = new MyReceiver();
            IntentFilter intentFilter = new IntentFilter("com.example.broadcast.ACTION1");
            registerReceiver(myReceiver, intentFilter);
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.action2).setOnClickListener(v -> {
            MyReceiver myReceiver2 = new MyReceiver();
            IntentFilter intentFilter2 = new IntentFilter("com.example.broadcast.ACTION2");
            registerReceiver(myReceiver2, intentFilter2);
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.mybotton3).setOnClickListener(v -> {
            Log.d(TAG, getApplicationContext().toString());

        });
    }

    private void openActivity() {
        Intent intent1 = new Intent(this, SecondActivity.class);
        Intent intent2 = new Intent().setClassName("com.android.mms",
                "com.android.mms.ui.NewMessageActivity");
        startActivity(intent1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册
//        unregisterReceiver(myReceiver);
    }
}