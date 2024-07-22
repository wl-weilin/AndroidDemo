package com.demoapp.viewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

/**
 * View包含两部分：矩形色块区和坐标显示
 */
public class DraggableView extends View {
    String TAG = "DraggableView";
    private Paint paint;                // 画笔
    private float leftRect, topRect;    // 表示此View在屏幕中的坐标
    private float widthRect, highRect;          // 矩形区的宽高
    private float highText;
    private GestureDetector gestureDetector;

    DraggableViewParams dragParams;

    public DraggableView(Context context, DraggableViewParams params) {
        super(context);
        this.dragParams = params;
        init(params);

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Toast.makeText(context,"单击自定义View",Toast.LENGTH_SHORT).show();
                Log.i(TAG,this.getClass().getTypeName());
                return true;
            }
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // 双击事件的处理逻辑
                Toast.makeText(context,"双击自定义View",Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    private void init(DraggableViewParams dragParams) {
        // 设置画笔颜色及字体大小
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize(20);
        // 设置View的背景颜色
        setBackgroundColor(Color.GREEN);

        highText = 30;
        leftRect = dragParams.viewLeft;
        topRect = dragParams.viewTop;
        widthRect = dragParams.viewWidth;
        highRect = dragParams.viewHigh - highText;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制矩形，坐标原点是Activity内的左上点
        canvas.drawRect(leftRect, topRect, leftRect + widthRect, topRect + highRect, paint);

        // 绘制当前坐标
        String text = "(" + (int) getX() + ", " + (int) getY() + ")";
        canvas.drawText(text, leftRect, topRect + highRect + 20, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "View.width=" + widthMeasureSpec + ";View.height=" + heightMeasureSpec);
        // 设置 View 的默认尺寸
//        setMeasuredDimension((int) width, (int) high);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        ViewParent parent = getParent();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG,"subView ACTION_DOWN");
                Log.i(TAG, "subView X=" + event.getX() + ";Y=" + event.getY());
                event.offsetLocation(dragParams.viewLeft,dragParams.viewTop);
//                ((ViewGroup) parent).onTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG,"subView ACTION_MOVE");
                event.offsetLocation(dragParams.viewLeft,dragParams.viewTop);
//                ((ViewGroup) parent).onTouchEvent(event);
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG,"subView ACTION_UP");
//                ((ViewGroup) parent).onTouchEvent(event);
                break;
        }
        return true;
    }

    public static class DraggableViewParams {
        public float viewLeft, viewTop;     // 子View的左上角坐标
        public float viewWidth, viewHigh;   // 设置子View的宽高
    }
}



