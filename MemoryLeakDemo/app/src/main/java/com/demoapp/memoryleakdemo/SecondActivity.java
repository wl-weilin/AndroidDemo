package com.demoapp.memoryleakdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import static java.lang.Thread.sleep;

public class SecondActivity extends Activity {
    String TAG = "SecondActivity";
    MyReceiver myReceiver;
    UserClass userClass;

    class MyHandler extends Handler {
        WeakReference<Context> activityReference;

        public MyHandler(Context context) {
            activityReference = new WeakReference<Context>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            Context context = activityReference.get();
            if (context != null) {
                //TODO
            }
        }
    }

    private class Inner extends Thread {
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

        userClass = UserClass.getInstance(this);

        findViewById(R.id.botton).setOnClickListener(v -> {
            myReceiver = new MyReceiver();
            IntentFilter intentFilter = new IntentFilter("com.example.broadcast.ACTION1");
            registerReceiver(myReceiver, intentFilter);
//            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();

//            MyHandler handler = new MyHandler(this);
//            handler.sendMessageDelayed(Message.obtain(), 10000);

//            MyThread thread=new MyThread();
//            thread.start();

//            Inner inner = new Inner();
//            inner.start();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(myReceiver);
    }
}
