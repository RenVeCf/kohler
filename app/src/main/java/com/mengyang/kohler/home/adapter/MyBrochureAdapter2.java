package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.BooksBean;
import com.mengyang.kohler.module.PdfBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/1/29
 */

public class MyBrochureAdapter2 extends BaseQuickAdapter<PdfBean.userPdfListBean, BaseViewHolder> {

    public MyBrochureAdapter2(@Nullable List<PdfBean.userPdfListBean> data) {
        super(R.layout.item_my_brochure_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PdfBean.userPdfListBean item) {
        if (helper.getAdapterPosition() == 0) {
            helper.getView(R.id.iv_my_brochure_adapter_remove_item_img).setPadding(15, 0, 0, 0);
        }
        Glide.with(App.getContext()).load(item.getBookKVUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into((ImageView) helper.getView(R.id.iv_my_brochure_adapter_remove_item_img));
        helper.addOnClickListener(R.id.iv_my_brochure_adapter_remove_item_img);
        helper.addOnClickListener(R.id.iv_my_brochure_adapter_remove_item);
    }
}
