package com.demoapp.broadcastreceiver;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

// 使用命令发送广播：adb shell am broadcast -a com.example.broadcast.ACTION1 -n com.demoapp.broadcastreceiver/.MyReceiver
public class MyReceiver extends BroadcastReceiver {
    public static final String TAG = "BroadcastReceiverMain";
    String myAction1 = "com.example.broadcast.ACTION1";
    String myAction2 = "com.example.broadcast.ACTION2";

    static final IntentFilter intentFilter = new IntentFilter();

    static {
        intentFilter.addAction("com.example.broadcast.ACTION1");
        intentFilter.addAction("com.example.broadcast.ACTION2");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "收到广播:" + intent);
        Log.d(TAG, "context = " + context);
        String action = intent.getAction();

        if (myAction1.equals(action)) {
            Toast.makeText(context, "收到广播1", Toast.LENGTH_SHORT).show();
            Intent activityIntent = new Intent().setClassName("com.demoapp.dialogdemo",
                    "com.demoapp.dialogdemo.MainActivity");
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(activityIntent);
            Log.d(TAG, action + " 执行完成");
        } else if (myAction2.equals(action)) {
            Toast.makeText(context, "收到广播2", Toast.LENGTH_SHORT).show();

            Log.d(TAG, action + " 执行完成");
        }
    }

    private void openActivity(Context context) {
        Intent activityIntent = new Intent().setClassName("com.demoapp.activitydemo",
                "com.demoapp.activitydemo.MainActivity");
        context.startActivity(activityIntent);
    }


    //模拟耗时任务，参数为耗时时间
    public void timeoutTask(long duration) {
        final long cur = System.currentTimeMillis();
        while (System.currentTimeMillis() <= cur + duration) {
            //
        }
    }
}
