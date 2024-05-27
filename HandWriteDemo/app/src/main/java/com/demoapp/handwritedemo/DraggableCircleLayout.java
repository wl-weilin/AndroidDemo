package com.demoapp.handwritedemo;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.content.Context;
import android.widget.LinearLayout;

public class DraggableCircleLayout extends LinearLayout {

    public DraggableCircleLayout(Context context) {
        super(context);

        DraggableCircleView draggable=new DraggableCircleView(context);
        LayoutParams params = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        addView(draggable, params);
    }
}

