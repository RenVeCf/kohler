package com.mengyang.kohler.whole_category.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.whole_category.adapter.CommodityClassificationAdapter;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * 商品分类
 */
public class CommodityClassificationFragment extends BaseFragment {

    @BindView(R.id.rv_commodity_classification)
    RecyclerView rvCommodityClassification;
    private CommodityClassificationAdapter mCommodityClassificationAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_commodity_classification;
    }

    @Override
    protected void initValues() {
        GridLayoutManager layoutManagerActivity = new GridLayoutManager(App.getContext(), 2);
        rvCommodityClassification.setLayoutManager(layoutManagerActivity);
        rvCommodityClassification.addItemDecoration(new GridSpacingItemDecoration(2, 15, false));
        rvCommodityClassification.setHasFixedSize(true);
        rvCommodityClassification.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
