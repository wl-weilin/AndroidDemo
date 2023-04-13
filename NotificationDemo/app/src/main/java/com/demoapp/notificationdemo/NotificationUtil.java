package com.demoapp.notificationdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

public class NotificationUtil {
    Context mContext;
    String channelId;       //设置channelId，要有唯一性
    String channelName;     //在Android通知管理中可见的名称
    int notificationId;     //id标识通知
    NotificationChannel channel;    //Android8.0开始，显示通知时,需要构造NotificationChannel，一个channelId对应一个NotificationChannel
    NotificationManager notificationManager;     //获取系统服务NotificationManager,然后创建NotificationChannel

    public NotificationUtil(Context context) {
        mContext = context;
        notificationnInit();
    }

    public void notificationnInit() {
        channelId = "channelId";
        channelName = "channelName";
        notificationId = 0x1111;

        channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.setShowBadge(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    //点击通知后要执行的操作：首先构建Intent，然后根据Intent跳转到Activity
    public PendingIntent getActivity() {
        Intent intent = new Intent();
//        intent.setClass(mContext, SecondActivity.class);
//        intent.setClassName("com.demoapp.activitydemo",
//                "com.demoapp.activitydemo.MainActivity");
        intent.setClassName("com.demoapp.notificationdemo",
                "com.demoapp.notificationdemo.SecondActivity1");

//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("schema-demo://example/"));
        int flag = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;
        return PendingIntent.getActivity(mContext, 0, intent, flag);
    }

    public PendingIntent getBroadcast() {
        //点击通知后要执行的操作：首先构建Intent，然后根据Intent跳转到Activity
        Intent intent = new Intent(mContext, SecondActivity.class);
        int flag = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;
        return PendingIntent.getActivity(mContext, 0, intent, flag);
    }

    public void normalNotification() {
        PendingIntent pendingIntent = getActivity();

        //获取一个Notification构造器
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, channelId);
        //设置Notification的样式
        builder.setContentTitle("标题")
                .setContentText("内容")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)  //传入Intent
//                .setAutoCancel(true)  //点击通知后自动消失
//                .setOngoing(true)
        ;

        //获取构建好的Notification
        Notification notification = builder.build();
        //id标识该notification
        notificationManager.notify(notificationId, notification);
    }
}
