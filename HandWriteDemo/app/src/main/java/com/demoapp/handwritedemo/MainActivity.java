package com.demoapp.handwritedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private GraffitiView mGraffitiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(new DraggableCircleView(this));
//        mGraffitiView = findViewById(R.id.graffiti_view);
//        initGraffitiView();

    }

    private void initGraffitiView(){
        findViewById(R.id.btn_undo).setOnClickListener(v -> {
            mGraffitiView.undo();
        });

        findViewById(R.id.btn_do).setOnClickListener(v -> {
            mGraffitiView.redo();
        });

        findViewById(R.id.btn_red).setOnClickListener(v -> {
            mGraffitiView.resetPaintColor(Color.RED);
        });

        findViewById(R.id.btn_blue).setOnClickListener(v -> {
            mGraffitiView.resetPaintColor(Color.BLUE);
        });

        findViewById(R.id.btn_green).setOnClickListener(v -> {
            mGraffitiView.resetPaintColor(Color.GREEN);
        });

        findViewById(R.id.btn_scral).setOnClickListener(v -> {
            mGraffitiView.resetPaintWidth();
        });

        findViewById(R.id.btn_eraser).setOnClickListener(v -> {
            mGraffitiView.eraser();
        });
    }

}