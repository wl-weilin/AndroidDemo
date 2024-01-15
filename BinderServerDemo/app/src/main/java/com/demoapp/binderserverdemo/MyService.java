package com.demoapp.binderserverdemo;

import android.app.Service;
import android.content.Intent;
import android.database.SQLException;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.demoapp.binderclientdemo.IClientCallback;

public class MyService extends Service {
    String TAG = "MyService";

    public MyService() {
    }

    IMyService.Stub stubImpl = new StubImpl();

    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind executed");
        return new StubImpl();
    }

    //模拟耗时任务，参数为耗时时间
    public static void timeoutTask(long duration) {
        final long cur = System.currentTimeMillis();
        while (System.currentTimeMillis() <= cur + duration) {
            //
        }
    }
}

class StubImpl extends IMyService.Stub {
    String TAG = "MyService";
    IClientCallback mCallback;

    public void useClientCallback() throws RemoteException {
        mCallback.scheduleClientFunc();
    }

    @Override
    public void funcA() throws RemoteException {
        Log.d(TAG, "Call funcA");
    }

    @Override
    public String funcB() throws SecurityException {
        Log.d(TAG, "Call funcB");
        return null;
    }

    @Override
    public String funcC(String str) throws RemoteException {
        Log.d(TAG, "Call funcC");
        return null;
    }

    @Override
    public void onewayFuncA() throws RemoteException {
        Log.i(TAG, "Call onewayFunc start");
        MyService.timeoutTask(1000);
        Log.i(TAG, "Call onewayFunc end");
    }

    @Override
    public void onewayFuncB(String str) throws RemoteException {

    }

    @Override
    public String inFunc(Person person) throws RemoteException {
        Log.d(TAG, person.toString());
        person.mId=1001;
        person.mName="XiaoHong";
        return null;
    }

    @Override
    public String outFunc(Person person) throws RemoteException {
        Log.d(TAG, person.toString());
        person.mId=1001;
        person.mName="XiaoHong";
        return null;
    }

    @Override
    public String inoutFunc(Person person) throws RemoteException {
        Log.d(TAG, person.toString());
        person.mId=1001;
        person.mName="XiaoHong";
        return null;
    }

    @Override
    public void setCallback(IClientCallback callback) throws RemoteException {
        Log.d(TAG, "Call Service setCallback");
        // 设置客户端的回调接口
        mCallback = callback;
        // 调用到客户端
        useClientCallback();
        // 客户端的死亡回调
        mCallback.asBinder().linkToDeath(new DeathRecipient() {
            @Override
            public void binderDied() {
                Log.i(TAG, "The Client died!");
            }
        },0);
    }
}