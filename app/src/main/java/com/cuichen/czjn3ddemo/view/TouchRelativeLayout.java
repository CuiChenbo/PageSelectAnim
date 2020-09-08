package com.cuichen.czjn3ddemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class TouchRelativeLayout extends RelativeLayout {
    public TouchRelativeLayout(Context context) {
        super(context);
    }

    public TouchRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;  //消费触摸事件
    }
}
