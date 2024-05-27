package com.demoapp.lifecycledemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "LifecycleDemoMain";
    private MyViewModel viewModel;
    private Lifecycle lifecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLifecycle();
        initViewModel();

        findViewById(R.id.second_activity).setOnClickListener(v -> {
            startActivity(new Intent(this, SecondActivity.class));
        });

        findViewById(R.id.button1).setOnClickListener(v -> {
            // 设置 LiveData 中的数据
            viewModel.setLiveDataValue("Hello, LiveData!");
        });

    }

    public void initLifecycle(){
        // 获取 Lifecycle 对象
        lifecycle = getLifecycle();

        // 创建并注册 LifecycleObserver
        MyLifecycleObserver lifecycleObserverbserver = new MyLifecycleObserver();
        lifecycle.addObserver(lifecycleObserverbserver);
    }

    public void initViewModel() {
        // 使用 ViewModelProvider 获取 ViewModel 对象
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        // 创建 Observer 对象，用于监听 LiveData 中的数据变化
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(String data) {
                // 处理数据变化
                Log.d("LiveData", "Data changed: " + data);
            }
        };
        // 添加观察者
        viewModel.observeLiveData(observer);
    }

    // 自定义 LifecycleObserver 类
    private static class MyLifecycleObserver implements DefaultLifecycleObserver {
        private final String TAG = "MyLifecycleObserver";

        @Override
        public void onCreate(@NonNull LifecycleOwner owner) {
            Log.i(TAG, "Executed onCreate");
            new Throwable().printStackTrace();
        }

        @Override
        public void onStop(@NonNull LifecycleOwner owner) {
            Log.i(TAG, "Executed onStop");
        }

        // 其他生命周期事件的处理方法
    }
}

