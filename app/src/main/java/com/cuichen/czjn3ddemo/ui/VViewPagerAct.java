package com.cuichen.czjn3ddemo.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.cuichen.czjn3ddemo.R;
import com.cuichen.czjn3ddemo.fragment.ImageFragment;
import com.cuichen.czjn3ddemo.fragment.WebFragment;
import com.cuichen.czjn3ddemo.view.NoScrollViewPager;
import com.cuichen.czjn3ddemo.view.RiseInTransformer;

import java.util.ArrayList;
import java.util.List;

public class VViewPagerAct extends FragmentActivity {

    private NoScrollViewPager vvp;
    private AppCompatButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去除标题栏
        setContentView(R.layout.activity_viewpager);
        vvp = (NoScrollViewPager) findViewById(R.id.vvp);
        btn = (AppCompatButton) findViewById(R.id.btn);

        initView();
        initList();

        vvp.setPageTransformer(true , new RiseInTransformer());
    }



    public void setVpNoScroll(boolean isNoScroll){
        vvp.setNoScroll(isNoScroll);
    }

    public NoScrollViewPager getViewpager(){
        return vvp;
    }

    private List<Fragment> fragments = new ArrayList<>();


    private WebFragment webFragment = new WebFragment();
    private ImageFragment imageFragment = new ImageFragment();
    private void initView() {
        fragments.add(webFragment);
        fragments.add(imageFragment);

       PagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        vvp.setAdapter(pagerAdapter);
        vvp.setCurrentItem(1);
    }


    private void initList() {
        btn.setVisibility(View.INVISIBLE);
        btn.setText(vvp.isNoScroll() ? "已禁止滑动"  : "已开启滑动");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vvp.setNoScroll(!vvp.isNoScroll());
                btn.setText(vvp.isNoScroll() ? "已禁止滑动"  : "已开启滑动");
            }
        });

        vvp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                imageFragment.onParentPageScrolled(position, positionOffset, positionOffsetPixels);
                webFragment.onParentPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
               imageFragment.onPageScrollStateChanged(state , vvp.getCurrentItem());
               webFragment.onPageScrollStateChanged(state , vvp.getCurrentItem());
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        //无动作、初始状态
                        Log.i("ccb", "---->onPageScrollStateChanged无动作" +vvp.getCurrentItem());
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        //点击、滑屏
                        Log.i("ccb", "---->onPageScrollStateChanged点击、滑屏"+vvp.getCurrentItem());
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        //释放
                        Log.i("ccb", "---->onPageScrollStateChanged释放"+vvp.getCurrentItem());
                        break;

                }
            }
        });
    }
}
