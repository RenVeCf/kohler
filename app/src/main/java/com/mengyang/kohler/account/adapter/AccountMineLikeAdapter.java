package com.mengyang.kohler.account.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.LikeListBean;
import com.mengyang.kohler.module.bean.ReservationQueryBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/1/29
 */

public class AccountMineLikeAdapter extends BaseQuickAdapter<LikeListBean.ResultListBean, BaseViewHolder> {
    public AccountMineLikeAdapter(@Nullable List<LikeListBean.ResultListBean> data) {
        super(R.layout.item_account_mine_like_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LikeListBean.ResultListBean item) {
        helper.setText(R.id.tv_account_mine_like_adapter_product_name, "")
                .setText(R.id.tv_account_mine_like_adapter_model_name, "");
        Glide.with(App.getContext()).load("").into((ImageView) helper.getView(R.id.iv_account_mine_like_adapter_item));
        helper.addOnClickListener(R.id.iv_account_mine_like_adapter_remove);
    }
}
