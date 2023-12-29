package com.demoapp.notificationdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.view.View;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class NotificationUtil {
    Context mContext;
    String channelId;       //设置channelId，要有唯一性
    String channelName;     //在Android通知管理中可见的名称
    int notificationId;     //id标识通知
    NotificationChannel channel;    //Android 8.0开始，显示通知时,需要构造NotificationChannel，一个channelId对应一个NotificationChannel
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
                "com.demoapp.notificationdemo.SecondActivity");

        int flag = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;
        return PendingIntent.getActivity(mContext, 0, intent, flag);
    }

    public PendingIntent getBroadcast() {
        //点击通知后要执行的操作：首先构建Intent，然后根据Intent跳转到Activity
        Intent intent = new Intent("button_click");
        intent.setClassName(mContext.getPackageName(),
                mContext.getPackageName() + ".ButtonReceiver");
        int flag = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;
        return PendingIntent.getBroadcast(mContext, 0, intent, flag);
    }

    public void normalNotification() {
        PendingIntent pendingIntent = getActivity();

        //获取一个Notification构造器
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, channelId);
        //设置Notification的样式
        builder.setContentTitle("标题")
                .setContentText("内容")
                .setSubText("子标题")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.notification_icon)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.notification_icon))
                .setContentIntent(pendingIntent)  //传入Intent
                .setAutoCancel(true)  //点击通知后自动消失
                .setOngoing(true)
        ;

        //获取构建好的Notification
        Notification notification = builder.build();
        //id标识该notification
        notificationManager.notify(notificationId, notification);
    }

    public void buttonNotification() {
        PendingIntent pendingIntent = getActivity();
        //获取一个Notification构造器
        Notification.Builder builder = new Notification.Builder(mContext, channelId);
        //设置Notification的样式
        builder.setContentTitle("标题")
                .setContentText("内容")
                .setSubText("子标题")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.notification_icon)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)  //传入Intent
        ;

        // 添加第一个按钮
        Notification.Action action = new Notification.Action.Builder(
                Icon.createWithResource(mContext, R.mipmap.ic_launcher),
                "Button 1", pendingIntent).build();
        builder.addAction(action);
        // 添加第二个按钮
        builder.addAction(R.mipmap.ic_launcher, "Button 2", pendingIntent);

        //获取构建好的Notification
        Notification notification = builder.build();
        //id标识该notification
        notificationManager.notify(notificationId, notification);
    }

    public void styleNotification() {
        PendingIntent pendingIntent = getActivity();
        //获取一个Notification构造器
        Notification.Builder builder = new Notification.Builder(mContext, channelId);
        //设置Notification的样式
        builder.setContentTitle("标题")
                .setContentText("内容")
                .setSubText("子标题")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.notification_icon)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)  //传入Intent
                .setStyle(new Notification.BigTextStyle().bigText(
                        "第1行\n第2行\n第3行\n第4行\n第5行\n第6行\n").setSummaryText("通知摘要"))
        ;

        // 添加第一个按钮
        Notification.Action action = new Notification.Action.Builder(
                Icon.createWithResource(mContext, R.drawable.ic_settings),
                "Button 1", pendingIntent).build();
        builder.addAction(action);
        // 添加第二个按钮
        builder.addAction(R.mipmap.ic_launcher, "Button 2", pendingIntent);

        //获取构建好的Notification
        Notification notification = builder.build();
        //id标识该notification
        notificationManager.notify(notificationId, notification);
    }

    /**
     * 模拟实现一个音乐播放器的通知栏控制
     * 如果需要完全实现，需要通过builder.setStyle()设置MediaStyle，比较麻烦
     */
    public void showRemoteViewsNotification() {
        PendingIntent pendingIntent = getActivity();

        //获取一个Notification构造器
        Notification.Builder builder = new Notification.Builder(mContext, channelId);
        //设置Notification的样式
        builder
                .setContentTitle("音乐")
                .setSmallIcon(R.drawable.icon_notification)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.notification_icon))
                .setContentIntent(pendingIntent)  //传入Intent
                .setOngoing(true)
        ;

        // 设置普通视图，在通知栏高度限制为 64 dp
        RemoteViews smallRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.music_control_small);
        smallRemoteViews.setImageViewBitmap(R.id.music_pic, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.qqmusic));
        builder.setCustomContentView(smallRemoteViews);

        // 设置扩展视图，在通知栏高度可以扩展到256dp
        RemoteViews bigRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.music_control_big);
        bigRemoteViews.setViewVisibility(R.id.favorite, View.GONE);
        bigRemoteViews.setViewVisibility(R.id.lyric_closed, View.GONE);
        bigRemoteViews.setImageViewBitmap(R.id.music_pic, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.qqmusic));
        builder.setCustomBigContentView(bigRemoteViews);

        //获取构建好的Notification
        Notification notification = builder.build();

        //id标识该notification
        notificationManager.notify(notificationId, notification);
    }
}
