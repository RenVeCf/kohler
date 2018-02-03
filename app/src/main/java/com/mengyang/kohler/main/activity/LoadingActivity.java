package com.mengyang.kohler.main.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.DisplayUtils;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.utils.LanguageUtil;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.main.adapter.ContentAdapter;
import com.mengyang.kohler.main.view.LockableViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 引导页
 */

public class LoadingActivity extends BaseActivity {

    @BindView(R.id.lvp_loading)
    LockableViewPager lvpLoading;
    @BindView(R.id.bt_loading_login)
    Button btLoadingLogin;
    private int[] imageUrls = {R.mipmap.loading1, R.mipmap.loading2, R.mipmap.loading3, R.mipmap.loading4, R.mipmap.loading5};
    private int[] imageUrlsEn = {R.mipmap.loading1, R.mipmap.loading2, R.mipmap.loading3, R.mipmap.loading4, R.mipmap.loading5};
    private ContentAdapter adapter;
    private List<View> viewList;
    private int currentItem = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loading;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        // 判断是否是第一次开启应用
        boolean isFirstOpen = (boolean) SPUtil.get(this, IConstants.FIRST_APP, true);
        if (!isFirstOpen) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        //沉浸式状态栏初始化白色
        ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(false).init();
        DisplayUtils.getScreenHeight(this);
        viewList = new ArrayList<>();
        if (LanguageUtil.isZh(this)) {
            //            btLoadingLogin.setBackgroundResource(R.mipmap.ic_launcher_round);
            for (int i = 0; i < imageUrls.length; i++) {
                viewList.add(initView(imageUrls[i]));
            }
        } else {
            //            btLoadingLogin.setBackgroundResource(R.mipmap.ic_launcher_round);
            for (int i = 0; i < imageUrlsEn.length; i++) {
                viewList.add(initView(imageUrlsEn[i]));
            }
        }
        adapter = new ContentAdapter(viewList, null);
        lvpLoading.setSwipeable(true);
        lvpLoading.setAdapter(adapter);
        lvpLoading.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 4) {
                    btLoadingLogin.setVisibility(View.VISIBLE);
                } else {
                    btLoadingLogin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        lvpLoading.setOnTouchListener(new View.OnTouchListener() {
            float startX;
            float startY;//没有用到
            float endX;
            float endY;//没有用到

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = motionEvent.getX();
                        startY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        endX = motionEvent.getX();
                        endY = motionEvent.getY();
                        //首先要确定的是，是否到了最后一页，然后判断是否向左滑动，并且滑动距离是否符合，我这里的判断距离是屏幕宽度的4分之一（这里可以适当控制）
                        if (currentItem == (viewList.size() - 1) && startX - endX >= (DisplayUtils.getScreenWidth(LoadingActivity.this) / 4)) {
                            Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        break;
                }
                return false;
            }
        });
    }

    private View initView(int imagePathId) {
        ImageView imageView = new ImageView(getBaseContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(imagePathId);
        return imageView;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.bt_loading_login)
    public void onViewClicked() {
        startActivity(new Intent(LoadingActivity.this, MainActivity.class));
        finish();
    }
}
