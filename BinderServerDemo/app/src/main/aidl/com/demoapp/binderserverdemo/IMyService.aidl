// IMyService.aidl
package com.demoapp.binderserverdemo;

// Declare any non-default types here with import statements

interface IMyService {
   oneway void funcA();
   String funcB();
}