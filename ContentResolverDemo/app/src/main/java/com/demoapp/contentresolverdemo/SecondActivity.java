package com.demoapp.contentresolverdemo;

import static com.demoapp.contentresolverdemo.MainActivity.uriString;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class SecondActivity extends AppCompatActivity {
    private final String TAG = "ResolverDemoMain";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        readFileProvider();

        //通过Intent打开指定数据类型的Activity，会调用到getType()
        findViewById(R.id.by_intent).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            Uri uri = Uri.parse(uriString);
            intent.setData(uri);
            startActivity(intent);
        });

        // 通过访问android:exported="true"的Provider，获取到FileProvider的授权
        findViewById(R.id.grant_auth).setOnClickListener(v -> {
            try {
                Bundle bundle = getContentResolver().call("com.demoapp.filedemo.provider",
                        "getFileUri", null, null);
                String result_uri = bundle.getString("result_uri");
                Log.d(TAG, result_uri);

            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        });

        // 读取FileProvider文件中的内容
        findViewById(R.id.read_file).setOnClickListener(v -> {
            Uri uri = Uri.parse("content://com.demoapp.filedemo.fileprovider/share_name/myfile.txt");
            readFile(uri);
        });

        // 获取设备的OAID
        findViewById(R.id.get_oaid).setOnClickListener(v -> {
            Uri uri = Uri.parse("content://com.miui.idprovider/oaid");
            Cursor cursor = getContentResolver().query(uri, null, null,
                    null, null);
            cursor.moveToNext();
            String oaid = cursor.getString(cursor.getColumnIndexOrThrow("uniform_id"));
            cursor.close();
            Log.d(TAG, oaid);
            Toast.makeText(this, "OAID: " + oaid, Toast.LENGTH_SHORT).show();
        });

        // 获取设备的VAID
        findViewById(R.id.get_vaid).setOnClickListener(v -> {
            Uri uri = Uri.parse("content://com.miui.idprovider/vaid");
            Cursor cursor = getContentResolver().query(uri, null, null,
                    null, null);
            cursor.moveToNext();
            String vaid = cursor.getString(cursor.getColumnIndexOrThrow("uniform_id"));
            cursor.close();
            Log.d(TAG, vaid);
            Toast.makeText(this, "VAID: " + vaid, Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.other).setOnClickListener(v -> {

        });
    }

    //通过Activity分享到此Activity中，调用此方法读取由其它APP分享的文件
    public void readFileProvider() {
        Intent intent = getIntent();
        if (intent != null && intent.getData() != null) {
            readFile(intent.getData());
        }
    }

    public void readFile(Uri uri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    getContentResolver().openFileDescriptor(uri, "r");
            FileReader reader = new FileReader(parcelFileDescriptor.getFileDescriptor());
            BufferedReader bufferedReader = new BufferedReader(reader);
            // 解析传来的数据，\A表示从字符串开头进行匹配
            String res = new Scanner(bufferedReader).useDelimiter("\\A").next();
            Log.d(TAG, res);
            parcelFileDescriptor.close();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }
}