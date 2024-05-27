package com.demoapp.onetrackdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.miui.analytics.ITrackBinder;

import org.json.JSONObject;

public class ContinuityTrackManager {
    private static final String TAG = "ContinuityTrackManager";

    private static final String SERVICE_PACKAGE_NAME = "com.miui.analytics";
    private static final String SERVICE_NAME = "com.miui.analytics.onetrack.TrackService";
    private static final String APP_ID = "31000000779";
    private static final String PACKAGE_NAME = "com.demoapp.onetrackdemo";
    private static final int FLAG_NOT_LIMITED_BY_USER_EXPERIENCE_PLAN = 1 << 0;
    private static final int FLAG_NON_ANONYMOUS = 1 << 1;

    private static volatile ContinuityTrackManager mContinuityTrackManager;
    private final Context mContext;
    private ITrackBinder mITrackBinder;
    private boolean mIsBound = false;

    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: " + mITrackBinder);
            mITrackBinder = ITrackBinder.Stub.asInterface(service);
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            mIsBound = false;
            mITrackBinder = null;
        }
    };

    private ContinuityTrackManager(Context context) {
        mContext = context;
    }

    public static ContinuityTrackManager getInstance(Context context) {
        if (mContinuityTrackManager == null) {
            synchronized (ContinuityTrackManager.class) {
                if (mContinuityTrackManager == null) {
                    mContinuityTrackManager = new ContinuityTrackManager(context);
                    Log.d(TAG, "ContinuityTrackManager Create");
                }
            }
        }
        return mContinuityTrackManager;
    }

    public void bindTrackBinder() {
        if (mITrackBinder == null) {
            try {
                Intent intent = new Intent();
                intent.setClassName(SERVICE_PACKAGE_NAME, SERVICE_NAME);
                mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Bind Service Exception");
            }
        }
        Toast.makeText(mContext, "Bind Service", Toast.LENGTH_SHORT).show();
    }

    public void unbindTrackBinder() {
        if (!mIsBound) return;
        try {
            mContext.unbindService(mConnection);
            mIsBound = false;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Unbind Service Exception");
        }
    }

    public void trackEvent(JSONObject jsonData) {
        if (!mIsBound ||mITrackBinder == null) {
            Log.d(TAG, "TrackService Not Bound,Please Try Again");
            bindTrackBinder();
            return;
        }
        if (jsonData == null) {
            Log.w(TAG, "JSONObject is null");
            return;
        }
        try {
            mITrackBinder.trackEvent(APP_ID, PACKAGE_NAME, jsonData.toString(),
                    FLAG_NOT_LIMITED_BY_USER_EXPERIENCE_PLAN | FLAG_NON_ANONYMOUS);
            Log.d(TAG, "Send to TrackService Success");
        } catch (RemoteException e) {
            Log.e(TAG, "trackEvent: " + e.getMessage());
        }
    }
}
