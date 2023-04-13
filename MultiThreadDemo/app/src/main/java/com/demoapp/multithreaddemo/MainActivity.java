package com.demoapp.multithreaddemo;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public static final int MsgNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mybotton = (Button) findViewById(R.id.mybotton);

        //创建并定义threadHandler
        Handler threadHandler= createSubThread(this);
        mybotton.setOnClickListener(v -> {
            //构造Message并设置targetHandler与what值
            Message message =  Message.obtain(threadHandler,MsgNum);
            message.sendToTarget();
        });
    }

    public Handler createSubThread(Context context){
        //Step 1:创建一个线程,线程名字：handler-thread
        HandlerThread myHandlerThread = new HandlerThread( "handler-thread") ;

        //Step 2:必须先开启线程才能定义threadHandler
        myHandlerThread.start();

        //Step 3:在这个线程中创建一个handler对象
        return new Handler( myHandlerThread.getLooper() ){
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