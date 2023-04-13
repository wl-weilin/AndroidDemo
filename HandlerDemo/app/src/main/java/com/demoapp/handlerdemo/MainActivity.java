package com.demoapp.handlerdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public String TAG = "HandlerDemo";
    public static final int MsgNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取主线程Looper,也可以用Looper.myLooper()
        Looper mainLooper = Looper.getMainLooper();
//        Handler mainHanlder = new Handler(mainLooper) {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case MsgNum:
//                        Log.d(TAG, "执行消息成功！");
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
        Handler mainHanlder = new Handler(mainLooper);

        findViewById(R.id.Botton0).setOnClickListener(v -> {
            Message message = Message.obtain();
            message.what = MsgNum;
            mainHanlder.sendMessage(message);
//            mainHanlder.post(() -> Log.d(TAG, "执行Post消息成功！"));
        });

        findViewById(R.id.Botton1).setOnClickListener(v -> {
            Handler threadHandler = createHandlerThread();
            //构造Message并设置targetHandler与what值
            Message message = Message.obtain(threadHandler, MsgNum);
            threadHandler.sendMessage(message);
        });

        findViewById(R.id.Botton2).setOnClickListener(v -> {
            LooperThread looperThread = new LooperThread();
            looperThread.start();
            //等待looperThread线程给Handler赋值，否则mHandler=null
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = Message.obtain();
            message.what = MsgNum;
            looperThread.mHandler.sendMessage(message);
        });


    }


    public Handler createHandlerThread() {
        //Step 1:创建一个线程,线程名字：handler-thread
        HandlerThread myHandlerThread = new HandlerThread("handler-thread");
        //Step 2:必须先开启线程才能定义threadHandler
        myHandlerThread.start();

        //Step 3:在这个线程中创建一个handler对象
        return new Handler(myHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MsgNum:
                        // 在这里可以进行子线程耗时操作，不能更新UI，但可以使用Toast
                        Toast.makeText(getApplicationContext(), "我是HandlerThread", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
    }
}

class LooperThread extends Thread {
    public Handler mHandler;
    public static final int MsgNum = 1;

    @Override
    public void run() {
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MsgNum:
                        Log.d("LooperThread", "执行消息成功！");
                        break;
                    default:
                        break;
                }
            }
        };
        Looper.loop();
    }
}