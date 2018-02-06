package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.BooksListBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/1/29
 */

public class BrochureListAdapter extends BaseQuickAdapter<BooksListBean.ResultListBean, BaseViewHolder> {

    public BrochureListAdapter(@Nullable List<BooksListBean.ResultListBean> data) {
        super(R.layout.item_brochure_list_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BooksListBean.ResultListBean item) {
        if (helper.getAdapterPosition() == 0) {
            helper.getView(R.id.iv_brochure_list_adapter_download_item_img).setPadding(5, 0, 0, 0);
        }
        Glide.with(App.getContext()).load(item.getKvUrl()).into((ImageView) helper.getView(R.id.iv_brochure_list_adapter_download_item_img));
        helper.addOnClickListener(R.id.iv_brochure_list_adapter_download_item_img);
        helper.addOnClickListener(R.id.iv_brochure_list_adapter_download_item);
    }
}
