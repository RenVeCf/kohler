package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/loading1/22
 */

public class HomeBooksAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HomeBooksAdapter(@Nullable List<String> data) {
        super(R.layout.item_home_books_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Glide.with(App.getContext()).load("").into((ImageView) helper.getView(R.id.iv_item_home_books));
    }
}
