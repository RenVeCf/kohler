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

public class ArtKohlerSelectAdapter extends BaseQuickAdapter<ArtKohlerBean.GalleryBean, BaseViewHolder> {

    public ArtKohlerSelectAdapter(@Nullable List<ArtKohlerBean.GalleryBean> data) {
        super(R.layout.item_art_kohler_select_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArtKohlerBean.GalleryBean item) {
        helper.setText(R.id.tv_item_art_select_up, item.getTitle())
                .setText(R.id.tv_item_art_select_down, item.getElementDesc());
        Glide.with(App.getContext()).load(item.getKvUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into((ImageView) helper.getView(R.id.iv_item_art_select));
    }
}
