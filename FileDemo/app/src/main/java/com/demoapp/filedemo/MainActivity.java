package com.demoapp.filedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "FileDemoMain";
    private final String AUTH = "com.demoapp.filedemo.fileprovider";
    private String fileName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileName = getFilesDir().getPath() + "/share_dir/myfile.txt";

        findViewById(R.id.create_file).setOnClickListener(v -> {
            Uri uri = creatFile(fileName);
            Log.d(TAG, uri.toString());
        });

        findViewById(R.id.write_file).setOnClickListener(v -> {
            writeFile(fileName, "Hello");
        });

        findViewById(R.id.read_file).setOnClickListener(v -> {
            try {
                readFile(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.grant_uri).setOnClickListener(v -> {
            grantUri(fileName);
        });

        findViewById(R.id.share_file).setOnClickListener(v -> {
            shareFileByActivity(fileName);
        });

        findViewById(R.id.button1).setOnClickListener(v -> {

        });
    }

    public Uri creatFile(String fileName) {
        File file = new File(fileName);

        //获取文件所在文件夹，如果不存在则先创建文件夹。否则直接创建文件会抛出错误
        File fileDir = file.getParentFile();
        assert fileDir != null;
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        try {
            //判断文件是否存在，存在就删除
            if (file.exists()) {
                Log.d(TAG, "文件存在，执行删除！");
                file.delete();
            }
            //创建文件
            file.createNewFile();
            Log.i(TAG, "文件创建成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取文件对应的content类型Uri
        Uri uri = FileProvider.getUriForFile(this, AUTH, file);

        return uri;
    }

    public void writeFile(String fileName, String content) {
        try {
            //要指定编码方式，否则会出现乱码
            OutputStreamWriter osw = new OutputStreamWriter(
                    new FileOutputStream(fileName, true), "gbk");
            osw.write(content);
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile(String fileName) throws IOException {
        File file = new File(fileName);
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            if (!file.exists()) {
                Log.d(TAG, "文件不存在！");
                return;
            }
            inputStreamReader = new InputStreamReader(Files.newInputStream(file.toPath()));
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!"".equals(line)) {
                    Log.d(TAG, line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
        }
    }

    public void shareFileByActivity(String fileName) {
        Intent intent = new Intent();
        // 在新Task中打开
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 设置action，授予读写权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setAction(Intent.ACTION_VIEW);
        // 为指定的file生成Uri
        Uri uri = FileProvider.getUriForFile(this, AUTH, new File(fileName));
        intent.setDataAndType(uri, "text/plain");
        startActivity(intent);
    }

    public void grantUri(String fileName) {
        File file = new File(fileName);
        // 需要Application的context，Activity
        Context context = getApplicationContext();
        Log.d(TAG, context.toString());
        Uri uri = FileProvider.getUriForFile(context, AUTH, file);
        grantUriPermission("com.demoapp.contentresolverdemo", uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        Log.d(TAG, uri.toString());
    }
}

