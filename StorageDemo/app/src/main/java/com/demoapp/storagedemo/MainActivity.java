package com.demoapp.storagedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final int READ_WRITE_PERMISSION_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
        findViewById(R.id.internal_private).setOnClickListener(v -> {
            String path = getDataDir().getPath();
            Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.external_private).setOnClickListener(v -> {
            String path = getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath();
            Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.public_dir).setOnClickListener(v -> {
            String path = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                path = Environment.getExternalStorageDirectory().getPath();
            }
            path = Environment.getExternalStorageState(new File("/storage/emulated/0/"));
//            path = Environment.getExternalStorageState();

            Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.query_permission).setOnClickListener(v -> {
            String permission = "android.permission.READ_EXTERNAL_STORAGE";
            if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "拥有" + permission.split("\\.")[2] + "权限",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "没有" + permission.split("\\.")[2] + "权限",
                        Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.get_permission).setOnClickListener(v -> {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            "android.permission.READ_EXTERNAL_STORAGE",
                    },
                    READ_WRITE_PERMISSION_REQUEST);
        });
    }

    public void initLayout() {
        TextView title;
        title = findViewById(R.id.title_app_internal).findViewById(R.id.title);
        title.setText("APP内部私有目录");
        title = findViewById(R.id.title_app_external).findViewById(R.id.title);
        title.setText("APP外部私有目录");
        title = findViewById(R.id.title_public).findViewById(R.id.title);
        title.setText("公共目录");
        title = findViewById(R.id.permission).findViewById(R.id.title);
        title.setText("外部存储权限");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_WRITE_PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 用户授予了权限，可以执行相关操作
                    Toast.makeText(this, "申请" + permissions[0].split("\\.")[2] + "权限成功",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // 用户拒绝了权限，可以给出相应的提示或者禁用相关功能
                    Toast.makeText(this, "申请" + permissions[0].split("\\.")[2] + "权限失败",
                            Toast.LENGTH_SHORT).show();

                }
                break;

            default:
                break;
        }
    }
}