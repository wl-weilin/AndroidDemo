package com.demoapp.activitydemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

        findViewById(R.id.button1).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("key", "value");
            setResult(RESULT_CODE, intent);
            finish();
        });
    }

    private void newActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
//        Intent intent = new Intent(this, FourthActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
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
//        onBackPressed();
    }

}