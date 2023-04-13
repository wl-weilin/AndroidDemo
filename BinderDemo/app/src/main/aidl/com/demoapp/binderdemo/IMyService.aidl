// IMyService.aidl
package com.demoapp.binderdemo;

// Declare any non-default types here with import statements

interface IMyService {
   oneway void funcA();
   String funB();
}