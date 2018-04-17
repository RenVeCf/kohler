package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/4/17
 */

public class WorksOfArtAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public WorksOfArtAdapter(@Nullable List<Object> data) {
        super(R.layout.item_works_of_art, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        Glide.with(App.getContext()).load(item).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into((ImageView) helper.getView(R.id.iv_item_works_of_art));
    }
}
