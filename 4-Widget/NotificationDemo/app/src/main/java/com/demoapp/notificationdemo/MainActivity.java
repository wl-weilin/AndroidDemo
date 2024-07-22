package com.demoapp.notificationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private NotificationUtil notificationUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationUtil = new NotificationUtil(this);

        findViewById(R.id.Next).setOnClickListener(v -> {
            newActivity();
        });

        // 普通通知
        findViewById(R.id.notify_1).setOnClickListener(v -> {
            notificationUtil.normalNotification();
        });

        // 带按钮通知
        findViewById(R.id.notify_2).setOnClickListener(v -> {
            notificationUtil.buttonNotification();
        });

        // Style通知
        findViewById(R.id.notify_3).setOnClickListener(v -> {
            notificationUtil.styleNotification();
        });

        // RemoteView
        findViewById(R.id.notify_4).setOnClickListener(v -> {
            notificationUtil.showRemoteViewsNotification();
        });
    }

    private void newActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}