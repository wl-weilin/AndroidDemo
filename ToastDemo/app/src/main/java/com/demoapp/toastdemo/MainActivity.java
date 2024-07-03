package com.demoapp.toastdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String TAG = "ToastDemo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.normal_toast).setOnClickListener(v -> {
            showNormalToast();
        });

        findViewById(R.id.custom_toast).setOnClickListener(v -> {
            showCustomToast();
        });
    }

    // 显示普通Toast
    public void showNormalToast(){
        Toast.makeText(this, "一个普通Toast", Toast.LENGTH_SHORT).show();
    }

    // 显示自定义Toast
    public void showCustomToast(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.custom_toast, null);
        ImageView mImage = mView.findViewById(R.id.toast_image);
        TextView mText = mView.findViewById(R.id.toast_text);
        Toast mToast = new Toast(this);
        mToast.setView(mView);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.BOTTOM, 0, 0);

        mText.setText("ABC");
//        mImage.setImageBitmap();
        mToast.show();
    }
}