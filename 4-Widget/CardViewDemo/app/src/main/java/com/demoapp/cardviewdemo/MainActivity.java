package com.demoapp.cardviewdemo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = View.inflate(this, R.layout.popup_menu, null);

        findViewById(R.id.button1).setOnClickListener(v -> {
            PopupWindow popupWindow = new PopupWindow(this);
            popupWindow.setContentView(view);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.showAtLocation(findViewById(R.id.layout_main), Gravity.NO_GRAVITY, 100, 500);
        });
    }
}