package com.demoapp.binderdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {
    public MyService() {
    }

    IMyService.Stub stub = new IMyService.Stub() {
        @Override
        public void funcA() throws RemoteException {
            timeoutTask(5000);

        }

        @Override
        public String funB() throws RemoteException {
            return "I am funB";
        }

        //模拟耗时任务，参数为耗时时间
        public void timeoutTask(long duration){
            final long cur = System.currentTimeMillis();
            while (System.currentTimeMillis() <= cur + duration) {
                //
            }
        }
    };

    public IBinder onBind(Intent intent) {
        return stub;
    }
}