package com.demoapp.dialogdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityDialog extends Activity {
    public static final String TAG="ActivityDialog" ;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive");
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        // 设置宽高
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity=Gravity.BOTTOM;

        // 设置显示在底部
        getWindow().setAttributes(layoutParams);

        // 设置圆角边缘
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(50);
        getWindow().setBackgroundDrawable(drawable);

        findViewById(R.id.dialog_button_ok).setOnClickListener(v -> {
            MainActivity.mActivity.finish();
//            finish();
        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.action.test");
//        registerReceiver(this.mBroadcastReceiver, intentFilter);

    }
}