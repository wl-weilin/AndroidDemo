package com.demoapp.toastdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;

public class SnackbarDemo {
    Activity mActivity;
    public SnackbarDemo(Context context){
        mActivity= (Activity) context;
    }

    // 显示普通Snackbar
    public void showNormalSnackbar(View view) {
        // 创建 Snackbar 实例
        Snackbar snackbar = Snackbar.make(mActivity.findViewById(android.R.id.content), "普通Snackbar", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("移除", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        // 显示 Snackbar
        snackbar.show();
    }

    public void showSnackbarLikeToast() {
        // 创建 Snackbar 实例
        Snackbar snackbar = Snackbar.make(mActivity.findViewById(android.R.id.content), "Toast-like", Snackbar.LENGTH_INDEFINITE);

        // 获取 Snackbar 的 View
        ViewGroup snackbarView = (ViewGroup) snackbar.getView();
//        Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();

        // 获取 Snackbar 的 TextView 并设置文本居中对齐
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        // 获取SnackbarContentLayout并设置居中显示
        SnackbarContentLayout contentLayout=(SnackbarContentLayout) snackbarView.getChildAt(0);
        FrameLayout.LayoutParams contentLayoutParams = (FrameLayout.LayoutParams) contentLayout.getLayoutParams();
        contentLayoutParams.gravity=Gravity.CENTER;

        // 设置 Snackbar 水平居中显示，宽度，底部距离
        // 这里宽度设置为WRAP_CONTENT无效，需要设置为具体数字
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                300,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置为居中、底部显示，并设置距离屏幕底部高度
        params.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
        params.bottomMargin = 200;
        snackbarView.setLayoutParams(params);

        // 显示 Snackbar
        snackbar.show();
    }

    public void showSnackbarLikeToastByLayout() {
        // 获取根布局
        View rootView = mActivity.findViewById(android.R.id.content);

        // 创建 Snackbar
        Snackbar snackbar = Snackbar.make(rootView, "", Snackbar.LENGTH_INDEFINITE);
        ViewGroup snackbarView = (ViewGroup) snackbar.getView();

        // 获取自定义布局
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View customView = inflater.inflate(R.layout.snackbar_like_toast, null);

        // 配置自定义布局
        TextView textView = customView.findViewById(R.id.text);
        textView.setText("Toast-like");

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = 300;
        // 设置为居中、底部显示，并设置距离屏幕底部高度
        params.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
        params.bottomMargin = 200;
        snackbarView.setLayoutParams(params);

        // 将自定义视图设置到 Snackbar 中
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.removeAllViews();
        snackbarLayout.addView(customView, 0);

        // 显示 Snackbar
        snackbar.show();
    }

    public void showCustomSnackbar(View view) {
        // 获取根布局
        View rootView = mActivity.findViewById(android.R.id.content);

        // 创建 Snackbar
        Snackbar snackbar = Snackbar.make(rootView, "", Snackbar.LENGTH_LONG);

        // 获取自定义布局
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View customView = inflater.inflate(R.layout.custom_snackbar_layout, null);

        // 配置自定义布局
        TextView textView = customView.findViewById(R.id.text);
        textView.setText("这是一个自定义 Snackbar");

        // 将自定义视图设置到 Snackbar 中
        snackbar.getView().setLayoutParams(new Snackbar.SnackbarLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.removeAllViews();
        snackbarLayout.addView(customView, 0);

        // 显示 Snackbar
        snackbar.show();
    }
}
