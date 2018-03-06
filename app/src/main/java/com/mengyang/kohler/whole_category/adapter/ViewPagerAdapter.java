package com.mengyang.kohler.whole_category.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.mengyang.kohler.module.bean.CommodityClassificationTitleBean;
import com.mengyang.kohler.whole_category.fragment.CommodityClassificationFragment;

import java.util.List;

/**
 * 商品分类——顶部导航栏适配器
 * Created by ywl on 10/15/15.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
//    private List< CommodityClassificationTitleBean > beanList;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
//    public ViewPagerAdapter(FragmentManager fm, List< CommodityClassificationTitleBean > beanList) {
        super(fm);
        this.fragments = fragments;
//        this.beanList = beanList;
    }

    @Override
    public int getCount() {
        return fragments.size();
//        return beanList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
//        return CommodityClassificationFragment.newInstance(beanList.get(position).getId()+"");
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
//    }
}
