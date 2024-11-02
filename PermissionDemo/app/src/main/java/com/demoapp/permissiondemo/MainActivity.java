package com.demoapp.permissiondemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final int PERMISSION_STATUS_1 = 1;
    final int PERMISSION_STATUS_2 = 2;
    String permission = "android.permission.CAMERA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(v -> {
            requestPermissionsWhenRefuse(permission);
        });

        findViewById(R.id.check_permission).setOnClickListener(v -> {

            if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "拥有" + permission.split("\\.")[2] + "权限",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "没有" + permission.split("\\.")[2] + "权限",
                        Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.request_single_permission).setOnClickListener(v -> {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    PERMISSION_STATUS_1);
        });

        findViewById(R.id.request_mult_permission).setOnClickListener(v -> {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            "android.permission.ACCESS_FINE_LOCATION",
                            "android.permission.READ_EXTERNAL_STORAGE",
                            "android.permission.READ_CONTACTS"
                    },
                    PERMISSION_STATUS_2);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_STATUS_1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 用户授予了权限，可以执行相关操作
                    Toast.makeText(this, "申请" + permissions[0].split("\\.")[2] + "权限成功",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // 用户拒绝了权限，可以给出相应的提示或者禁用相关功能
                    Toast.makeText(this, "申请" + permissions[0].split("\\.")[2] + "权限失败",
                            Toast.LENGTH_SHORT).show();
                    requestPermissionsWhenRefuse(permissions[0]);
                }
                break;
            case PERMISSION_STATUS_2:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "申请" + permissions[i].split("\\.")[2] + "权限成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "申请" + permissions[i].split("\\.")[2] + "权限失败",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 当用户已经拒绝某权限，则再次申请权限。
     * 并显示Dialog框向用户解释为什么需要该权限
     */
    public void requestPermissionsWhenRefuse(String permission) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            return;
        }

        String msg = "使用XXX功能需要" + permission.split("\\.")[2] + "权限";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置对话框的标题和提示内容
        builder.setTitle("权限说明").setMessage(msg);
        builder.setNegativeButton("取消", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setPositiveButton("去设置", (dialog, which) -> {
            getAppDetailSettingIntent();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 跳转到APP的详细设置页
     */
    private void getAppDetailSettingIntent() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT > 8) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(intent);
    }


}