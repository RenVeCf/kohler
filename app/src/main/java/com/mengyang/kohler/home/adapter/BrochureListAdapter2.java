package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

public class BrochureListAdapter2 extends BaseQuickAdapter<BooksListBean.ResultListBean, BaseViewHolder> {

    public BrochureListAdapter2(@Nullable List<BooksListBean.ResultListBean> data) {
        super(R.layout.item_home_books_adapter2, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BooksListBean.ResultListBean item) {

        if (item.getPdfUrl() != null && !TextUtils.isEmpty(item.getPdfUrl())) {
            helper.setVisible(R.id.iv_brochure_list_adapter_download_item, false);
        } else {
            helper.setVisible(R.id.iv_brochure_list_adapter_download_item, true);
            helper.setText(R.id.tv_brochure_list_adapter_download_item,  item.getNameCn());
        }

        Glide.with(App.getContext()).load(item.getKvUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into((ImageView) helper.getView(R.id.iv_brochure_list_adapter_download_item_img));
        helper.addOnClickListener(R.id.iv_brochure_list_adapter_download_item_img);
        helper.addOnClickListener(R.id.iv_brochure_list_adapter_download_item);




    }
}
