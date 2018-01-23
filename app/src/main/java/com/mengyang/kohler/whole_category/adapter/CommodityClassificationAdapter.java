package com.mengyang.kohler.whole_category.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/1/22
 */

public class CommodityClassificationAdapter  extends PagerAdapter {

    private ArrayList<View> mPageLists = null;
    private ArrayList<String> mTitleLists = null;

    public CommodityClassificationAdapter(ArrayList<View> pageLists, ArrayList<String> titleLists) {
        this.mPageLists = pageLists;
        this.mTitleLists = titleLists;
    }

    @Override
    public int getCount() {
        return (null == mPageLists) ? 0 :mPageLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View pageView = mPageLists.get(position);
        container.addView(pageView);
        return pageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 将当前位置的View移除
        container.removeView(mPageLists.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (null == mTitleLists && position < mTitleLists.size())
                ? null : mTitleLists.get(position);
    }
}
