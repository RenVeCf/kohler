package com.mengyang.kohler.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.account.AccountFragment;
import com.mengyang.kohler.ar.ARFragment;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.home.fragment.HomeFragment;
import com.mengyang.kohler.main.adapter.FragmentViewPagerAdapter;
import com.mengyang.kohler.main.view.TabContainerView;
import com.mengyang.kohler.whole_category.WholeCategoryFragment;
import com.mengyang.kohler.common.view.ResideLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.tab_pager)
    ViewPager tabPager;
    @BindView(R.id.ll_tab_container)
    TabContainerView llTabContainer;
    @BindView(R.id.rl_home)
    ResideLayout rlHome;

    /**
     * tab图标集合
     */
    private final int ICONS_RES[][] = {
            {
                    R.mipmap.ic_launcher_round,
                    R.mipmap.ic_launcher_round
            },
            {
                    R.mipmap.ic_launcher_round,
                    R.mipmap.ic_launcher_round
            },

            {
                    R.mipmap.ic_launcher_round,
                    R.mipmap.ic_launcher_round
            },
            {
                    R.mipmap.ic_launcher_round,
                    R.mipmap.ic_launcher_round
            }
    };

    /**
     * tab 颜色值
     */
    private final int[] TAB_COLORS = new int[]{
            R.color.main_bottom_tab_textcolor_normal,
            R.color.main_bottom_tab_textcolor_selected};

    private Fragment[] fragments = {
            new HomeFragment(),
            new WholeCategoryFragment(),
            new ARFragment(),
            new AccountFragment()
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initValues() {
        initViews();
//        Boolean isFirst = (Boolean) SPUtil.get(this, "isFirst", true);
//        if (isFirst) {
//            rlHome.openPane();
//            SPUtil.put(this, "isFirst", false);
//        }
    }

    @Override
    public void onBackPressed() {
        if (rlHome.isOpen()) {
            rlHome.closePane();
        } else {
            super.onBackPressed();
        }
    }

    private void initViews() {
        FragmentViewPagerAdapter mAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments);
        //设置当前可见Item左右可见page数，次范围内不会被销毁
        tabPager.setOffscreenPageLimit(1);
        tabPager.setAdapter(mAdapter);
        llTabContainer.setOnPageChangeListener(this);
        llTabContainer.initContainer(App.getContext().getResources().getStringArray(R.array.tab_main_title), ICONS_RES, TAB_COLORS, true);
        int width = App.getContext().getResources().getDimensionPixelSize(R.dimen.tab_icon_width);
        int height = App.getContext().getResources().getDimensionPixelSize(R.dimen.tab_icon_height);
        llTabContainer.setContainerLayout(R.layout.tab_container_view, R.id.iv_tab_icon, R.id.tv_tab_text, width, height);
        llTabContainer.setViewPager(tabPager);
        tabPager.setCurrentItem(getIntent().getIntExtra("tab", 0));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int index = 0, len = fragments.length; index < len; index++) {
            fragments[index].onHiddenChanged(index != position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

    }
}
