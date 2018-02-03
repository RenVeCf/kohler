package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.StoreListBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/1/30
 */

public class StoreListAdapter extends BaseQuickAdapter<StoreListBean.ResultListBean, BaseViewHolder> {

    public StoreListAdapter(@Nullable List<StoreListBean.ResultListBean> data) {
        super(R.layout.item_store_list_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreListBean.ResultListBean item) {
        helper.setText(R.id.tv_store_list_name, item.getRoomname())
                .setText(R.id.tv_store_list_address, item.getAddress())
                .setText(R.id.tv_store_list_phone, item.getTel())
                .setText(R.id.tv_store_list_distance, item.getDistance() + "m");
    }
}
