package com.mengyang.kohler.whole_category.adapter;

import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.module.bean.CommodityClassificationFragmentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/loading1/22
 */

public class CommodityClassificationAdapter extends BaseQuickAdapter<CommodityClassificationFragmentBean.ResultListBean, BaseViewHolder> {

    public CommodityClassificationAdapter(@Nullable List<CommodityClassificationFragmentBean.ResultListBean> data) {
        super(R.layout.item_commodity_classification_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityClassificationFragmentBean.ResultListBean item) {
        helper.setText(R.id.tv_commodity_classification_adapter_product_name, item.getProDetail().getProductName())
                .setText(R.id.tv_commodity_classification_adapter_model_name, item.getProDetail().getSkuCode());
        Glide.with(App.getContext()).load(item.getProDetail().getListImageUrl()).into((ImageView) helper.getView(R.id.iv_commodity_classification_adapter_item));
        helper.addOnClickListener(R.id.iv_commodity_classification_adapter_item);
    }
}
