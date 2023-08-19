package com.demoapp.jnibycmakedemo;

public class HelloJNI {
    static {
        System.loadLibrary("JNISample");
    }
    public static native void printHello();
    public static native void printString(String str);
    public static native String getJniString();
}
