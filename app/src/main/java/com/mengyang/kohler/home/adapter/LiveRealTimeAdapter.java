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
 * Time : 2018/1/29
 */

public class LiveRealTimeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public LiveRealTimeAdapter(@Nullable List<String> data) {
        super(R.layout.item_live_real_time_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_live_real_time_vote_num, "");
        Glide.with(App.getContext()).load("").into((ImageView) helper.getView(R.id.iv_live_real_time_item_img));
    }
}
