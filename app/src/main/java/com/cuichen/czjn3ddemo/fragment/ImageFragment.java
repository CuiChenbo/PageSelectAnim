package com.cuichen.czjn3ddemo.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cuichen.czjn3ddemo.R;
import com.cuichen.czjn3ddemo.ui.VViewPagerAct;
import com.cuichen.czjn3ddemo.utils.DensityUtil;

/**
 * ChenboCui
 */
public class ImageFragment extends Fragment {

    private ImageView iv_arrow, iv_icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    private RelativeLayout llAnim;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv_arrow = (ImageView) view.findViewById(R.id.iv_arrow);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        llAnim = (RelativeLayout) view.findViewById(R.id.llAnim);
        initView();
        translateAnimation();
        initList();

    }


    //箭头重复移动的补间动画
    private void translateAnimation() {
//        int[] location = new int[2];
//        iv_arrow.getLocationOnScreen(location);//获取视图位置
//        int x = location[0];
//        int y = location[1];
        TranslateAnimation tAnim = new TranslateAnimation(0, 0, 0, DensityUtil.dip2px(getActivity(), 20));//设置视图上下移动的位置
        tAnim.setDuration(1000);
        tAnim.setRepeatCount(Animation.INFINITE);
        tAnim.setRepeatMode(Animation.REVERSE);
        iv_arrow.setAnimation(tAnim);
        tAnim.start();
    }

    private float lastY;

    private int viewpagerH = 0; //界面高度
    private int dampPixels = 0; //阻尼差
    private int concealPixels;  //隐藏区域
    private int animLayoutPixels;  //动画区域高度

    private boolean OffsetPixelsAnim; //是否执行跟随手指的位置动画

    private void initView() {
        concealPixels = DensityUtil.dip2px(getActivity(), -40);
        animLayoutPixels = DensityUtil.dip2px(getActivity(), -99);

        appearAnim();
    }


    //出场动画
    private ObjectAnimator appearAnimator;
    private void appearAnim() {
        if (appearAnimator == null) {
            appearAnimator = ObjectAnimator.ofFloat(llAnim, "translationY", animLayoutPixels, concealPixels);
            appearAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });
            appearAnimator.setInterpolator(new DecelerateInterpolator());
            appearAnimator.setDuration(500);
        }
        appearAnimator.start();
    }



    private void initList() {
//        webAct.getViewpager().setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                float rawY = event.getY();
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        lastY = rawY;
////                        rl.getParent().requestDisallowInterceptTouchEvent(true); //请求父控件不中断事件
//                        l("ACTION_DOWN   " + rawY);
//                        break;
//
//                    case MotionEvent.ACTION_MOVE:
//                        float dy = (rawY - lastY);
//                        float translationY = (ll.getY() + dy);
////                        l("ACTION_MOVE - DOWN   " + translationY);
//                        ll.setY(translationY);
//                        if (translationY > 100) {
////                            rl.getParent().requestDisallowInterceptTouchEvent(false);//请求父控件中断事件
//                        }
//                        lastY = rawY;
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        l("ACTION_UP   " + event.getY());
//                        break;
//
//                }
//                return false;
//            }
//        });
    }


    public void onParentPageScrolled(int position, float positionOffset, int positionOffsetPixels){
        final VViewPagerAct webAct = (VViewPagerAct) getActivity();
        if (viewpagerH == 0) viewpagerH = webAct.getViewpager().getHeight();

        int translationY = 0;
        if (positionOffsetPixels == 0) {
            translationY = 0;
        } else {
            translationY = viewpagerH - positionOffsetPixels;
        }
        l("positionOffset : " + positionOffset
                + "---\n position: " + position
                + "---\n positionOffsetPixels: " + positionOffsetPixels
                + "---\n translationY: " + translationY);

        // 反向阻尼效果
        if (translationY < 60) {
            dampPixels = translationY * 2;
        } else {
            dampPixels = 60 * 2;
        }

        if (OffsetPixelsAnim) {
            if (appearAnimator != null)appearAnimator.cancel();
            llAnim.setTranslationY(translationY + dampPixels + concealPixels);
        }
        OffsetPixelsAnim = true;
    }

    private int onCurrentItem = 1;
    public void onPageScrollStateChanged(int state , int currentItem) {
        if (onCurrentItem == currentItem)return;
       if (state == ViewPager.SCROLL_STATE_IDLE) {
           OffsetPixelsAnim = false;
           if (currentItem == 1) {
               llAnim.setVisibility(View.VISIBLE);
               appearAnim();
           } else {
               llAnim.setTranslationY(animLayoutPixels);
               llAnim.setVisibility(View.INVISIBLE);
           }
           onCurrentItem = currentItem;
       }
    }


    private void l(String s) {
        Log.i("touch", s);
    }

}
