package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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

    private int mHeight;
    private int mWidth;

    public KbisVideoAdapter(@Nullable List<KbisBean.VideoListBean> data) {
        super(R.layout.item_kbis_video, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final KbisBean.VideoListBean item) {
        int position = helper.getLayoutPosition();
//        if (position == 2)
            LogUtils.i("rmy", "item.getKvUrl() = " + item.getKvUrl() + ", position = " + helper.getAdapterPosition());

        Glide.with(App.getContext()).load(item.getKvUrl()).into((ImageView) helper.getView(R.id.iv_kbis));

//        ViewGroup.LayoutParams imageLayoutParams = helper.getView(R.id.iv_kbis).getLayoutParams();
//        if (helper.getLayoutPosition() % 2 == 0) {
//            imageLayoutParams.width = 440;//获取实际展示的图片宽度
//            imageLayoutParams.height = 687;//获取最终图片高度
//        } else {
//            imageLayoutParams.width = 440;//获取实际展示的图片宽度
//            imageLayoutParams.height = 440;//获取最终图片高度
//        }
//        helper.getView(R.id.iv_kbis).setLayoutParams(imageLayoutParams);//应用高度到布局中

        helper.setText(R.id.tv_kbis_play_title, item.getTitle())
                .setText(R.id.tv_kbis_play_des, item.getElementDesc());

        helper.addOnClickListener(R.id.iv_kbis);
        helper.addOnClickListener(R.id.iv_kbis_play);
    }
}
