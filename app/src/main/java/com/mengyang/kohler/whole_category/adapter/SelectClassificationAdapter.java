package com.mengyang.kohler.whole_category.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.SelectClassificationBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/2
 */

public class SelectClassificationAdapter extends BaseQuickAdapter<SelectClassificationBean, BaseViewHolder> {

    public SelectClassificationAdapter(@Nullable List<SelectClassificationBean> data) {
        super(R.layout.item_select_classification_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectClassificationBean item) {
        Glide.with(App.getContext()).load(item.getKvUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into((ImageView) helper.getView(R.id.iv_select_classification));
        helper.addOnClickListener(R.id.iv_select_classification);
    }
}
