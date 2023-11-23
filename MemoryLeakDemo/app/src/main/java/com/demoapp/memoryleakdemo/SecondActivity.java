package com.demoapp.memoryleakdemo;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    String TAG = "SecondActivity";
    MyReceiver myReceiver;
    SingletonObject singleton;

    class InnerHandler extends Handler {
//        WeakReference<Context> activityReference;
//
//        public MyHandler(Context context) {
//            activityReference = new WeakReference<Context>(context);
//        }

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "Get Message");
//            Context context = activityReference.get();
//            if (context != null) {
//                //TODO
//            }
        }
    }

    private class InnerThread extends Thread {
        @Override
        public void run() {
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        findViewById(R.id.botton).setOnClickListener(v -> {
//            Inner inner = new Inner();
//            inner.start();
        });

        findViewById(R.id.static_leak).setOnClickListener(v -> {
            singleton = SingletonObject.getInstance(this);
        });

        // Handler造成内存泄露
        findViewById(R.id.handler_leak).setOnClickListener(v -> {
            // 内部类MyHandler且延时执行
            InnerHandler handler = new InnerHandler();
            handler.sendMessageDelayed(Message.obtain(), 10000);

        });

        findViewById(R.id.thread_leak).setOnClickListener(v -> {
            // 非静态内部类Thread导致的内存泄漏
            InnerThread innerThread=new InnerThread();
            innerThread.start();


            // 使用外部继承的Thread类避免内存泄漏
            MyThread thread=new MyThread();
            thread.start();
        });

        findViewById(R.id.resource_leak).setOnClickListener(v -> {
            myReceiver = new MyReceiver();
            IntentFilter intentFilter = new IntentFilter("com.example.broadcast.ACTION1");
            registerReceiver(myReceiver, intentFilter);
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(myReceiver);
    }
}
