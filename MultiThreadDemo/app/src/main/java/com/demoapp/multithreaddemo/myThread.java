package com.demoapp.multithreaddemo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

class myThread extends Thread {
    Context mContext;
    public myThread(Context context){
        mContext=context;
    }
    public Handler mHandler;
    public static final int MsgNum = 1;

    public void run() {
        Looper.prepare();
        // 创建Handler
        mHandler = new Handler(Looper.myLooper()) {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MsgNum:
                        // 在这里可以进行子线程耗时操作，不能更新UI，但可以使用Toast
                        Toast.makeText(mContext, "我是HandlerThread", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
        Looper.loop();
    }
}
