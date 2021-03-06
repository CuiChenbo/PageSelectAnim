package com.cuichen.czjn3ddemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends VerticalViewPager {

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    public boolean isNoScroll() {
        return noScroll;
    }

    // 是否禁止 viewpager 左右滑动  true = 禁止
    private boolean noScroll = false;


    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (noScroll){
            return false;
        }else{
            return super.onTouchEvent(arg0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll){
            return false;
        }else{
            return super.onInterceptTouchEvent(arg0);
        }
    }

}