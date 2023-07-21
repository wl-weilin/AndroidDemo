package com.demoapp.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    public String TAG = "ServiceDemo";
    private Intent remoteIntent;
    private Intent localIntent;
    private boolean isRemote;
    private boolean mIsBound = false;
    private LocalBinder mLocalBinder;     //本地服务的代理
    private IRemoteAidl mRemoteBinder;    //远程服务的代理

    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "远程服务绑定成功！");
            mIsBound = true;
            //获取Service的onBind方法返回的对象的代理
            if (service instanceof LocalBinder) {
                mLocalBinder = (LocalBinder) service;
                isRemote = false;
            } else {
                mRemoteBinder = IRemoteAidl.Stub.asInterface(service);
                isRemote = true;
            }
        }

        //执行unbindService()不会回调该方法，只有在Service被杀死的时候调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "远程服务已销毁！");
            mLocalBinder = null;
            mRemoteBinder = null;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localIntent = new Intent(this, MyService.class);
        remoteIntent = new Intent();
        // Android 5.0以后只设置action不能启动相应的服务，需要设置packageName或者Component
        remoteIntent.setPackage("com.demoapp.servicedemo");
        remoteIntent.setAction("com.demoapp.servicedemo.ServiceAction");
        remoteIntent.putExtra("isRemote", true);

        findViewById(R.id.start_service).setOnClickListener(v -> {
            Log.d(TAG, "startForegroundService Executed");
            startService(localIntent);
//            startForegroundService(remoteIntent);
        });

        findViewById(R.id.stop_service).setOnClickListener(v -> {
            stopService(remoteIntent);
        });

        findViewById(R.id.bind_local).setOnClickListener(v -> {
            bindService(localIntent, mConnection, BIND_AUTO_CREATE); // 绑定服务,成功返回true

        });

        findViewById(R.id.bind_remote).setOnClickListener(v -> {
            bindService(remoteIntent, mConnection, BIND_AUTO_CREATE); // 绑定服务,成功返回true
        });

        findViewById(R.id.unbind_service).setOnClickListener(v -> {
            if (!mIsBound) return;
            unbindService(mConnection);
            mIsBound = false;
            mLocalBinder = null;
            mRemoteBinder = null;
        });

        findViewById(R.id.start_download).setOnClickListener(v -> {
            startDownload();
        });

        findViewById(R.id.pause_download).setOnClickListener(v -> {
            pauseDownload();
        });

        findViewById(R.id.continue_download).setOnClickListener(v -> {
            continueDownload();
        });

        findViewById(R.id.invoke_service).setOnClickListener(v -> {
            invokeFunc();
        });
    }


    public void startDownload() {
        if (!mIsBound) return;
        if (isRemote) {
            try {
                mRemoteBinder.startDownload();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            mLocalBinder.startDownload();
        }
    }

    public void pauseDownload() {
        if (!mIsBound) return;
        if (isRemote) {
            try {
                mRemoteBinder.pauseDownload();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            mLocalBinder.pauseDownload();
        }
    }

    public void continueDownload() {
        if (!mIsBound) return;
        if (isRemote) {
            try {
                mRemoteBinder.continueDownload();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            mLocalBinder.continueDownload();
        }
    }

    public void invokeFunc() {
        if (!mIsBound) return;
        if (isRemote) {
            try {
                mRemoteBinder.func();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            mLocalBinder.func();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(localIntent);
    }
}