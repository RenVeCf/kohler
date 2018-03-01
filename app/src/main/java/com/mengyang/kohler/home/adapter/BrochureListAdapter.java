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

public class BrochureListAdapter extends BaseQuickAdapter<BooksListBean.ResultListBean, BaseViewHolder> {

    public BrochureListAdapter(@Nullable List<BooksListBean.ResultListBean> data) {
        super(R.layout.item_brochure_list_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BooksListBean.ResultListBean item) {
        if (item.getPdfUrl() != null && !TextUtils.isEmpty(item.getPdfUrl())) {
            helper.setImageResource(R.id.iv_brochure_list_adapter_download_item, R.mipmap.xiazai);
        } else {
            helper.setImageResource(R.id.iv_brochure_list_adapter_download_item, R.mipmap.video);
        }
        helper.setText(R.id.tv_brochure_list_adapter_download_item,  item.getNameCn());
        Glide.with(App.getContext()).load(item.getKvUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into((ImageView) helper.getView(R.id.iv_brochure_list_adapter_download_item_img));
        helper.addOnClickListener(R.id.iv_brochure_list_adapter_download_item_img);
        helper.addOnClickListener(R.id.iv_brochure_list_adapter_download_item);
    }
}
