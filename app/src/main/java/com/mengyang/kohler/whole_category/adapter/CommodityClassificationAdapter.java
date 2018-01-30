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

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/loading1/22
 */

public class CommodityClassificationAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CommodityClassificationAdapter(@Nullable List<String> data) {
        super(R.layout.item_commodity_classification_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_commodity_classification_adapter_product_name, "")
                .setText(R.id.tv_commodity_classification_adapter_model_name, "");
        Glide.with(App.getContext()).load("").into((ImageView) helper.getView(R.id.iv_commodity_classification_adapter_item));
    }
}
