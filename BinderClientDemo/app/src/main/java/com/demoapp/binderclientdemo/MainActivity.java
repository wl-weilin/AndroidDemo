package com.demoapp.binderclientdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.demoapp.binderserverdemo.IMyService;

public class MainActivity extends AppCompatActivity {
    String TAG = "BinderClientDemoMain";
    private IMyService iServiceStub;
    private Intent remoteIntent;
    private boolean mIsBound = false;


    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "远程服务绑定成功！");
            mIsBound = true;
            //获取远程Service的onBind方法返回的对象的代理
            iServiceStub = IMyService.Stub.asInterface(service);
        }

        //执行unbindService()不会回调该方法，只有在Service端所在进程崩溃或被杀的时候调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "远程服务已销毁！");
            mIsBound = false;
            iServiceStub = null;
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

        remoteIntent = new Intent();
        // Android 5.0以后只设置action不能启动相应的服务，需要设置packageName或者Component
        remoteIntent.setPackage("com.demoapp.binderserverdemo");
        remoteIntent.setAction("com.demoapp.binderserverdemo.ServiceAction");

        findViewById(R.id.bind_service).setOnClickListener(v -> {
            Log.d(TAG, "bindService Executed");
            bindService(remoteIntent, mConnection, BIND_AUTO_CREATE);
        });

        findViewById(R.id.func_a).setOnClickListener(v -> {
            Log.d(TAG, "call funcA");
            try {
                iServiceStub.funcA();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

        findViewById(R.id.func_b).setOnClickListener(v -> {
            Log.d(TAG, "call funcB");
            try {
                iServiceStub.funcB();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

        findViewById(R.id.func_c).setOnClickListener(v -> {
            Log.d(TAG, "call funcC");
            try {
                iServiceStub.funcC("123");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

        findViewById(R.id.func_in_out).setOnClickListener(v -> {
            Log.d(TAG, "call inout func");
            String str="123";
            Person person=new Person(1000,"xiaoming");
            try {
                iServiceStub.funcC(str);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            Log.d(TAG, str);
        });
    }
}