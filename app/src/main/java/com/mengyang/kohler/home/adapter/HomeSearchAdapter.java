package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.AllSearchBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/1/29
 */

public class HomeSearchAdapter extends BaseQuickAdapter<AllSearchBean, BaseViewHolder> {

    public HomeSearchAdapter(@Nullable List<AllSearchBean> data) {
        super(R.layout.item_home_search_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllSearchBean item) {
        helper.setText(R.id.tv_home_search_adapter_product_name, item.getProductName())
                .setText(R.id.tv_home_search_adapter_model_name, item.getSkuCode());
        Glide.with(App.getContext()).load(item.getListImageUrl()).into((ImageView) helper.getView(R.id.iv_home_search_adapter_item));
    }
}
