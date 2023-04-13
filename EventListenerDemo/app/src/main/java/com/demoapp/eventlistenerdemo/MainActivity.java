package com.demoapp.eventlistenerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    String TAG = "EventListener";
    private View.OnClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button1:
                        Log.d(TAG, "Click Button1");
                        newActivity();
                        break;
                    case R.id.button2:
                        Log.d(TAG, "Click Button2");
                        break;
                    case R.id.button3:
                        Log.d(TAG, "Click Button3");
                        break;
                    default:
                        break;
                }
            }
        };

        addListenterForBotton1(findViewById(R.id.text1));

        findViewById(R.id.button2).setOnClickListener(clickListener);
        findViewById(R.id.button3).setOnClickListener(clickListener);
    }

    void addListenterForBotton1(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick");
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.i(TAG, "onLongClick");
                return false;
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i(TAG, "ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i(TAG, "ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i(TAG, "ACTION_UP");
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void newActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}