// IRemoteAidl.aidl
package com.demoapp.servicedemo;

// Declare any non-default types here with import statements

interface IRemoteAidl {
    String func();
    void startDownload();
    void pauseDownload();
    void continueDownload();
    void cancelDownload();
}