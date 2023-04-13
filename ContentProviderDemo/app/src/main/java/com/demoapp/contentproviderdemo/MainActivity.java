package com.demoapp.contentproviderdemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import static com.demoapp.contentproviderdemo.MyDatabaseHelper.DatabaseName;
import static com.demoapp.contentproviderdemo.MyDatabaseHelper.TableName;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private final String TAG = "ProviderDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this, DatabaseName, null, 2);

        findViewById(R.id.create_database).setOnClickListener(v -> {
            //打开数据库，已存在则直接打开，否则创建一个新的数据库
            dbHelper.getWritableDatabase();
        });

        //查询并打印数据
        findViewById(R.id.query_data).setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // 查询表中所有的数据
            Cursor cursor = db.query(TableName, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do { // 遍历Cursor对象，取出数据并打印
                    String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    Log.d(TAG, "id=" + id + "; " + "name=" + name);
                } while (cursor.moveToNext());
            }
            cursor.close();
        });

        //添加数据
        findViewById(R.id.add_data).setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            long res = -1;   //数据库操作的返回值，判断是否成功
            // 从编辑框中获取数据
            int input_id = Integer.parseInt(((EditText) findViewById(R.id.text_id)).getText().toString());
            String input_name = ((EditText) findViewById(R.id.text_name)).getText().toString();
            ContentValues values = new ContentValues();
            values.put("id", input_id);
            values.put("name", input_name);
            // 判断数据有效性和是否存在
            if (isValid(values) && !isExist(values)) {
                res = db.insert(TableName, null, values);
            }

            if (res != -1) {    // 插入结果=-1表示失败
                Toast.makeText(getApplicationContext(), "Add succeeded", Toast.LENGTH_SHORT).show();
            }
        });

        //删除符合条件的一些数据
        findViewById(R.id.delete_data).setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // 按照ID删除
            int input_id = Integer.parseInt(((EditText) findViewById(R.id.text_id)).getText().toString());
            // 删除id = input_id的人
            int res = db.delete(TableName, "id = ?", new String[]{String.valueOf(input_id)});
            if (res > 0)
                Toast.makeText(getApplicationContext(), "Delete succeeded", Toast.LENGTH_SHORT).show();
            else Toast.makeText(getApplicationContext(), "没有数据被删除！", Toast.LENGTH_SHORT).show();
        });

        //删除全部数据
        findViewById(R.id.delete_all).setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            int res = db.delete(TableName, null, null);
            if (res > 0)
                Toast.makeText(getApplicationContext(), "Delete succeeded", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "没有数据被删除！", Toast.LENGTH_SHORT).show();
        });

        //更新数据，即修改已存在的数据
        findViewById(R.id.update_data).setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // 从编辑框中获取数据
            int input_id = Integer.parseInt(((EditText) findViewById(R.id.text_id)).getText().toString());
            String input_name = ((EditText) findViewById(R.id.text_name)).getText().toString();
            ContentValues values = new ContentValues();
            values.put("name", input_name);

            //将id为input_id的人的名字改为input_name
            int res = db.update(TableName, values, "id = ?", new String[]{String.valueOf(input_id)});
            if (res > 0)
                Toast.makeText(getApplicationContext(), "Update succeeded", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.invoke_call).setOnClickListener(v -> {
            Bundle bundle=getContentResolver().call("com.demoapp.contentproviderdemo.provider",
                    "getFileUri",null,null);
            Log.d(TAG, bundle.toString());
        });

        findViewById(R.id.file_activity).setOnClickListener(v -> {

        });

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

    /**
     * 某个id的values在数据库中是否已经存在
     *
     * @return 存在则返回true
     */
    public boolean isExist(ContentValues values) {
        int input_id = (int) values.get("id");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TableName, new String[]{"id"}, "id=?",
                new String[]{String.valueOf(input_id)}, null, null, null);
        if (cursor.getCount() != 0) {
            Toast.makeText(getApplicationContext(), "该ID已存在！", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}