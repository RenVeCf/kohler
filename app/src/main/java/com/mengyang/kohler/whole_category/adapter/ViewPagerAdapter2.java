package com.mengyang.kohler.whole_category.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 商品分类——顶部导航栏适配器
 * Created by ywl on 10/15/15.
 */
public class ViewPagerAdapter2 extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    String[] title;

    public ViewPagerAdapter2(FragmentManager fm, List<Fragment> fragments,String[] title) {
        super(fm);
        this.fragments = fragments;
        this.title = title;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];

    }
}
