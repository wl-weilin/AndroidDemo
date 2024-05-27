package com.demoapp.handwritedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DraggableCircleView extends View {
    private Paint circlePaint;
    private float circleX, circleY;
    private float touchX, touchY;

    public DraggableCircleView(Context context) {
        super(context);
        init();
    }

    public DraggableCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DraggableCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);
        circlePaint.setTextSize(20);
        circleX = circleY = 100; // 初始圆心坐标
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制圆圈
        canvas.drawCircle(circleX, circleY, 50, circlePaint);
        // 绘制当前坐标
        String text = "(" + circleX + ", " + circleY + ")";
        canvas.drawText(text, circleX - 30, circleY + 70, circlePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 设置 View 的默认尺寸为 200x200 像素
//        setMeasuredDimension(200, 200);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录按下时的触摸位置
                touchX = event.getX();
                touchY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 根据手指移动距离更新圆圈位置
                float dx = event.getX() - touchX;
                float dy = event.getY() - touchY;
                circleX += dx;
                circleY += dy;
                // 更新触摸位置
                touchX = event.getX();
                touchY = event.getY();
                // 重新绘制 View
                invalidate();
                break;
        }
        return true;
    }
}

