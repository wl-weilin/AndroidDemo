package com.demoapp.broadcastsender;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "BroadcastSenderMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.broadcast1).setOnClickListener(v -> {
//            sendImplicit(); // 隐式广播
            sendExplicit(); // 显式广播
        });

        findViewById(R.id.broadcast2).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("com.example.broadcast.ACTION2");
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            sendOrderedBroadcast(intent, null);
        });

        findViewById(R.id.button3).setOnClickListener(v -> {
            Log.d(TAG, this.toString());
        });
    }

    // 发送隐式广播
    public void sendImplicit() {
        Intent intent = new Intent();
        intent.setAction("com.example.broadcast.ACTION1");
//        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND | Intent.FLAG_RECEIVER_REPLACE_PENDING);
        sendBroadcast(intent);
//        sendOrderedBroadcast(intent, null);
    }

    //显式广播
    public void sendExplicit() {
        Intent intent = new Intent();
        intent.setClassName("com.demoapp.broadcastreceiver",
                "com.demoapp.broadcastreceiver.MyReceiver");
        intent.setAction("com.example.broadcast.ACTION1");
        sendBroadcast(intent);
    }
}


