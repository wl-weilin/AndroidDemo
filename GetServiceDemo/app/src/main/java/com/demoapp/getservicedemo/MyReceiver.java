package com.demoapp.getservicedemo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.demoapp.servicedemo.IRemoteAidl;

public class MyReceiver extends BroadcastReceiver {
    String TAG = "MyReceiver";
    String myAction1 = "com.example.broadcast.ACTION1";
    String myAction2 = "com.example.broadcast.ACTION2";

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

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        remoteIntent = new Intent();
        // Android 5.0以后只设置action不能启动相应的服务，需要设置packageName或者Component
        remoteIntent.setPackage("com.demoapp.servicedemo");
        remoteIntent.setAction("com.demoapp.servicedemo.ServiceAction");
        remoteIntent.putExtra("isRemote", true);
        Log.d(TAG, context.toString());
        context.bindService(remoteIntent, mConnection, context.BIND_AUTO_CREATE);
    }

    //模拟耗时任务，参数为耗时时间
    public void timeoutTask(long duration){
        final long cur = System.currentTimeMillis();
        while (System.currentTimeMillis() <= cur + duration) {
            //
        }
    }
}
