package com.demoapp.nativedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.demoapp.nativedemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "NativeDemoMain";
    // Used to load the 'nativedemo' library on application startup.
    static {
        System.loadLibrary("nativedemo");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(v -> {
            makeCrash();
        });
    }

    /**
     * A native method that is implemented by the 'nativedemo' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native void makeCrash();
}