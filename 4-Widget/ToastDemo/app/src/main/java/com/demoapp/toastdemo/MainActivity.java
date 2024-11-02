package com.demoapp.toastdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    String TAG = "ToastDemo";
    SnackbarDemo mSnackbarDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSnackbarDemo = new SnackbarDemo(this);

        findViewById(R.id.target_sdk).setOnClickListener(v -> {
            showTargetSdkInfo();
        });

        findViewById(R.id.normal_toast).setOnClickListener(v -> {
            showNormalToast();
        });

        findViewById(R.id.custom_toast1).setOnClickListener(v -> {
            showCustomToast();
        });

        findViewById(R.id.custom_toast2).setOnClickListener(v -> {
            showCustomToast2();
        });

        findViewById(R.id.normal_snackbar).setOnClickListener(v -> {
            mSnackbarDemo.showNormalSnackbar(findViewById(R.id.normal_snackbar));
        });

        findViewById(R.id.custom_snackbar).setOnClickListener(v -> {
            mSnackbarDemo.showCustomSnackbar(null);
        });

        findViewById(R.id.snackbar_like_toast).setOnClickListener(v -> {
            mSnackbarDemo.showSnackbarLikeToast();
        });

        findViewById(R.id.snackbar_like_toast_layout).setOnClickListener(v -> {
            mSnackbarDemo.showSnackbarLikeToastByLayout();
        });
    }

    /**
     * (1) 默认文本Toast且TargetSdk > Android 10。Toast显示会经过APP、system_server、SystemUI三个进程
     * (2) 自定义View的Toast。Toast显示会经过APP、system_server、APP三个进程
     * (3) 默认文本Toast且TargetSdk <= Android 10，同自定义的View
     * (4) 默认文本Toast且OS版本 <= Android 10，同自定义的View
     */
    public void showTargetSdkInfo() {
        if (Double.parseDouble(Build.VERSION.RELEASE) < 11) {
            Toast.makeText(this, "OS Version=" + Double.parseDouble(Build.VERSION.RELEASE) + ": 非SystemUI流程",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // 表示APP的targetSdk
        int targetSdkVersion;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            targetSdkVersion = packageInfo.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (targetSdkVersion <= Build.VERSION_CODES.Q) {
            Toast.makeText(this, "APP targetSdk=" + targetSdkVersion + ": 非SystemUI流程",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "APP targetSdk=" + targetSdkVersion + ": SystemUI流程",
                    Toast.LENGTH_SHORT).show();
        }

    }

    // 显示普通Toast
    public void showNormalToast() {
        Toast.makeText(this, "普通Toast", Toast.LENGTH_SHORT).show();
    }

    // 显示自定义Toast
    public void showCustomToast() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.custom_toast, null);
        TextView mText = mView.findViewById(R.id.toast_text);
        Toast mToast = new Toast(this);
        mToast.setView(mView);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.BOTTOM, 0, 180);
        mToast.show();
    }

    public void showCustomToast2() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.ifly_transient_notification, null);
        TextView mText = mView.findViewById(R.id.message);
        mText.setText("自定义Toast");
        Toast mToast = new Toast(this);
        mToast.setView(mView);
        mToast.setDuration(Toast.LENGTH_SHORT);
//        mToast.setGravity(Gravity.BOTTOM, 0, 180);
        mToast.show();
    }

}