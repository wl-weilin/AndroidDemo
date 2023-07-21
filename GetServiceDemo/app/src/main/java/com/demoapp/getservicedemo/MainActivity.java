package com.demoapp.getservicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;

import com.demoapp.servicedemo.IRemoteAidl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String TAG = "GetServiceDemo";
    private IRemoteAidl iRemoteAidl;
    private Intent remoteIntent;
    private boolean mIsBound = false;
    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "远程服务绑定成功！");
            mIsBound = true;
            //获取远程Service的onBind方法返回的对象的代理
            iRemoteAidl = IRemoteAidl.Stub.asInterface(service);
        }

        //执行unbindService()不会回调该方法，只有在Service端所在进程崩溃或被杀的时候调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "远程服务已销毁！");
            mIsBound = false;
            iRemoteAidl = null;
        }

        @Override
        public void onBindingDied(ComponentName name) {
            Log.d(TAG, "onBindingDied");
        }

        @Override
        public void onNullBinding(ComponentName name) {
            Log.d(TAG, "onNullBinding");
        }
    };

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        remoteIntent = new Intent();
        // Android 5.0以后只设置action不能启动相应的服务，需要设置packageName或者Component
        remoteIntent.setPackage("com.demoapp.servicedemo");
        remoteIntent.setAction("com.demoapp.servicedemo.ServiceAction");
        remoteIntent.putExtra("isRemote", true);

        findViewById(R.id.start_service).setOnClickListener(v -> {
            Log.d(TAG, "startForegroundService Executed");
            startForegroundService(remoteIntent);
//            startService(remoteIntent);
        });

        findViewById(R.id.stop_service).setOnClickListener(v -> {
            stopService(remoteIntent);
        });

        findViewById(R.id.bind_service).setOnClickListener(v -> {
            Log.d(TAG, "bindService Executed");
            bindService(remoteIntent, mConnection, BIND_AUTO_CREATE);
//            bindInChildThread();
        });

        findViewById(R.id.unbind_service).setOnClickListener(v -> {
            if (!mIsBound) return;
            unbindService(mConnection);
            mIsBound = false;
        });

        findViewById(R.id.start_download).setOnClickListener(v -> {
            if (iRemoteAidl == null) return;
            try {
                iRemoteAidl.startDownload();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.pause_download).setOnClickListener(v -> {
            if (iRemoteAidl == null) return;
            try {
                iRemoteAidl.pauseDownload();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.continue_download).setOnClickListener(v -> {
            if (iRemoteAidl == null) return;
            try {
                iRemoteAidl.continueDownload();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.invoke_service).setOnClickListener(v -> {
            invokeRemoteService();
//            MyReceiver myReceiver = new MyReceiver();
//            IntentFilter intentFilter = new IntentFilter("com.example.broadcast.ACTION1");
//            registerReceiver(myReceiver, intentFilter);
//            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        });
    }

    public void invokeRemoteService() {
        if (!mIsBound || iRemoteAidl == null) return;
        try {
            String res = iRemoteAidl.func();
            Log.d(TAG, res);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
//        Process.myPid();
//        finishInChildThread();
    }

    public void bindInChildThread() {
        Context context = getBaseContext();
        new Thread(this::finish).start();
//        new Thread(() -> {
        boolean bindSucceed;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bindSucceed = context.bindService(remoteIntent, mConnection, BIND_AUTO_CREATE);
        Log.d(TAG, "isBindSucceed: " + bindSucceed);
//            if (!bindSucceed) {
//                throw new AssertionError("could not bind to KeyChainService");
//            }
//        }).start();
//        finish();
    }

    public void finishInChildThread() {
//        Context context = getBaseContext();
        new Thread(this::finish).start();
    }
}