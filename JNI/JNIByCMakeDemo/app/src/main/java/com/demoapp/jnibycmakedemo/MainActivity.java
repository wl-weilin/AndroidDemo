package com.demoapp.jnibycmakedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private final String TAG="JNIByCMakeDemoMain";
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