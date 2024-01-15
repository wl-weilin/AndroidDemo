package com.demoapp.binderclientdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.demoapp.binderserverdemo.IMyService;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    String TAG = "BinderClientDemoMain";
    private Intent remoteIntent;
    private boolean mIsBound = false;
    // 远程服务的引用
    private IMyService iRemoteServiceStub;
    // 远程服务的连接回调
    private ServiceConnection mConnection;

    // 表示远程Messenger，通过该引用调用服务端的Messenger
    private Messenger mRemoteMessenger;
    // 远程服务的连接回调，通过Messenger实现
    private ServiceConnection mMessengerConnection;
    // 处理服务端回复的Messenger
    private Messenger mReplyMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        findViewById(R.id.bind_service).setOnClickListener(v -> {
            Log.d(TAG, "bindService Executed");
            remoteIntent = new Intent();
            remoteIntent.setPackage("com.demoapp.binderserverdemo");
            remoteIntent.setAction("com.demoapp.binderserverdemo.ServiceAction");
            bindService(remoteIntent, mConnection, BIND_AUTO_CREATE);
        });

        // 非oneway方法调用
        findViewById(R.id.sync_func).setOnClickListener(v -> {
            Log.d(TAG, "call funcA");
            try {
                iRemoteServiceStub.funcA();
//                iServiceStub.funcB();
//                iServiceStub.funcC("123");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

        // oneway方法调用
        findViewById(R.id.oneway_func).setOnClickListener(v -> {
            Log.i(TAG, "Call onewayFunc start");
            try {
                iRemoteServiceStub.onewayFuncA();
//                iServiceStub.onewayFuncB();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            Log.i(TAG, "Call onewayFunc end");
        });

        // in/out/inout参数修饰的方法调用
        findViewById(R.id.in_out_func).setOnClickListener(v -> {
            Log.d(TAG, "call inout func");
            Person person = new Person(1000, "XiaoMing");
            try {
                iRemoteServiceStub.inFunc(person);
//                iServiceStub.outFunc(person);
//                iServiceStub.inoutFunc(person);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            Log.d(TAG, person.toString());
        });

        // 将客户端的回调类发送给服务端，服务端在需要时可以调回到客户端
        findViewById(R.id.send_callback).setOnClickListener(v -> {
            Log.d(TAG, "call setCallback");
            try {
                iRemoteServiceStub.setCallback(new IClientCallback.Stub() {
                    @Override
                    public void scheduleClientFunc() throws RemoteException {
                        MainActivity.this.func();
                    }
                });
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

        // 绑定服务端Service，服务端的Service通过Messenger实现
        findViewById(R.id.bind_messenger).setOnClickListener(v -> {
            Log.d(TAG, "call bindMessenger");
            bindMessenger();
        });

        // 发送跨进程消息
        findViewById(R.id.call_messenger).setOnClickListener(v -> {
            Log.d(TAG, "call callMessenger");
            callMessenger();
        });
    }

    public void init() {
        // 远程Service连接
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "远程服务绑定成功！");
                mIsBound = true;
                //获取远程Service的onBind方法返回的对象的代理
                iRemoteServiceStub = IMyService.Stub.asInterface(service);
            }

            //执行unbindService()不会回调该方法，只有在Service端所在进程崩溃或被杀的时候调用
            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "远程服务已销毁！");
                mIsBound = false;
                iRemoteServiceStub = null;
            }

            @Override
            public void onBindingDied(ComponentName name) {
                Log.d(TAG, "onBindingDied");
            }

            @Override
            public void onNullBinding(ComponentName name) {
                Log.d(TAG, "onNullBinding");
            }
        };

        // 远程Service连接，通过Messenger实现
        mMessengerConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "远程Messenger服务绑定成功！");
                mRemoteMessenger = new Messenger(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "远程服务已销毁！");
            }
        };

        // 定义Handler，处理来自服务端回复的msg
        mReplyMessenger = new Messenger(new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG, "From Client msg = " + msg);
                switch (msg.what) {
                    case 1:
                        Log.i(TAG, Objects.requireNonNull(msg.getData().getString("bundleKey")));
                        break;
                    case 2:
                        Log.i(TAG, Objects.requireNonNull(msg.getData().getString("bundleKey")));
                        break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        });
    }

    public void func() {
        Log.d(TAG, "调用到客户端！");
        new Throwable("Binder Test").printStackTrace();
    }

    public void bindMessenger() {
        Intent messengerIntent = new Intent();
        messengerIntent.setPackage("com.demoapp.binderserverdemo");
        messengerIntent.setAction("com.demoapp.binderserverdemo.MessengerAction");
        bindService(messengerIntent, mMessengerConnection, BIND_AUTO_CREATE);
    }

    public void callMessenger() {
        Message toServermsg = Message.obtain();
        toServermsg.what = 1;
        // 填充要发送到服务端的消息内容。注意：不可以传递未实现Parcelable接口的对象（包括String）
        // 如：toServermsg.obj = "I am msg from Client";否则会抛出错误
        Bundle bundle = new Bundle();
        bundle.putString("bundleKey", "I am msg from Client");
        toServermsg.setData(bundle);
        // 设置客户端接收回复的Messenger
        toServermsg.replyTo = mReplyMessenger;
        try {
            mRemoteMessenger.send(toServermsg);
        } catch (RemoteException e) {
            Log.e(TAG, "Send to ServerMessenger error ", e);
        }
    }
}