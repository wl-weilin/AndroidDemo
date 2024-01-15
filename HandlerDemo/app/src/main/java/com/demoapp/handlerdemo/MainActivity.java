package com.demoapp.handlerdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.util.Log;

import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    public String TAG = "HandlerDemo";
    public static final int MsgNum = 1;
    private Handler mHanlder;
    private Handler mMainHanlder;
    private Looper mMainLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取主线程Looper,也可以用Looper.myLooper()
        mMainLooper = Looper.getMainLooper();
        mMainHanlder = new Handler(mMainLooper);

        findViewById(R.id.send_message).setOnClickListener(v -> {
            Message message = Message.obtain();
            message.what = MsgNum;
            new Handler(mMainLooper).sendMessage(message);
        });

        findViewById(R.id.post_message).setOnClickListener(v -> {
            new Handler(mMainLooper).post(() -> Log.d(TAG, "执行Post消息成功！"));
        });

        findViewById(R.id.my_handler).setOnClickListener(v -> {
            initMyHandler();
            mHanlder.post(() -> Log.d(TAG, "自定义Handler执行Post消息成功！"));
        });

        findViewById(R.id.handle_thread).setOnClickListener(v -> {
            Handler threadHandler = createHandlerThread();
            //构造Message并设置targetHandler与what值
            Message message = Message.obtain(threadHandler, MsgNum);
            threadHandler.sendMessage(message);
        });

        findViewById(R.id.looper_thread).setOnClickListener(v -> {
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

        findViewById(R.id.async_msg).setOnClickListener(v -> {
            sendAsyncMsg();
        });
    }

    public void sendAsyncMsg() {
        // 构造同步和异步消息
        Message msg1 = Message.obtain(mMainHanlder, () -> Log.d(TAG, "1s后执行的同步消息！"));
        Message msg2 = Message.obtain(mMainHanlder, () -> Log.d(TAG, "2s后执行的同步消息！"));
        Message msg3 = Message.obtain(mMainHanlder, () -> Log.d(TAG, "3s后执行的异步消息！"));
        Message msg4 = Message.obtain(mMainHanlder, () -> Log.d(TAG, "4s后执行的异步消息！"));
        msg3.setAsynchronous(true);
        msg4.setAsynchronous(true);

        // 发送消息并设置延时执行
        mMainHanlder.sendMessageDelayed(msg1, 1000); // 发送1秒后执行的同步消息
        mMainHanlder.sendMessageDelayed(msg2, 2000); // 发送2秒后执行的同步消息
        mMainHanlder.sendMessageDelayed(msg3, 3000); // 发送3秒后执行的异步消息
        mMainHanlder.sendMessageDelayed(msg4, 4000); // 发送4秒后执行的异步消息

        // 设置同步屏障生效
        int token = 0;
        try {
            Method method = MessageQueue.class.getDeclaredMethod("postSyncBarrier");
            token = (int) method.invoke(Looper.getMainLooper().getQueue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Token=" + token);

        // 解除同步屏障
        // 注意：解除同步屏障也必须是要在异步消息的执行代码中中解除，因为同步消息在屏障期间不会被执行
        int finalToken = token; // 从内部类引用的外部变量必须是final的
        Message msg5 = Message.obtain(mMainHanlder, new Runnable() {
            @Override
            public void run() {
                try {
                    Method method = MessageQueue.class.getDeclaredMethod("removeSyncBarrier", int.class);
                    method.invoke(Looper.getMainLooper().getQueue(), finalToken);
                    Log.d(TAG, "移除同步屏障");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        msg5.setAsynchronous(true);
        mMainHanlder.sendMessageDelayed(msg5, 4500);
    }


    // 自定义Handler
    public void initMyHandler() {
        mHanlder = new Handler(mMainLooper) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MsgNum:
                        Log.d(TAG, "执行消息成功！");
                        break;
                    default:
                        break;
                }
            }
        };
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