package com.demoapp.activitydemo;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private final String TAG = "ActivityDemoMain";
    public static int REQUEST_CODE = 1;
    ActivityResultLauncher<Intent> launcher;
    private Configuration oldConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ActivityDemo);
        setContentView(R.layout.activity_main);
        initLayout();

        registerActivityResult();

        findViewById(R.id.button1).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            Uri uri = Uri.parse("http://www.baidu.com");
            intent.setData(uri);
            intent = Intent.createChooser(intent, "Choose Activity");
            startActivity(intent);
        });

        findViewById(R.id.activity_local).setOnClickListener(v -> {
//            Intent intent1 = new Intent(Intent.ACTION_VIEW);
//            startActivity(intent1);
            openLocalActivity();
        });

        findViewById(R.id.activity_cross_app).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.putExtra(Intent.EXTRA_STREAM, "content://123");
            intent = Intent.createChooser(intent, "ABC");
            startActivity(intent);
//            openOtherApp();
        });

        findViewById(R.id.implicit_lunch).setOnClickListener(v -> {
            Intent intent1 = new Intent();
            intent1.setAction(Intent.ACTION_VIEW);
//            Uri uri = Uri.parse("content://com.demoapp.contentproviderdemo.provider/book");
//            intent1.setData(uri);
            startActivity(intent1);
        });

        findViewById(R.id.start_for_result).setOnClickListener(v -> {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

        findViewById(R.id.result_launcher).setOnClickListener(v -> {
            openActivityReturn();
        });
    }

    public void initLayout() {
        TextView textView;
        textView = findViewById(R.id.explicit_lunch_activity).findViewById(R.id.title);
        textView.setText("显式启动");
        textView = findViewById(R.id.implicit_lunch_activity).findViewById(R.id.title);
        textView.setText("隐式启动");
        textView = findViewById(R.id.send_result).findViewById(R.id.title);
        textView.setText("带返回值");
    }

    private void openLocalActivity() {
        // 方法1
        Intent intent1 = new Intent();
        intent1.setClass(this, SecondActivity.class);
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        // 方法2
        Intent intent2 = new Intent(this, SecondActivity.class);

        //方法3
        Intent intent3 = new Intent();
        ComponentName componentName = ComponentName.createRelative(this, ".ThirdActivity");
        intent3.setComponent(componentName);

        startActivity(intent1);
    }

    private void openOtherApp() {
        // 方法1
        Intent intent1 = new Intent();
        intent1.setClassName("com.android.camera",
                "com.android.camera.Camera");

        // 方法2
        Intent intent2 = new Intent();
        ComponentName componentName = ComponentName.createRelative("com.demoapp.dialogdemo",
                "com.demoapp.dialogdemo.MainActivity");
        intent2.setComponent(componentName);
//        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent1);
    }

    private void registerActivityResult() {
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == SecondActivity.RESULT_CODE) {
                            String value = result.getData().getStringExtra("key");
                            Toast.makeText(MainActivity.this, "返回值:" + value, Toast.LENGTH_SHORT).show();
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
            String value = data.getStringExtra("key");
            Toast.makeText(this, "返回值:" + value, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
//        new Exception(TAG).printStackTrace();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent");
    }

}

