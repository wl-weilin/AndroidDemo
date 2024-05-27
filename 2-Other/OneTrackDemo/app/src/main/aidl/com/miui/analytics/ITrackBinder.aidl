// ITrackService.aidl
package com.miui.analytics;

// Declare any non-default types here with import statements

interface ITrackBinder {

     void trackEvent(String appId ,String pkg , String data ,int flags);

     void trackEvents(String appId,String pkg, inout List<String> dataList ,int flags);
}
