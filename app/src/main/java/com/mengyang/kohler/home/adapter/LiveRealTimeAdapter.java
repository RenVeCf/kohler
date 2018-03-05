package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.module.bean.LiveRealTimeBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/1/29
 */

public class LiveRealTimeAdapter extends BaseQuickAdapter<LiveRealTimeBean.ResultListBean, BaseViewHolder> {

    public LiveRealTimeAdapter(@Nullable List<LiveRealTimeBean.ResultListBean> data) {
        super(R.layout.item_live_real_time_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveRealTimeBean.ResultListBean item) {
        LogUtils.i("rmy", "item.getLikeCount() = " + item.getLikeCount());
        helper.setText(R.id.tv_live_real_time_vote_num, item.getLikeCount() + "");
        Glide.with(App.getContext()).load(item.getPicUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into((ImageView) helper.getView(R.id.iv_live_real_time_item_img));
        helper.addOnClickListener(R.id.ll_live_real_time_adapter_vote);
    }
}
