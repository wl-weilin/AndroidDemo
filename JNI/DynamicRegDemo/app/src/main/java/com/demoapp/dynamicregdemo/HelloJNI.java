package com.demoapp.dynamicregdemo;

public class HelloJNI {
    static {
        System.loadLibrary("dynamicregdemo");
    }
    public static native void printHello();
    public static native void printString(String str);
    public static native String getJniString();
}
