package com.demoapp.servicedemo;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

public class DownloadTask {
    private final Context mContext;
    public NotificationCompat.Builder builder;
    public NotificationManager notificationManager;
    public final Object object = new Object(); //对象锁
    public volatile String status = "noTask";  //标记下载状态
    public static final int notifyId = 0x3;

    public DownloadTask(Context context){
        mContext=context;
    }

    public void startDownload() {
        if (!status.equals("noTask")) { //如果已有下载任务
            return;
        }
        String channelId = "channelId";  //设置channelId，要有唯一性
        String channelName = "channelName";  //在Android通知管理中可见的名称

        //Android 8.0开始，显示通知时,需要构造NotificationChannel，一个channelId对应一个NotificationChannel
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.setShowBadge(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        //获取系统服务NotificationManager,然后创建NotificationChannel
        notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);

        //获取一个Notification构造器
        builder = new NotificationCompat.Builder(mContext, channelId);
        //设置Notification的样式
        builder.setContentTitle("正在下载")
                .setContentText("未开始")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                .setOngoing(true);

        //获取构建好的Notification
        Notification notification = builder.build();
        //id标识该notification
        notificationManager.notify(notifyId, notification);

        //创建线程模拟下载并进度提示
        Thread downloadThread = new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                builder.setProgress(100, i, false)
                        .setContentText("下载" + i + "%"); //下载进度提示
                notificationManager.notify(notifyId, builder.build());
                try {
                    Thread.sleep(100); //演示休眠
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //通过等锁方式实现暂停
                if (status.equals("pause")) {
                    synchronized (object) {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //取消下载
                if (status.equals("noTask")) {
                    break;
                }
                if (i == 100) {
                    status = "noTask";
                }
            }
        });
        downloadThread.start();
        status = "downloading";
    }

    public void pauseDownload() {
        if (status.equals("pause")) { //如果已处于暂停状态，不执行
            return;
        }
        status = "pause";
    }

    public void continueDownload() {
        if (status.equals("pause")) { //如果已处于暂停状态，唤醒下载线程
            synchronized (object) {
                object.notify();
            }
            status = "downloading";
        }
    }

    public void cancelDownload() {
        pauseDownload();
        notificationManager.cancel(notifyId);
        status = "noTask";
    }
}
