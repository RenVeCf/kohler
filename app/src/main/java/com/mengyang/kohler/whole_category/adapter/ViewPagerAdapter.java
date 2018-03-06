package com.mengyang.kohler.whole_category.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.mengyang.kohler.module.bean.CommodityClassificationTitleBean;
import com.mengyang.kohler.whole_category.fragment.CommodityClassificationFragment;

import java.util.List;

/**
 * 商品分类——顶部导航栏适配器
 * Created by ywl on 10/15/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

}
