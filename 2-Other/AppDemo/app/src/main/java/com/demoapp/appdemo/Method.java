package com.demoapp.appdemo;

/**
 * 一些常用函数
 */
public class Method {
    //模拟耗时任务，参数为耗时时间
    public void timeoutTask(long duration){
        final long cur = System.currentTimeMillis();
        while (System.currentTimeMillis() <= cur + duration) {
            //
        }
    }
}
