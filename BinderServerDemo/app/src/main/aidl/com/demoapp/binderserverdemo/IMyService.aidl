// IMyService.aidl
package com.demoapp.binderserverdemo;

// Declare any non-default types here with import statements

interface IMyService {
    // 带参数和返回值的普通调用
    String say(String str);
    // 异步调用
    oneway void asyncFunc();

}