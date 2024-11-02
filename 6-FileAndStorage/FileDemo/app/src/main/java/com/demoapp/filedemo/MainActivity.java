package com.demoapp.filedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "FileDemoMain";
    public static final String AUTH = "com.demoapp.filedemo.fileprovider";
    private static final int GRANT_PERMISSION_RESULT = 2;
    private String fileName = null;
    // 本地操作的文件
    private File mLocalFile = null;
    // 通过FileProvider对外分享的文件
    private File mFileProviderDocu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
        mLocalFile = new File(getFilesDir(), "/share_dir/Hello.txt");
        mFileProviderDocu = new File(getFilesDir(), "/share_dir/Hello.txt");

        findViewById(R.id.create_file).setOnClickListener(v -> {
            Log.d(TAG, mLocalFile.getPath());
            Util.creatFile(this, mLocalFile);
        });

        findViewById(R.id.write_file).setOnClickListener(v -> {
            Util.writeFile(this, mLocalFile, "Hello");
        });

        findViewById(R.id.view_file).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(this, FileViewActivity.class);
            intent.setData(Uri.fromFile(mLocalFile));
            startActivity(intent);
        });

        findViewById(R.id.grant_uri_permission).setOnClickListener(v -> {
            String defaultPkg = "com.demoapp.documentviewdemo";
            String targetPkg = Objects.requireNonNull(getReferrer()).getAuthority();
            if (targetPkg == null || targetPkg.contains("com.android") || targetPkg.contains("launcher")) {
                targetPkg = defaultPkg;
            }
            Log.d(TAG, "targetPkg = " + targetPkg);
            Util.grantUri(this, mFileProviderDocu, targetPkg);
        });

        findViewById(R.id.grant_intent_permission).setOnClickListener(v -> {
            Uri uri = FileProvider.getUriForFile(this, AUTH, mFileProviderDocu);
            Intent intent = new Intent();
            intent.setData(uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            setResult(GRANT_PERMISSION_RESULT, intent);
            finish();
        });

        findViewById(R.id.share_provider_file).setOnClickListener(v -> {
            Util.shareFileProviderDocument(this, mFileProviderDocu);
        });

        findViewById(R.id.share_file).setOnClickListener(v -> {
            Uri uri = Uri.fromFile(mLocalFile);
            Log.d(TAG, "uri = " + uri.toString());

            File file = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOCUMENTS + "/files/Hello.txt");
            Log.d(TAG, "file = " + file.getPath());
            if (!file.exists()) {
                Toast.makeText(this, file.getName() + "不存在", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "text/plain");
            startActivity(intent);
        });

        findViewById(R.id.button1).setOnClickListener(v -> {
            Uri uri = FileProvider.getUriForFile(this, AUTH, mFileProviderDocu);

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
//            intent.setClassName("com.demoapp.documentviewdemo", "com.demoapp.documentviewdemo.SecondActivity");

            intent.setDataAndType(uri, "text/plain");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        });
    }

    public void initLayout() {
        TextView title;
        title = findViewById(R.id.divider1).findViewById(R.id.title);
        title.setText("本地文件操作");
        title = findViewById(R.id.divider2).findViewById(R.id.title);
        title.setText("对外分享文件");
    }

}

