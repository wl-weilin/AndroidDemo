package com.demoapp.filedemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;

import androidx.core.content.FileProvider;

import static com.demoapp.filedemo.MainActivity.AUTH;

public class Util {
    private final static String TAG = "FileDemoMain";

    public static void creatFile(Context context, File file) {
        //获取文件所在文件夹，如果不存在则先创建文件夹。否则直接创建文件会抛出错误
        File fileDir = file.getParentFile();
        assert fileDir != null;
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        try {
            //判断文件是否存在，存在就删除
            if (file.exists()) {
                Toast.makeText(context, "文件存在，执行删除！", Toast.LENGTH_SHORT).show();
                file.delete();
            }
            //创建文件
            file.createNewFile();
            Toast.makeText(context, "文件创建成功！", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(Context context, File file, String content) {
        if (!file.exists()) {
            Toast.makeText(context, "文件不存在！", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            //要指定编码方式，否则会出现乱码
            OutputStreamWriter osw = new OutputStreamWriter(
                    new FileOutputStream(file, true), "gbk");
            osw.write(content);
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "写入成功！", Toast.LENGTH_SHORT).show();
    }

    public static void viewFile(Activity activity, File file) {
        if (!file.exists()) {
            Toast.makeText(activity, "文件不存在！", Toast.LENGTH_SHORT).show();
            return;
        }

        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();
        try {
            inputStreamReader = new InputStreamReader(Files.newInputStream(file.toPath()));
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!"".equals(line)) {
                    Log.d(TAG, line);
                }
                sb.append(line).append("\n");
            }

            bufferedReader.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextView textView = activity.findViewById(R.id.txt_content);
        textView.setVisibility(View.VISIBLE);
        textView.setText(sb.toString());
    }

    /**
     * 使用其它APP打开通过FileProvider对外分享的文档
     */
    public static void shareFileProviderDocument(Context context, File file) {
        Uri uri = null;
        // 为指定的file生成content://类型的Uri
        try {
//            uri = FileProvider.getUriForFile(context, AUTH, file);
            uri = FileProvider.getUriForFile(context, AUTH, file,"abc.txt");
        } catch (IllegalArgumentException e) {
            Toast.makeText(context, file.getName() + "不在共享路径中！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return;
        }
        Log.d(TAG, "uri = " + uri);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "text/plain");
        // 在新Task中打开
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 设置action，授予读写权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        context.startActivity(intent);
    }

    /**
     * 为指定的外部APP授予当前APP下某文件的读写权限
     * 注：对外分享文件必须通过FileProvider方式，不能通过file://方式
     */
    public static void grantUri(Context context, File file, String targetPkg) {
        Uri uri = FileProvider.getUriForFile(context, AUTH, file);
        Log.d(TAG, uri.toString());
        Context appContext = context.getApplicationContext();
        context.grantUriPermission(targetPkg, uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Toast.makeText(context, targetPkg + ": 获取Uri{" + uri + "}的读写权限", Toast.LENGTH_SHORT).show();
    }

}
