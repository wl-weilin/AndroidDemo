package com.demoapp.activitydemo;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    private final String TAG = "ActivityDemo";
    public static int REQUEST_CODE = 1;
    ActivityResultLauncher<Intent> launcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        registerReturn();

        findViewById(R.id.activity_local).setOnClickListener(v -> {
            openLocalActivity();
        });

        findViewById(R.id.activity_cross_app).setOnClickListener(v -> {
            openOtherApp();
        });

        findViewById(R.id.activity_return).setOnClickListener(v -> {
            openActivityReturn();
        });

        findViewById(R.id.button2).setOnClickListener(v -> {
            Intent intent = new Intent(this, SecondActivity.class);
            registerReturn();
        });
    }

    private void openLocalActivity() {
        // 方法1
        Intent intent1 = new Intent();
        intent1.setClass(this, SecondActivity.class);

        // 方法2
        Intent intent2 = new Intent(this, SecondActivity.class);

        //方法3
        Intent intent3 = new Intent();
        ComponentName componentName = ComponentName.createRelative(this, ".ThirdActivity");
        intent3.setComponent(componentName);

        startActivity(intent1);
    }

    private void openOtherApp() {
        Intent intent1 = new Intent();
        intent1.setClassName("com.demoapp.notificationdemo",
                "com.demoapp.notificationdemo.MainActivity");

        // 方法2
        Intent intent2 = new Intent();
        ComponentName componentName = ComponentName.createRelative("com.demoapp.notificationdemo",
                "com.demoapp.notificationdemo.MainActivity");
        intent2.setComponent(componentName);

        startActivity(intent1);
    }

    private void registerReturn() {
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == SecondActivity.RESULT_CODE) {
                            Log.d(TAG, "return = " + result.getData().getStringExtra("key"));
                        }
                    }
                });
    }

    // 有返回值的Activity
    private void openActivityReturn() {
        Intent intent = new Intent(this, SecondActivity.class);
//        startActivityForResult(intent, REQUEST_CODE); //回调到onActivityResult()
        launcher.launch(intent);    //回调到registerReturn()中
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == SecondActivity.RESULT_CODE) {
            Log.d(TAG, data.getStringExtra("key"));
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        finish();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}

