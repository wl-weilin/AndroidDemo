package com.demoapp.lockdemo;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {
    String TAG = "LockDemo";
    public final Object objectLock = new Object();

    private class MyRunnable implements Runnable{

        @Override
        public void run() {
            synchronized (objectLock) {
                Log.d(TAG, "消息获得锁");
                timeoutTask(2000);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.Botton).setOnClickListener(v -> {
            Handler handlerPost = new Handler(Looper.getMainLooper());
            handlerPost.post(new MyRunnable());
//            handlerPost.post(new MyRunnable());

//            timeoutTask(1000);
            new Thread(new MyRunnable()).start();
        });

    }


    //模拟耗时任务，参数为耗时时间ms
    public void timeoutTask(long duration) {
        final long cur = System.currentTimeMillis();
        while (System.currentTimeMillis() <= cur + duration) {
            //
        }
    }

    public void func(){
        synchronized (objectLock) {
            Log.d(TAG, "消息获得锁");
            timeoutTask(2000);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
