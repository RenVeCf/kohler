package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.WeeklyRadioConcertBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/3/6
 */

public class WeeklyRadioConcertAdapter extends BaseQuickAdapter<WeeklyRadioConcertBean.ResultListBean, BaseViewHolder> {
    public WeeklyRadioConcertAdapter(@Nullable List<WeeklyRadioConcertBean.ResultListBean> data) {
        super(R.layout.item_weekly_radio_concert_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeeklyRadioConcertBean.ResultListBean item) {
        if (item.getIsHidden() == 0) {
            helper.setGone(R.id.iv_item_weekly_radio_concert_adapter, true)
                    .setGone(R.id.weekly_radio_concert_adapter_date, true)
                    .setGone(R.id.tv_weekly_radio_concert_adapter_table, true)
                    .setGone(R.id.tv_weekly_radio_concert_adapter_abstract, true)
                    .setGone(R.id.bt_weekly_radio_concert_adapter,true);

            Glide.with(App.getContext()).load(item.getKvUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into((ImageView) helper.getView(R.id.iv_item_weekly_radio_concert_adapter));
            helper.setText(R.id.weekly_radio_concert_adapter_date, item.getCreateTime() + "")
                    .setText(R.id.tv_weekly_radio_concert_adapter_table, item.getTitle())
                    .setText(R.id.tv_weekly_radio_concert_adapter_abstract, item.getDescription());
            helper.addOnClickListener(R.id.bt_weekly_radio_concert_adapter);
        } else {
            helper.setGone(R.id.iv_item_weekly_radio_concert_adapter, false)
            .setGone(R.id.weekly_radio_concert_adapter_date, false)
            .setGone(R.id.tv_weekly_radio_concert_adapter_table, false)
            .setGone(R.id.tv_weekly_radio_concert_adapter_abstract, false)
            .setGone(R.id.bt_weekly_radio_concert_adapter,false);
        }
    }
}
