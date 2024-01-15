package com.demoapp.binderserverdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Objects;

public class MessengerService extends Service {
    private final String TAG = "MyMessenger";
    private Handler mMessengerHandler;

    @Override
    public void onCreate() {
        mMessengerHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG, "From Client msg = " + msg);
                switch (msg.what) {
                    case 1:
                        Log.i(TAG, Objects.requireNonNull(msg.getData().getString("bundleKey")));
                        // 设置回复给客户端的消息
                        Message toClientMsg = Message.obtain();
                        toClientMsg.what = 2;
                        Bundle bundle = new Bundle();
                        bundle.putString("bundleKey", "I am replay from Server");
                        toClientMsg.setData(bundle);
                        // 获取来自客户端的Messenger
                        Messenger clientMessenger = msg.replyTo;
                        try {
                            clientMessenger.send(toClientMsg);
                        } catch (Exception e) {
                            Log.e(TAG, "Send to clientMessenger error ", e);
                        }
                        break;
                    case 2:
                        Log.i(TAG, Objects.requireNonNull(msg.getData().getString("bundleKey")));
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {
        Messenger messenger = new Messenger(mMessengerHandler);
        return messenger.getBinder();
    }
}
