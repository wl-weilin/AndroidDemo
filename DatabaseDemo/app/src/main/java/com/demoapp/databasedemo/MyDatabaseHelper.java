package com.demoapp.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

//借助SQLiteOpenHelpe类可以非常方便地管理数据库
public class MyDatabaseHelper extends SQLiteOpenHelper {
    // 数据库名与表名，注意全局一致
    public static final String DatabaseName = "People.db";
    public static final String TableName = "Person";

    private final Context mContext;
    private final String TAG = "MyDatabaseHelper";

    //创建“Person”表，每条数据包括身份号id (主键)、 姓名等属性
    //integer表示整型，text表示文本类型，real表示浮点型
    //将id列设为主键，并用autoincrement关键字表示id的值是自增长的，即使删除某行，id也会继续增加
    public static final String Create_Table = "create table " + TableName + " ("
            + "id integer primary key, "
            + "name text)";


    //构建出SQLiteOpenHelper的实例，但还未实际创建数据库
    public MyDatabaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        Log.d(TAG, "MyDatabaseHelper()");
    }

    //创建数据库时会执行onCreate()
    //数据库文件会存放在/data/data/<packagename>/databases/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table);
        initData(db);
        Log.d(TAG, "MyDatabaseHelper onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Person");
        onCreate(db);
    }

    public void initData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        // 插入第一条数据
        values.put("id", 1001);
        values.put("name", "赵一");
        db.insert(TableName, null, values);

        // 插入第二条数据
        values.clear(); //清除数据
        values.put("id", 1002);
        values.put("name", "钱二");
        db.insert(TableName, null, values);
    }
}
