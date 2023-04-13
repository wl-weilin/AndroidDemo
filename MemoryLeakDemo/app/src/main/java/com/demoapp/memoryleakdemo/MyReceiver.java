package com.demoapp.memoryleakdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

class MyReceiver extends BroadcastReceiver {
    String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive excuted");
        Log.d(TAG, context.getClass().getSimpleName());
    }
}
