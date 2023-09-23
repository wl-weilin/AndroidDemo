package com.demoapp.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import static com.demoapp.contentproviderdemo.MyDatabaseHelper.DatabaseName;
import static com.demoapp.contentproviderdemo.MyDatabaseHelper.TableName;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import java.io.File;

public class DataBaseProvider extends ContentProvider {
    public DataBaseProvider() {
        Log.d(TAG, "DataBaseProvider Excuted");
    }

    public static final int Person_Dir = 1;
    public static final int Person_Item = 2;

    // 注意与android:authorities一致
    public static final String AUTHORITY = "com.demoapp.contentproviderdemo.provider";
    private static final UriMatcher uriMatcher;
    private final String TAG = "DataBaseProvider";

    private MyDatabaseHelper dbHelper;

    //UriMatcher类就可以实现匹配内容URI的功能
    static {
        //#：表示匹配任意长度的数字,数字表示id
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //addURI()把authority, path(组成Uri)和一个自定义代码传进去。
        //调用match()方法时，就可以通过ContentResolver传入的Uri匹配此处的Uri，返回所对应的自定义代码
        uriMatcher.addURI(AUTHORITY, TableName, Person_Dir);
        uriMatcher.addURI(AUTHORITY, TableName + "/#", Person_Item);
    }

    @Override
    public boolean onCreate() {
//        timeoutTask(8000);
        Log.d(TAG, "onCreate Excuted");
        dbHelper = new MyDatabaseHelper(getContext(), DatabaseName, null, 2);
        return true;
    }

    //ContentResolver会使用Uri定位到表，getType获取到ContentResolver传过来的Uri后，
    //通过uriMatcher类匹配addURI()的Uri并返回自定义代码，然后返回所对应的MIME类型
    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType Excuted");
        switch (uriMatcher.match(uri)) {
            case Person_Dir:  //若内容URI以路径结尾(多条数据)，则Type为android.cursor.dir
                return "vnd.android.cursor.dir/vnd.com.demoapp.contentproviderdemo.provider." + TableName;
            case Person_Item: //若内容URI以id结尾(单条数据)，则Type为android.cursor.item
                return "vnd.android.cursor.item/vnd.com.demoapp.contentproviderdemo.provider." + TableName;
        }
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query Excuted");
//        printInfo();
//        timeoutTask(8000);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case Person_Dir:
                cursor = db.query(TableName, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case Person_Item:
                String Id = uri.getPathSegments().get(1);
                cursor = db.query(TableName, projection, "id = ?", new String[]{Id}, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert Excuted");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case Person_Dir:
            case Person_Item:
                long newId = db.insert(TableName, null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/" + TableName + "/" + newId);
                int NOTIFY_NO_DELAY = 1 << 15;
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete Excuted");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)) {
            case Person_Dir:
                deletedRows = db.delete(TableName, selection, selectionArgs);
                break;
            case Person_Item:
                //以路径分隔符"/"分割uri字符串，返回id
                String Id = uri.getPathSegments().get(1);
                deletedRows = db.delete(TableName, "id = ?", new String[]{Id});
                getContext().getContentResolver().notifyChange(uri, null,
                        ContentResolver.NOTIFY_DELETE);
                break;
            default:
                break;
        }
        return deletedRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.d(TAG, "update Excuted");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)) {
            case Person_Dir:
                updatedRows = db.update(TableName, values, selection, selectionArgs);
                break;
            case Person_Item:
                String Id = uri.getPathSegments().get(1);
                updatedRows = db.update(TableName, values, "id = ?", new String[]{Id});
                break;
            default:
                break;
        }
        return updatedRows;
    }

    @Override
    public Bundle call(@NonNull String method, String arg, Bundle extras) {
        Log.d(TAG, "call Excuted " + method);
        Bundle bundle = new Bundle();
        if (method.equals("method1")) {
            Context context = getContext();
            Log.d(TAG, "Provider Context = " + context.toString());
            bundle.putString("key", "调用method1成功！");
            return bundle;
        } else if (method.equals("method2")) {
            throw new RuntimeException("异常测试");
        }
        return null;
    }

    //模拟耗时任务，参数为耗时时间ms
    public void timeoutTask(long duration) {
        final long cur = System.currentTimeMillis();
        while (System.currentTimeMillis() <= cur + duration) {
            //
        }
    }
}