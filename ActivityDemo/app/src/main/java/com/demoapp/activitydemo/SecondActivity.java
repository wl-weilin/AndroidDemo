package com.demoapp.activitydemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.nio.ByteBuffer;

public class SecondActivity extends AppCompatActivity {
    public static int RESULT_CODE = 2;
    String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG, "onCreate");

        findViewById(R.id.Previous).setOnClickListener(v -> {
            newActivity();
        });

        final int[] count = {0};
        findViewById(R.id.button1).setOnClickListener(v -> {
            int sizeInBytes = 1024 * 1024 * 20 * (count[0]++); // 10M
            ByteBuffer.allocateDirect(sizeInBytes);
            Log.i(TAG, "内存使用上限:" + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "MB");
            Log.i(TAG, "已申请内存:" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB");
            Log.i(TAG, "已申请但未使用:" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "MB");

        });
    }

    private void newActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void newTaskActivity() {
        Intent intent = new Intent(this, FourthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Android");
        builder.setMessage("Hello Android!");
        builder.setPositiveButton("我知道了",
                (dialogInterface, i) -> {

                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

}