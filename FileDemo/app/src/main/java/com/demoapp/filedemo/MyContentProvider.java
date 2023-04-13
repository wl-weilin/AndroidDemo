package com.demoapp.filedemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import java.io.File;

public class MyContentProvider extends ContentProvider {
    private final String TAG = "MyContentProvider";

    public MyContentProvider() {
    }

    @Override
    public Bundle call(@NonNull String method, String arg, Bundle extras) {
        Log.d(TAG, "call Excuted");
        Bundle bundle = new Bundle();
        if (method.equals("getFileUri")) {
            Context context = getContext();
            Log.d(TAG, context.getPackageName());
            Log.d(TAG, context.toString());
            File file = new File(context.getFilesDir().getPath() + "/share_dir/myfile.txt");
            Uri uri = FileProvider.getUriForFile(context, "com.demoapp.filedemo.fileprovider", file);
            context.grantUriPermission("com.demoapp.contentresolverdemo", uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Log.d(TAG, uri.toString());
            bundle.putString("result_uri", uri.toString());
            return bundle;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate Excuted");
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}