package com.demoapp.servicedemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 如果是本地调用Service，则返回该对象
 */
public class LocalBinder extends Binder {
    public String TAG = "LocalBinder";
    private final Context mContext;
    public DownloadTask task = null;

    public LocalBinder(Context context) {
        mContext = context;
    }

    public String func() {
        Log.d(TAG, "调用 func()");
        ((Service) mContext).stopForeground(false);
        return "I am Server";
    }

    public void startDownload() {
        if (task != null) {
            return;
        }
        task = new DownloadTask(mContext);
        task.startDownload();
    }

    public void pauseDownload() {
        task.pauseDownload();
    }

    public void continueDownload() {
        task.continueDownload();
    }

    public void cancelDownload() {
        task.cancelDownload();
    }
}
