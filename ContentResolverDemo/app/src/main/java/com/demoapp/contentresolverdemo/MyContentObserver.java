package com.demoapp.contentresolverdemo;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public class MyContentObserver extends ContentObserver {
    private final String TAG = "ResolverDemoMain";

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public MyContentObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri, int flags) {
//        Log.d(TAG, String.valueOf(selfChange));
        assert uri != null;
        String str = String.format("change(%s,%s,%s) Executed",
                selfChange, uri, "0x" + Integer.toHexString(flags));
        Log.d(TAG, str);
//        Log.d(TAG, Integer.toHexString(flags));
        if ((flags & ContentResolver.NOTIFY_INSERT) != 0) {
            // 处理插入操作的变化
            Log.d(TAG, "NOTIFY_INSERT");
        }
        if ((flags & ContentResolver.NOTIFY_UPDATE) != 0) {
            // 处理更新操作的变化
            Log.d(TAG, "NOTIFY_UPDATE");
        }
        if ((flags & ContentResolver.NOTIFY_DELETE) != 0) {
            // 处理删除操作的变化
            Log.d(TAG, "NOTIFY_DELETE");
        }
    }

    @Override
    public void onChange(boolean selfChange) {
        Log.d(TAG, String.valueOf(selfChange));
        // 数据变化时的操作
        // 可以在这里更新UI、执行其他业务逻辑等
        Log.d(TAG, "change() Executed");
    }
}
