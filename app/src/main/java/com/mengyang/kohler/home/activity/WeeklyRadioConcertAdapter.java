package com.mengyang.kohler.home.activity;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.BooksListBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/3/6
 */

public class WeeklyRadioConcertAdapter extends BaseQuickAdapter<BooksListBean, BaseViewHolder> {
    public WeeklyRadioConcertAdapter(int layoutResId, @Nullable List<BooksListBean> data) {
        super(R.layout.item_weekly_radio_concert_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BooksListBean item) {
        helper.setText(R.id.iv_item_weekly_radio_concert_adapter, "")
                .setText(R.id.weekly_radio_concert_adapter_date, "")
                .setText(R.id.tv_weekly_radio_concert_adapter_table, "")
                .setText(R.id.tv_weekly_radio_concert_adapter_abstract, "");
        helper.addOnClickListener(R.id.bt_weekly_radio_concert_adapter);
    }
}
