package com.mengyang.kohler.account.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.FootPrintBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/1/29
 */

public class FootPrintAdapter extends BaseQuickAdapter<FootPrintBean.ResultListBean, BaseViewHolder> {

    public FootPrintAdapter(@Nullable List<FootPrintBean.ResultListBean> data) {
        super(R.layout.item_foot_print_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FootPrintBean.ResultListBean item) {
        helper.setText(R.id.tv_foot_print_top, item.getName())
                .setText(R.id.tv_foot_print_donw, item.getSkuCode());
        Glide.with(App.getContext()).load(item.getPicture()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into((ImageView) helper.getView(R.id.iv_foot_print));
        helper.addOnClickListener(R.id.tv_foot_print_top)
                .addOnClickListener(R.id.tv_foot_print_donw)
                .addOnClickListener(R.id.iv_foot_print);
    }
}
