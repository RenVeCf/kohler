package com.mengyang.kohler.account.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.ReservationQueryBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/1/29
 */

public class AccountMineReservationQueryAdapter extends BaseQuickAdapter<ReservationQueryBean.ResultListBean, BaseViewHolder> {

    public AccountMineReservationQueryAdapter(@Nullable List<ReservationQueryBean.ResultListBean> data) {
        super(R.layout.item_account_mine_reservation_query_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReservationQueryBean.ResultListBean item) {
        helper.setText(R.id.tv_reservation_query_time_of_appointment, item.getAppointmentTime())
                .setText(R.id.tv_reservation_query_store_address, item.getStoreAddress())
                .setText(R.id.tv_reservation_query_reservation_code, item.getCode());
    }
}
