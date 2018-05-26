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
import com.mengyang.kohler.module.bean.KbisBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/2
 */

public class KbisVideoAdapter extends BaseQuickAdapter<KbisBean.VideoListBean, BaseViewHolder> {

    public KbisVideoAdapter(@Nullable List<KbisBean.VideoListBean> data) {
        super(R.layout.item_kbis_video, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KbisBean.VideoListBean item) {
        helper.setText(R.id.tv_kbis_play_title, item.getTitle())
                .setText(R.id.tv_kbis_play_des, item.getElementDesc());

        Glide.with(App.getContext()).load(item.getKvUrl()).into((ImageView) helper.getView(R.id.iv_kbis));

        helper.addOnClickListener(R.id.iv_kbis);
        helper.addOnClickListener(R.id.iv_kbis_play);
    }
}
