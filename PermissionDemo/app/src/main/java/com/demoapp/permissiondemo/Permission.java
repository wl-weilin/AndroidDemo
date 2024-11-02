package com.demoapp.permissiondemo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permission {

    static boolean isPermissionGrand(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    static void requestPermissions(Activity activity, String permission[]) {
//        ActivityCompat.requestPermissions(activity, permission);

    }
}
