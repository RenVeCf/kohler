package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.R;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/1/30
 */

public class StoreListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public StoreListAdapter(@Nullable List<String> data) {
        super(R.layout.item_store_list_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_store_list_name, "")
                .setText(R.id.tv_store_list_address, "")
                .setText(R.id.tv_store_list_phone, "")
                .setText(R.id.tv_store_list_distance, "");
    }
}
