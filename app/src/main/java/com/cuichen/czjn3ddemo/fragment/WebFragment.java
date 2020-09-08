package com.cuichen.czjn3ddemo.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cuichen.czjn3ddemo.R;
import com.cuichen.czjn3ddemo.ui.VViewPagerAct;
import com.cuichen.czjn3ddemo.utils.DensityUtil;
import com.cuichen.czjn3ddemo.utils.Tu;
import com.cuichen.czjn3ddemo.view.TouchRelativeLayout;

/**
 * ChenboCui
 */
public class WebFragment extends Fragment {

    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web, container, false);
    }

    private ImageView iv;
    private RelativeLayout rlTouchLayout;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        translateAnimation(view);
        webView = (WebView) view.findViewById(R.id.web);
        iv = (ImageView) view.findViewById(R.id.iv);
        rlTouchLayout = (RelativeLayout) view.findViewById(R.id.rlTouchLayout);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);  //启用支持javascript
//        webView.loadUrl("http://103.235.232.73/project/tongyong/demo-v9/");
        webView.loadUrl("https://www.baidu.com/");

        initView();
        initList();
    }

    private void initView() {
        rlTouchLayout.setVisibility(View.INVISIBLE); //默认隐藏
        concealPixels = DensityUtil.dip2px(getActivity(), 30);
        animLayoutPixels = DensityUtil.dip2px(getActivity(), 90);
    }

    private void initList() {
//        webView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                //触摸WebView时、禁止Viewpager滑动事件
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE){
//                    VViewPagerAct webAct = (VViewPagerAct) getActivity();
//                    webAct.setVpNoScroll(true);
//                }
//                return false;
//            }
//        });
//
//        iv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                //触摸WebView时、禁止Viewpager滑动事件
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE){
//                    VViewPagerAct webAct = (VViewPagerAct) getActivity();
//                    webAct.setVpNoScroll(true);
//                }
//                return false;
//            }
//        });
////
//        rlTouchLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                // 触摸上拉布局时 ， 打开ViewPager滑动事件
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE){
//                    VViewPagerAct webAct = (VViewPagerAct) getActivity();
//                    webAct.setVpNoScroll(false);
//                }
//                return true;
//            }
//        });
//
//        rlTouchLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Tu.show(getActivity() , "点我");
//                final VViewPagerAct webAct = (VViewPagerAct) getActivity();
//               webAct.getViewpager().setCurrentItem(1);
//            }
//        });
    }

    private void translateAnimation(View view) {
        ImageView iv_arrow = (ImageView) view.findViewById(R.id.iv_arrow);


        int[] location = new int[2];
        iv_arrow.getLocationOnScreen(location);//获取视图位置
        int x = location[0];
        int y = location[1];
        TranslateAnimation tAnim = new TranslateAnimation(0, 0, 0, DensityUtil.dip2px(getActivity() , -18));//设置视图上下移动的位置
        tAnim .setDuration(1000);
        tAnim .setRepeatCount(Animation.INFINITE);
        tAnim .setRepeatMode(Animation.REVERSE);
        iv_arrow.setAnimation(tAnim);
        tAnim .start();
    }

    //出场动画
    private ObjectAnimator appearAnimator;
    private void appearAnim() {
        if (appearAnimator == null) {
            appearAnimator = ObjectAnimator.ofFloat(rlTouchLayout, "translationY", animLayoutPixels, concealPixels);
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
            appearAnimator.setDuration(600);
        }
        appearAnimator.start();
    }

    private boolean OffsetPixelsAnim; //是否执行跟随手指的位置动画
    private int translationPixels = 0; //阻尼差
    private int concealPixels;  //隐藏区域
    private int animLayoutPixels;  //动画区域高度
    public void onParentPageScrolled(int position, float positionOffset, int positionOffsetPixels){

        l("positionOffset : " + positionOffset
                + "---\n position: " + position
                + "---\n positionOffsetPixels: " + positionOffsetPixels);

           // 跟随移动效果
       int MaxTranslationY = (animLayoutPixels - concealPixels) / 2;
        if (positionOffsetPixels < MaxTranslationY) {
            translationPixels = (positionOffsetPixels - concealPixels)*-1;
        } else {
            translationPixels = (MaxTranslationY - concealPixels)*-1;
        }

        if (OffsetPixelsAnim) {
           if (appearAnimator != null)appearAnimator.cancel();
            rlTouchLayout.setTranslationY(translationPixels);
        }
        OffsetPixelsAnim = true;
    }

    private int onCurrentItem = 1;
    public void onPageScrollStateChanged(int state , int currentItem) {
        if (onCurrentItem == currentItem)return;
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            OffsetPixelsAnim = false;
            if (currentItem == 0) {
                rlTouchLayout.setVisibility(View.VISIBLE);
                appearAnim();
            } else {
                rlTouchLayout.setTranslationY(animLayoutPixels);
                rlTouchLayout.setVisibility(View.INVISIBLE);
            }
            onCurrentItem = currentItem;
        }
    }


    private void l(String s) {
        Log.i("pager_scroll", s);
    }
}
