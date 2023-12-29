// IMyService.aidl
package com.demoapp.binderserverdemo;

// Declare any non-default types here with import statements
import com.demoapp.binderclientdemo.Person;
//parcelable Person;

interface IMyService {
   // 非oneway、无返回值、无参数的方法
   void funcA();
   // 非oneway、有返回值、无参数的方法
   String funcB();
   // 非oneway、有返回值、有参数的方法
   String funcC(String str);

   // oneway方法不能有返回值
   // oneway、无返回值、无参数的方法
   oneway void onewayFuncA();
   // oneway、无返回值、有参数的方法
   oneway void onewayFuncB(String str);

   // in: 数据只能由客户端流向服务端。服务端修改参数，不会影响客户端的对象
   String inFunc(in Person person);
   // out: 数据只能由服务端流向客户端。即服务端收到的参数是空对象，并且服务端修改对象后客户端会同步变动
   String outFunc(out Person person);
   // inout: 数据可在服务端与客户端之间双向流通。即服务端能接收到客户端传来的完整对象，并且服务端修改对象后客户端会同步变动。
   String inoutFunc(inout Person person);
}