package com.demoapp.lifecycledemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.demoapp.lifecycledemo.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {
    private final String TAG = "SecondActivity";
    ActivitySecondBinding activityBinding;
    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_second);

        findViewById(R.id.button1).setOnClickListener(v -> {
            // 改变数据查看视图变化
            // user.setName("XiaoMing");
            // 改变视图查看数据变化，或者也可以通过屏幕输入查看
            EditText text_id = findViewById(R.id.text_id);
            text_id.setText("XiaoMing");
        });

        findViewById(R.id.button2).setOnClickListener(v -> {
            Log.i(TAG, mUser.getName());
        });
    }

    public void initDataBinding() {
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_second);
        // 创建要绑定到视图的对象
        mUser = new User();
        // 将对象绑定到视图中
        activityBinding.setUser(mUser);
        // 设置对象中的数据，数据会直接体现在视图中
        // 不需要通过获取到View对象在设置数据
        mUser.setName("ABC");
    }
}