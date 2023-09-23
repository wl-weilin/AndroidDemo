package com.demoapp.dialogdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class FloatingWindow {
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private DesktopLayout mWindowViewLayout;
    Button mClosetBtn = null;
    // 声明屏幕的宽高
    float x, y;
    int top;
    Activity mContext;

    public FloatingWindow(Context context) {
        mContext = (Activity) context;
    }

    /**
     * 创建悬浮窗体Layout
     */
    public void createDesktopLayout() {
        mWindowViewLayout = new DesktopLayout(mContext);
        mClosetBtn = mWindowViewLayout.findViewById(R.id.button2);
        mClosetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDesk();
            }
        });
        mWindowViewLayout.setOnTouchListener(onTouchListener);
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        float mTouchStartX;
        float mTouchStartY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // 获取相对屏幕的坐标，即以屏幕左上角为原点
            x = event.getRawX();
            y = event.getRawY() - top; // 25是系统状态栏的高度
            Log.i("testx", "startX" + mTouchStartX + "====startY"
                    + mTouchStartY);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // 获取相对View的坐标，即以此View左上角为原点
                    mTouchStartX = event.getX();
                    mTouchStartY = event.getY();
                    Log.i("testx", "startX" + mTouchStartX + "====startY"
                            + mTouchStartY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    // 更新浮动窗口位置参数
                    mLayoutParams.x = (int) (x - mTouchStartX);
                    mLayoutParams.y = (int) (y - mTouchStartY);
                    mWindowManager.updateViewLayout(v, mLayoutParams);
                    break;
                case MotionEvent.ACTION_UP:

                    // 更新浮动窗口位置参数
                    mLayoutParams.x = (int) (x - mTouchStartX);
                    mLayoutParams.y = (int) (y - mTouchStartY);
                    mWindowManager.updateViewLayout(v, mLayoutParams);

                    // 可以在此记录最后一次的位置

                    mTouchStartX = mTouchStartY = 0;
                    break;
            }
            return true;
        }
    };

//    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        mContext.onWindowFocusChanged(hasFocus);
        Rect rect = new Rect();
        // /取得整个视图部分,注意，如果你要设置标题样式，这个必须出现在标题样式之后，否则会出错
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        top = rect.top;//状态栏的高度，所以rect.height,rect.width分别是系统的高度的宽度

        Log.i("top", "" + top);
    }

    /**
     * 显示DesktopLayout
     */
    public void showDesk() {
        mWindowManager.addView(mWindowViewLayout, mLayoutParams);
        mContext.finish();
    }

    /**
     * 关闭DesktopLayout
     */
    private void closeDesk() {
        mWindowManager.removeView(mWindowViewLayout);
        mContext.finish();
    }

    /**
     * 设置WindowManager
     */
    public void createWindowManager() {
        // 取得系统窗体
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        // 窗体的布局样式
        mLayoutParams = new WindowManager.LayoutParams();

        // 设置窗体显示类型——TYPE_APPLICATION_OVERLAY
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        // 设置显示的模式
        mLayoutParams.format = PixelFormat.RGBA_8888;

        // 设置对齐的方法
        mLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;

        // 设置窗体宽度和高度
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

    }
}

class DesktopLayout extends LinearLayout {

    public DesktopLayout(Context context) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);// 水平排列


        //设置宽高
        this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

        View view = LayoutInflater.from(context).inflate(
                R.layout.window, null);
        this.addView(view);
    }
}