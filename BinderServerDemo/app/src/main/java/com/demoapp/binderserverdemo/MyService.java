package com.demoapp.binderserverdemo;

import android.app.Service;
import android.content.Intent;
import android.database.SQLException;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {
    String TAG = "MyService";

    public MyService() {
    }

    IMyService.Stub stubImpl = new StubImpl() ;

    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind executed");
        return stubImpl;
    }

    //模拟耗时任务，参数为耗时时间
    public void timeoutTask(long duration){
        final long cur = System.currentTimeMillis();
        while (System.currentTimeMillis() <= cur + duration) {
            //
        }
    }
}

class StubImpl extends IMyService.Stub {
    String TAG = "MyService";
    @Override
    public void funcA() throws RemoteException {
        Log.d(TAG, "Call funcA");
        throw new RuntimeException();
//        return "I am funB";
    }

    @Override
    public String funcB() throws SecurityException {
        Log.d(TAG, "Call funcB");
        throw new SecurityException();
//        return "I am funcB";
    }
}