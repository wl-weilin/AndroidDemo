package com.demoapp.broadcastreceiver;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends Activity {
    String TAG="BroadcastReceiver";
    MyReceiver myReceiver2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.mybotton2).setOnClickListener(v -> {
            Log.d(TAG,myReceiver2.toString());
            myReceiver2.myLog();
        });

        myReceiver2=new MyReceiver(){};
        myReceiver2.register(this);
    }

}
