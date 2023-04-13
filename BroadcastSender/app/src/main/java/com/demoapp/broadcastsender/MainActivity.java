package com.demoapp.broadcastsender;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.mybotton1).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("com.example.broadcast.ACTION1");
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND|Intent.FLAG_RECEIVER_REPLACE_PENDING);
            sendBroadcast(intent);
            sendOrderedBroadcast(intent, null);
        });

        findViewById(R.id.mybotton2).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("com.example.broadcast.ACTION2");
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            sendOrderedBroadcast(intent, null);
        });

    }

}


