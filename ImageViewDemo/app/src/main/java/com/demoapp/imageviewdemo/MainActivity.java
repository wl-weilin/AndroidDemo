package com.demoapp.imageviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    String TAG="ImageViewDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(v -> {
            Log.i(TAG,"click");
//            ImageView image = findViewById(R.id.image);
//            image.setLayoutParams(new LinearLayout.LayoutParams(1000,1000));

            View imageLayout = findViewById(R.id.image_layout);
            imageLayout.measure(50, 50);
            setViewBounds(imageLayout, 50, 50);
            imageLayout.invalidate();
        });
    }

    private void setViewBounds(View view, int widthPx, int heightPx) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(widthPx, heightPx);
            view.setLayoutParams(lp);
        } else {
            lp.height = heightPx;
            lp.width = widthPx;
        }
        view.setLayoutParams(lp);
    }
}