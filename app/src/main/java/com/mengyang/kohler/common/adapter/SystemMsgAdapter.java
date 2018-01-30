package com.mengyang.kohler.common.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.R;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/1/29
 */

public class SystemMsgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SystemMsgAdapter(@Nullable List<String> data) {
        super(R.layout.item_system_msg_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_system_msg_adapter_prompt, "")
                .setText(R.id.tv_system_msg_adapter_day_num, "");
    }
}
