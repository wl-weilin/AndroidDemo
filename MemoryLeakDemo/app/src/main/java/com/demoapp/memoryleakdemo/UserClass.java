package com.demoapp.memoryleakdemo;

import android.content.Context;

import java.lang.ref.WeakReference;


public class UserClass {
    private static volatile UserClass mInstance;
    private Context mContext;
    private WeakReference<Context> weakContext;

    private UserClass(Context context) {
//        this.mContext = context;

//        this.mContext = context.getApplicationContext();

        weakContext = new WeakReference<>(context);
//        this.mContext = weakContext.get();

    }

    public static UserClass getInstance(Context context) {
        if (mInstance == null) {
            synchronized (UserClass.class) {
                if (mInstance == null) {
                    mInstance = new UserClass(context);
                }
            }
        }
        return mInstance;
    }

    public void dealData() {
    }
}