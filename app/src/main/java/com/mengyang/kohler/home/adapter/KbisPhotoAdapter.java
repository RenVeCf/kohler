package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.KbisBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/2
 */

public class KbisPhotoAdapter extends BaseQuickAdapter<KbisBean.PhotoListBean, BaseViewHolder> {

    public KbisPhotoAdapter(@Nullable List<KbisBean.PhotoListBean> data) {
        super(R.layout.item_kbis_photo, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KbisBean.PhotoListBean item) {
        int position = helper.getAdapterPosition();

        if (position % 2 == 1) {
            helper.setVisible(R.id.tv_kbis_photo_des_top, true);
            helper.setVisible(R.id.tv_kbis_photo_des_down, false);

            helper.setText(R.id.tv_kbis_photo_des_top, item.getElementDesc());
        } else {
            helper.setVisible(R.id.tv_kbis_photo_des_down, true);
            helper.setVisible(R.id.tv_kbis_photo_des_top, false);

            helper.setText(R.id.tv_kbis_photo_des_down, item.getElementDesc());
        }

        Glide.with(App.getContext()).load(item.getKvUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into((ImageView) helper.getView(R.id.iv_kbis_photo));

        helper.addOnClickListener(R.id.iv_kbis_photo);
        helper.addOnClickListener(R.id.rl_body_photo);
        }
}
