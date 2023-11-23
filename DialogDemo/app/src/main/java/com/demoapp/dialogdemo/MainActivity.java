package com.demoapp.dialogdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "DialogDemoMain";
    public static Activity mActivity;

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive");
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.action.test");
        registerReceiver(this.mBroadcastReceiver, intentFilter);

        findViewById(R.id.popup_window).setOnClickListener(v -> {
            creatPopupWindow();
            onBackPressed();
        });

        findViewById(R.id.alert_dialog).setOnClickListener(v -> {
            creatAlertDialog();
        });

        findViewById(R.id.bottom_dialog).setOnClickListener(v -> {
            creatBottomDialog();
        });

        findViewById(R.id.activity_dialog).setOnClickListener(v -> {
            creatDialogActivity();
//            creatDialogFragment();
        });

        findViewById(R.id.floating).setOnClickListener(v -> {
            createFloatingWindow();
        });

        findViewById(R.id.button).setOnClickListener(v -> {

        });
    }

    private void openActivity() {
        Intent intent1 = new Intent(this, SecondActivity.class);
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
    }

    public void creatPopupWindow() {
        PopupWindow popupWindow = new PopupWindow(this);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        popupWindow.setContentView(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(findViewById(R.id.popup_window), Gravity.TOP, 0, 500);
    }


    public void creatAlertDialog() {
        // context 是当前 Activity 或者 Application 的上下文对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("标题");
        builder.setMessage("弹窗内容");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 点击确定按钮时的回调方法
                // 这里可以添加逻辑代码
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 点击取消按钮时的回调方法
                // 这里可以添加逻辑代码
            }
        });
        AlertDialog dialog = builder.create(); // 创建 AlertDialog 对象
        dialog.setCancelable(false);

        // 显示在底部
//        Window window = dialog.getWindow();
//        window.setGravity(Gravity.BOTTOM);
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        dialog.show(); // 显示弹窗
    }

    public void creatBottomDialog() {
        // 创建 Dialog 对象
        Dialog dialog = new Dialog(this);
        // 设置 Dialog 的布局
        dialog.setContentView(R.layout.dialog_layout);
        // 设置 Dialog 的标题
        dialog.setTitle("这是一个对话框");
        // 设置 Dialog 的消息
        TextView messageTextView = dialog.findViewById(R.id.dialog_title);
        messageTextView.setText("这是对话框中的消息");
        // 设置 Dialog 的按钮
        Button okButton = dialog.findViewById(R.id.dialog_button_ok);
        Button cancelButton = dialog.findViewById(R.id.dialog_button_cancel);

        // 显示在底部
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置按钮的点击事件
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理确定按钮的点击事件
                finish();
//                dialog.dismiss(); // 关闭 Dialog
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理取消按钮的点击事件
                dialog.dismiss(); // 关闭 Dialog
            }
        });
        // 显示 Dialog
        dialog.show();
    }

    public void creatDialogActivity() {
        Intent intent = new Intent(this, ActivityDialog.class);
        startActivity(intent);
    }

    public void creatDialogFragment() {
        MyFragmentDialog dialogFragment = new MyFragmentDialog();
        dialogFragment.show(getSupportFragmentManager(), "MyDialogFragment");
    }

    public void createFloatingWindow() {
        FloatingWindow floatingWindow = new FloatingWindow(this);
        floatingWindow.createWindowManager();
        floatingWindow.createDesktopLayout();
        floatingWindow.showDesk();
    }
}
