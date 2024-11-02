package com.demoapp.screendemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class ScreenUtils {
    public static String TAG = "ScreenUtils";

    public static void getRealScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(metrics);

        float widthInches = metrics.widthPixels / (metrics.xdpi);
        float heightInches = metrics.heightPixels / (metrics.ydpi);
        double screenDiagonal = Math.sqrt((widthInches * widthInches) + (heightInches * heightInches));
        Toast.makeText((Activity) context, "宽=" + widthInches + "in;" + "高=" + heightInches + "in;" +
                "对角=" + screenDiagonal + "in", Toast.LENGTH_SHORT).show();
        float inTocm = 2.54F;
        Log.i(TAG, "宽=" + widthInches * inTocm + "cm;" +
                "高=" + heightInches * inTocm + "cm;" +
                "对角=" + screenDiagonal * inTocm + "cm");
    }

    /**
     * 通过Display.getRealMetrics(metrics)获取的宽度，通常与Display.getMetrics(metrics)一致
     */
    public static void getRealScreenPixel(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(metrics);
        Toast.makeText(context, "全屏分辨率：宽*高=" +
                metrics.widthPixels + "*" + metrics.heightPixels, Toast.LENGTH_SHORT).show();
    }


    public static void getAvailablePixel(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        Toast.makeText(context, "可用分辨率：宽*高=" +
                metrics.widthPixels + "*" + metrics.heightPixels, Toast.LENGTH_SHORT).show();
    }

    public static void getStatusBarHeight(Context context) {
        int result = 0;
        @SuppressLint({"InternalInsetResource", "DiscouragedApi"})
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        Toast.makeText(context, "状态栏高度=" +
                result + "px", Toast.LENGTH_SHORT).show();
    }

    public static void getNavigationBarHeight(Context context) {
        int result = 0;
        @SuppressLint({"InternalInsetResource", "DiscouragedApi"})
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        Toast.makeText(context, "导航栏高度=" +
                result + "px", Toast.LENGTH_SHORT).show();
    }

    public static void getXYDpi(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(metrics);
        Toast.makeText(context, "xdpi=" + metrics.xdpi + "; ydpi=" + metrics.ydpi, Toast.LENGTH_SHORT).show();
    }

    public static void getDensity(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(metrics);
        Toast.makeText(context, "density=" + metrics.density, Toast.LENGTH_SHORT).show();
    }

    public static void getDiagonalDpi(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(metrics);
        // 计算对角线像素数量
        int diagonalPixel = (int) Math.sqrt(metrics.widthPixels * metrics.widthPixels +
                metrics.heightPixels * metrics.heightPixels);
        // 计算对角线屏幕尺寸，单位：英寸
        float widthInches = metrics.widthPixels / (metrics.xdpi);
        float heightInches = metrics.heightPixels / (metrics.ydpi);
        double screenDiagonal = Math.sqrt((widthInches * widthInches) + (heightInches * heightInches));
        Log.i(TAG, "对角像素数=" + diagonalPixel +
                "; 对角线尺寸" + screenDiagonal);
        int trueDpi = (int) (diagonalPixel / screenDiagonal);
        Toast.makeText(context, "实际DPI=" + trueDpi +
                "; 标准DPI=" + metrics.densityDpi, Toast.LENGTH_SHORT).show();
    }

}
