package com.demoapp.dynamicpicdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private View mRootView;
    private WindowManager mWm;
    private WindowManager.LayoutParams mLayoutParams;
    private boolean mIsShowed;
    private AnimationDrawable mAnim;
    private Handler mMainHandler;
    private Runnable mStartAni = () -> {
        mAnim.setVisible(true, true);
        mAnim.start();
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplication();
        mMainHandler = new Handler(mContext.getMainLooper());

        findViewById(R.id.button1).setOnClickListener(v -> {
            mWm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            initParams();
            initViews();
            mIsShowed = false;
            show();
        });
    }

    private void initParams() {
        mLayoutParams = new WindowManager.LayoutParams();
        //设置弹窗的type
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        //不获取焦点，不全屏
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;
//        //设置位置
//        layoutParams.x = 0;
//        layoutParams.y = getStatusBarHeight(mAppContext);
        //设置一个位置的相对标准
        mLayoutParams.gravity = Gravity.CENTER;
        //宽高包裹内容
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        //悬浮窗一般都是透明效果的，因为怕影响观看桌面的应用
        mLayoutParams.format = PixelFormat.TRANSPARENT;
//        mLayoutParams.privateFlags |= WindowManager.LayoutParams.SYSTEM_FLAG_SHOW_FOR_ALL_USERS;
//        mLayoutParams.windowAnimations = com.android.internal.R.style.Theme_Dialog_Alert;
    }

    private void initViews() {
        mRootView = LayoutInflater.from(this).inflate(R.layout.view_close_phone_tip, null);
        View closeView = mRootView.findViewById(R.id.close_phone_tip_close);

        ImageView closePhoneAniView = mRootView.findViewById(R.id.close_phone_tip_ani);
//        closePhoneAniView.setImageResource(R.drawable.ani_close_phone);
        mAnim = (AnimationDrawable) closePhoneAniView.getDrawable();

        closeView.setOnClickListener(view -> {
            hide();
        });
    }

    void show() {
//        if (mRootView == null) {
//            init();
//        }
        if (mIsShowed) {
            return;
        }
        try {
            mWm.addView(mRootView, mLayoutParams);
            mMainHandler.postDelayed(mStartAni, 500);
            mIsShowed = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void hide() {
        if (mRootView == null) {
            return;
        }
        try {
            mAnim.stop();
            mAnim.setVisible(false, true);
            mMainHandler.removeCallbacks(mStartAni);
            mIsShowed = false;
            mWm.removeView(mRootView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
