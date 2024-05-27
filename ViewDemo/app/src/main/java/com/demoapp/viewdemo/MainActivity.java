package com.demoapp.viewdemo;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.add_view).setOnClickListener(v -> {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
            addContentView(new LayoutOfDraggableView(this),params);

//            ViewGroup viewGroup= findViewById(android.R.id.content);
//            viewGroup.addView(mView);

        });

        findViewById(R.id.remove_view).setOnClickListener(v -> {
            ViewGroup viewGroup= (ViewGroup) mView.getParent();
            viewGroup.removeView(mView);
//            viewGroup.invalidate();
        });
    }
}