package com.demoapp.viewdemo;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class LayoutOfDraggableView extends LinearLayout {
    String TAG = "LayoutOfDraggableView";
    private float touchX, touchY;   // 触摸位置
    // 自定义View的参数
    private DraggableView.DraggableViewParams dragParams = new DraggableView.DraggableViewParams();

    DraggableView draggableView;

    public LayoutOfDraggableView(Context context) {
        super(context);
        // 设置为垂直布局
        setOrientation(LinearLayout.VERTICAL);

        // 添加自定义View
        dragParams.viewWidth = 100;
        dragParams.viewHigh = 130; // 高度多30用于显示坐标
        draggableView = new DraggableView(context, dragParams);
        LayoutParams params1 = new LayoutParams((int) dragParams.viewWidth, (int) dragParams.viewHigh);
        addView(draggableView, params1);

        // 添加一个按键
        Button button = new Button(context);
        button.setText("移除");
        LayoutParams params2 = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;
        addView(button, params2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup viewGroup = (ViewGroup) draggableView.getParent().getParent();
                viewGroup.removeView(LayoutOfDraggableView.this);
//                Log.i(TAG, "left=" + dragParams.viewLeft + ";top=" + dragParams.viewTop);
            }
        });

    }

    public LayoutOfDraggableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LayoutOfDraggableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i(TAG, "onLayout");
        // 设置子View的位置，
        draggableView.layout((int) touchX, (int) touchY, (int) (touchX + dragParams.viewWidth), (int) (touchY + dragParams.viewHigh));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i(TAG, "onDraw");
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
                Log.i(TAG, "ACTION_DOWN");
                Log.i(TAG, "X=" + event.getX() + ";Y=" + event.getY());
                // 记录按下时的触摸位置
                touchX = event.getX();
                touchY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "ACTION_MOVE");
                Log.i(TAG, "X=" + event.getX() + ";Y=" + event.getY());
                // 判断触摸区域是否在子View内
                boolean isInRect = dragParams.viewLeft < touchX && touchX < dragParams.viewLeft + dragParams.viewWidth &&
                        dragParams.viewTop < touchY && touchY < dragParams.viewTop + dragParams.viewHigh;
                if (!isInRect) {
                    Log.i(TAG, "ACTION_MOVE is not in subView");
                    return true;
                }
                // 计算手指移动距离
                float dx = event.getX() - touchX;
                float dy = event.getY() - touchY;
                // 通过移动距离设置最新位置
                dragParams.viewLeft += dx;
                dragParams.viewTop += dy;
                // 设置子View的位置
                draggableView.setX(dragParams.viewLeft);
                draggableView.setY(dragParams.viewTop);
                // 重新绘制 View
                draggableView.invalidate();
                invalidate();
                // 更新触摸位置
                touchX = event.getX();
                touchY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ACTION_UP");
                Log.i(TAG, "X=" + event.getX() + ";Y=" + event.getY());
                // 记录抬起时的触摸位置
                touchX = event.getX();
                touchY = event.getY();
                break;
        }
        return true;
    }
}
