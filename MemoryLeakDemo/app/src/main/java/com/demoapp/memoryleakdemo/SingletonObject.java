package com.demoapp.memoryleakdemo;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * 单例模式下，static对象持有Context引用
 */
public class SingletonObject {
    private static SingletonObject mInstance;
    private Context mContext;
    private WeakReference<Context> weakContext;

    private SingletonObject(Context context) {
//        this.mContext = context; // 会造成内存泄露
//        this.mContext = context.getApplicationContext(); // 使用APP生命周期，不会内存泄漏
        weakContext = new WeakReference<>(context);
    }

    public static SingletonObject getInstance(Context context) {

        if (mInstance == null) {
            synchronized (SingletonObject.class) {
                if (mInstance == null) {
                    mInstance = new SingletonObject(context);
                }
            }
        }
        return mInstance;
    }

    public void func() {
//        mContext = weakContext.get(); // 不要使用static SingletonObject mInstance的成员变量获取引用
        Context context = weakContext.get();
        if (context != null) {
            // TODO
        }
    }
}