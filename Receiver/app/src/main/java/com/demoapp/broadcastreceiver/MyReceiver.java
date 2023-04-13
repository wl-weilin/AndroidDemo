package com.demoapp.broadcastreceiver;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    String TAG = "BroadcastReceiver";
    String myAction1 = "com.example.broadcast.ACTION1";
    String myAction2 = "com.example.broadcast.ACTION2";

    static final IntentFilter intentFilter = new IntentFilter();

    static {
        intentFilter.addAction("com.example.broadcast.ACTION1");
        intentFilter.addAction("com.example.broadcast.ACTION2");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, context.toString());
//        Log.d(TAG, "收到广播");
        if (myAction1.equals(action)) {
            Toast.makeText(context, "收到广播1", Toast.LENGTH_SHORT).show();
//            timeoutTask(6000);
//            Intent activityIntent = new Intent().setClassName("com.android.mms","com.android.mms.ui.NewMessageActivity");
//            context.startActivity(activityIntent);
            Log.d(TAG, context + " 广播执行完成");
        } else if (myAction2.equals(action)) {
            Toast.makeText(context, "收到广播2", Toast.LENGTH_SHORT).show();

//            Intent activityIntent = new Intent().setClassName("com.google.android.apps.messaging","com.google.android.apps.messaging.home.HomeActivity");
//            context.startActivity(activityIntent);

            Log.d(TAG, context + " 广播执行完成");
        }
//        Log.d(TAG, context.getClass().toString());
//        Log.d(TAG, "Receiver: " + this.toString());
    }

    public void register(Context context) {
        context.registerReceiver(this, intentFilter);
        Log.d(TAG, context + " 注册广播");
    }

    //模拟耗时任务，参数为耗时时间
    public void timeoutTask(long duration){
        final long cur = System.currentTimeMillis();
        while (System.currentTimeMillis() <= cur + duration) {
            //
        }
    }
}
