package com.demoapp.notificationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotificationUtil notificationUtil=new NotificationUtil(this);

        findViewById(R.id.Next).setOnClickListener(v -> {
            newActivity();
//            Intent intent = new Intent(this, SecondActivity.class);
//            startActivityForResult(intent, REQUEST_CODE);
        });

        findViewById(R.id.notify_1).setOnClickListener(v -> {
//            newActivity();
            notificationUtil.normalNotification();
        });
    }

    private void newActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}