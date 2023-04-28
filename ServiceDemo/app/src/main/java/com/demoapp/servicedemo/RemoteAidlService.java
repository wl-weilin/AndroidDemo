package com.demoapp.servicedemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 如果是通过Aidl远程调用Service，则返回该对象。远程调用的intent需要指明包名和服务类名
 */
public class RemoteAidlService extends IRemoteAidl.Stub {
    public String TAG = "RemoteAidlService";
    private final Context mContext;
    public DownloadTask task = null;

    public RemoteAidlService(Context context) {
        mContext = context;
    }

    @Override
    public String func() {
        Log.d(TAG, "调用 func()");
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