package com.demoapp.dynamicregdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.demoapp.dynamicregdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private final String TAG="DynamicRegDemoMain";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(v -> {
            HelloJNI.printHello();
            HelloJNI.printString("传递参数：APP->Native");
            Log.i(TAG,HelloJNI.getJniString());
        });
    }
}