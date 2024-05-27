package com.demoapp.onetrackdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public ContinuityTrackManager mContinuityTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bindService).setOnClickListener(this);
        findViewById(R.id.Send).setOnClickListener(this);
        findViewById(R.id.Click).setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bindService:
//                mContinuityTrack =new ContinuityTrackManager(this);
                mContinuityTrack = ContinuityTrackManager.getInstance(this);
                mContinuityTrack.bindTrackBinder();
                break;

            case R.id.Send:
                if (mContinuityTrack != null) {
                    mContinuityTrack.sendToTrack("send");
                }
                break;

            case R.id.Click:
                if (mContinuityTrack != null) {
                    mContinuityTrack.sendToTrack("click");
                }

                break;
        }

    }
}