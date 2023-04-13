package com.demoapp.contentresolverdemo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    // 数据库名与表名，注意与Provider端一致
    public static final String DatabaseName = "People.db";
    public static final String TableName = "Person";
    public static final String Authority = "com.demoapp.contentproviderdemo.provider";
    public static final String uriString = "content://" + Authority + "/" + TableName + "/";

    private final String TAG = "ResolverDemoMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readFileProvider();

        EditText text_id = (EditText) findViewById(R.id.text_id);
        EditText text_name = (EditText) findViewById(R.id.text_name);

        findViewById(R.id.init_data).setOnClickListener(v -> {
            initData();
        });

        findViewById(R.id.get_type).setOnClickListener(v -> {
            Uri uri = Uri.parse(uriString);
            String mime=getContentResolver().getType(uri);
            Log.d(TAG, "mime = " + mime);
        });

        //查询数据，如果目标数据库为null，则不会输出
        findViewById(R.id.query_data).setOnClickListener(v -> {
            Uri uri = Uri.parse(uriString);
            Cursor cursor = getContentResolver().query(uri, null, null,
                    null, null);

            if (cursor == null) {
                Log.d(TAG, "cursor = " + cursor);
                return;
            }
            Log.d(TAG, "ColumnNames = " + Arrays.toString(cursor.getColumnNames()));
            int count = 0;
            //cursor依次指向每一行（表示一条数据），然后获取每一行的每个属性值
            while (cursor.moveToNext()) {
                count++;
                String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                Log.d(TAG, "id=" + id + "; " + "name=" + name);
            }

            cursor.close();
            Log.d(TAG, "count = " + count);
        });

        //添加数据，即添加一条数据到目标数据库中
        findViewById(R.id.add_data).setOnClickListener(v -> {
            Uri uri = Uri.parse(uriString);

            // 从编辑框中获取数据
            String input_id = text_id.getText().toString();
            String input_name = text_name.getText().toString();
            if (input_id.equals("") || input_name.equals("")) {
                Toast.makeText(getApplicationContext(), "id及name不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }

            ContentValues values = new ContentValues();
            values.put("id", Integer.parseInt(input_id));
            values.put("name", input_name);
            if (isValid(values)) {
                //insert()返回新建行的URL（包含行号），如content://com.demoapp.contentproviderdemo.provider/person/3
                Uri newUri = getContentResolver().insert(uri, values);
                Log.d(TAG, newUri.toString());
            }
        });

        //删除数据
        findViewById(R.id.delete_data).setOnClickListener(v -> {
            String input_id = text_id.getText().toString();
            Uri uri = Uri.parse(uriString + input_id);
            int res = getContentResolver().delete(uri, null, null);
            Log.d(TAG, res + "条数据被删除！");
            Log.d(TAG, String.valueOf(uri));
        });

        //更新数据，修改目标数据库中的某条数据
        findViewById(R.id.update_data).setOnClickListener(v -> {
            // 从编辑框中获取数据
            String input_id = text_id.getText().toString();
            String input_name = text_name.getText().toString();
            if (input_id.equals("") || input_name.equals("")) {
                Toast.makeText(getApplicationContext(), "id及name不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }

            //如果不指定newId，则会修改所有数据（newId不初始化默认为0）
            Uri uri = Uri.parse(uriString + Integer.parseInt(input_id));
            ContentValues values = new ContentValues();
            values.put("name", input_name);
            //更新数据，返回值表示有多少行数据被更新；如果没有指定uri数据，则res=0
            int res = getContentResolver().update(uri, values, null, null);
            if (res > 0) {
                Log.d(TAG, "数据更新成功！");
            }
        });

        findViewById(R.id.call).setOnClickListener(v -> {
            Bundle bundle = getContentResolver().call(Authority,
                    "method1", null, null);
            Log.d(TAG, bundle.toString());
            String result = bundle.getString("key");
            Log.d(TAG, result);
        });

        findViewById(R.id.by_intent).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            Uri uri = Uri.parse(uriString);
            intent.setData(uri);
            startActivity(intent);
        });

        findViewById(R.id.get_oaid).setOnClickListener(v -> {
            String oaid = getOAID();
            Log.d(TAG, oaid);
        });

        findViewById(R.id.grant_auth).setOnClickListener(v -> {
            try {
                Bundle bundle = getContentResolver().call("com.demoapp.filedemo.provider",
                        "getFileUri", null, null);
                String result_uri = bundle.getString("result_uri");
                Log.d(TAG, result_uri);

            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }

//            Uri uri=Uri.parse(result_uri);
//            Uri uri = Uri.parse("content://com.demoapp.contentproviderdemo.fileprovider/share_name/myfile.txt");
//            readFile(uri);
        });

        findViewById(R.id.read_file).setOnClickListener(v -> {
            Uri uri = Uri.parse("content://com.demoapp.filedemo.fileprovider/share_name/myfile.txt");
            readFile(uri);
        });
    }

    //读取由其它APP分享的文件
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

    private String getOAID() {
        Uri uri = Uri.parse("content://com.miui.idprovider/oaid");
        Cursor cursor = getContentResolver().query(uri, null, null,
                null, null);
        cursor.moveToNext();
        String oaid = cursor.getString(cursor.getColumnIndexOrThrow("uniform_id"));
        cursor.close();
//        Log.d(TAG, oaid);
        return oaid;
    }

    /**
     * 判断写入的数据是否合法
     *
     * @return 合法则返回true
     */
    public boolean isValid(ContentValues values) {
        int input_id = (int) values.get("id");
        String input_name = (String) values.get("name");
        if (input_id < 1000 || input_id > 10000) {
            Toast.makeText(getApplicationContext(), "ID 超过范围！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (input_name.length() < 2) {
            Toast.makeText(getApplicationContext(), "姓名不合规定！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void initData() {
        Uri uri = Uri.parse(uriString);
        // 删除全部数据
        int res = getContentResolver().delete(uri, null, null);
        Log.d(TAG, res + "条数据被删除！");

        ContentValues values = new ContentValues();
        // 插入第1条数据
        values.put("id", 1001);
        values.put("name", "赵一");
        getContentResolver().insert(uri, values);

        // 插入第2条数据
        values.clear(); //清除数据
        values.put("id", 1002);
        values.put("name", "钱二");
        getContentResolver().insert(uri, values);

        // 插入第3条数据
        values.clear(); //清除数据
        values.put("id", 1003);
        values.put("name", "孙三");
        getContentResolver().insert(uri, values);
    }
}