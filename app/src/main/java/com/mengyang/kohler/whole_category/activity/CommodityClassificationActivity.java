package com.mengyang.kohler.whole_category.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.whole_category.adapter.ViewPagerAdapter;
import com.mengyang.kohler.whole_category.fragment.CommodityClassificationFragment;
import com.mengyang.kohler.whole_category.view.NavitationFollowScrollLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 商品分类
 */

public class CommodityClassificationActivity extends BaseActivity {

    @BindView(R.id.tv_commodity_classification_top)
    TopView tvCommodityClassificationTop;
    @BindView(R.id.nfsl_commodity_classification)
    NavitationFollowScrollLayout nfslCommodityClassification;
    @BindView(R.id.vp_commodity_classification)
    ViewPager vpCommodityClassification;
    private String[] titles = new String[]{"标题一", "标题二", "标题三", "标题四", "标题五", "标题六", "标题七", "标题八", "标题九", "标题十"};
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commodity_classification;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvCommodityClassificationTop);
        fragments = new ArrayList<>();
        fragments.add(new CommodityClassificationFragment());
        fragments.add(new CommodityClassificationFragment());
        fragments.add(new CommodityClassificationFragment());
        fragments.add(new CommodityClassificationFragment());
        fragments.add(new CommodityClassificationFragment());
        fragments.add(new CommodityClassificationFragment());
        fragments.add(new CommodityClassificationFragment());
        fragments.add(new CommodityClassificationFragment());
        fragments.add(new CommodityClassificationFragment());
        fragments.add(new CommodityClassificationFragment());
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        vpCommodityClassification.setAdapter(viewPagerAdapter);

        nfslCommodityClassification.setViewPager(this, titles, vpCommodityClassification, R.color.black, R.color.black, 12, 12, 24, true, R.color.splilinecolor, 1f, 17f, 17f, 63);
        nfslCommodityClassification.setBgLine(this, 1, R.color.white);
        nfslCommodityClassification.setNavLine(this, 3, R.color.white);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
