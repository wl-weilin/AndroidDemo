package com.demoapp.broadcastreceiver2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    String TAG = "BroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Receiver2收到广播", Toast.LENGTH_SHORT).show();

        Log.d(TAG," 广播执行完成");
//        abortBroadcast();
    }

    //模拟耗时任务，参数为耗时时间
    public void timeoutTask(long duration){
        final long cur = System.currentTimeMillis();
        while (System.currentTimeMillis() <= cur + duration) {
        }
    }
}
