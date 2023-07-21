package com.demoapp.servicedemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    private final String TAG = "MyService";
    private LocalBinder mLocalBinder;  //本APP访问返回该对象
    private RemoteAidlService mRemoteAidlService; //远程APP访问返回该对象
    private boolean isRemote;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate executed");
        mLocalBinder = new LocalBinder(this);
        mRemoteAidlService = new RemoteAidlService(this);

        Intent intent = new Intent(this, SecondActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand executed");
//        stopForeground(false);
//          // 开启前台服务，id标识该notification
        int notificationId = 0x2717;
        startForeground(notificationId, notificationConfig());
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind executed");
        Log.d(TAG, intent.toString());
        //如果是跨APP绑定服务，则返回Aidl实现
        if (intent.getBooleanExtra("isRemote", false)) {
            isRemote = true;
            return mRemoteAidlService;
        } else {
            isRemote = false;
            return mLocalBinder;
        }
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind executed");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind executed");
        if (isRemote) {
            mRemoteAidlService.cancelDownload();
        } else {
            mLocalBinder.cancelDownload();
        }
        return true;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy executed");
//        stopForeground(false);
    }

    public Notification notificationConfig() {
        String channelId = "device_notification_channel";  //设置channelId，要有唯一性
        String channelName = "channelName";  //在Android通知管理中可见的名称

        //点击通知后要执行的操作：首先构建Intent，然后根据Intent跳转到Activity
        Intent myIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, myIntent, PendingIntent.FLAG_IMMUTABLE);

        //Android8.0开始，显示通知时,需要构造NotificationChannel，一个channelId对应一个NotificationChannel
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.setShowBadge(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        //获取系统服务NotificationManager,然后创建NotificationChannel
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        //获取一个Notification构造器
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        //设置Notification的样式
        builder.setContentTitle("标题")
                .setContentText("内容")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
                .setOngoing(true)
        ;
        //获取构建好的Notification
        Notification notification = builder.build();
//        manager.notify(1, notification);
        return notification;
    }
}