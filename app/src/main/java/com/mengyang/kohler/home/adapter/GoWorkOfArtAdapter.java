package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.ArtKohlerBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/3/12
 */

public class GoWorkOfArtAdapter extends BaseQuickAdapter<ArtKohlerBean.ArtworksBean, BaseViewHolder> {

    public GoWorkOfArtAdapter(@Nullable List<ArtKohlerBean.ArtworksBean> data) {
        super(R.layout.item_designer_introduction_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArtKohlerBean.ArtworksBean item) {
        helper.setText(R.id.tv_item_designer_introduction, item.getTitle());
        Glide.with(App.getContext()).load(item.getKvUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into((ImageView) helper.getView(R.id.iv_item_designer_introduction));
    }
}
