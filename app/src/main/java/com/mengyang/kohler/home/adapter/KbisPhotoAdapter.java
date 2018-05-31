package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        int position = helper.getLayoutPosition();
        Glide.with(App.getContext()).load(item.getKvUrl()).into((ImageView) helper.getView(R.id.iv_kbis_photo));
        TextView tvKbisPhotoDes = helper.getView(R.id.tv_kbis_photo_des_top);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tvKbisPhotoDes.getLayoutParams();
        if (position % 4 == 0) {
//            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params.addRule(RelativeLayout.ALIGN_PARENT_START);
            params.setMargins(30, 630, 0, 0);
        } else if (position % 2 != 0) {
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params.addRule(RelativeLayout.ALIGN_PARENT_START);
            params.setMargins(30, 30, 0, 0);
        } else {
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params.addRule(RelativeLayout.ALIGN_PARENT_END);
            params.setMargins(0, 30, 30, 0);
        }
        tvKbisPhotoDes.setLayoutParams(params);
        helper.setText(R.id.tv_kbis_photo_des_top, item.getTitle());
        helper.addOnClickListener(R.id.iv_kbis_photo);
    }
}
