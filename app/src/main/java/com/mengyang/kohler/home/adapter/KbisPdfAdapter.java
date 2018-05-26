package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.KbisBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/2
 */

public class KbisPdfAdapter extends BaseQuickAdapter<KbisBean.PdfListBean, BaseViewHolder> {
    public KbisPdfAdapter(@Nullable List<KbisBean.PdfListBean> data) {
        super(R.layout.item_kbis_pdf, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KbisBean.PdfListBean item) {
        LinearLayout llKbisPdf = helper.getView(R.id.ll_kbis_pdf);
        RelativeLayout.LayoutParams params_2 = (RelativeLayout.LayoutParams) llKbisPdf.getLayoutParams();

        if (helper.getLayoutPosition() % 2 != 0) {
            params_2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params_2.setMargins(0, 10, 0, 0);
        } else {
            params_2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params_2.setMargins(0, 0, 0, 20);
        }
        llKbisPdf.setLayoutParams(params_2);
        Glide.with(App.getContext()).load(item.getKvUrl()).into((ImageView) helper.getView(R.id.iv_kbis_pdf_bg));
        helper.setText(R.id.tv_kbis_pdf_title, item.getTitle())
                .setText(R.id.tv_kbis_pdf_name, item.getElementDesc())
                .addOnClickListener(R.id.iv_kbis_pdf_bg)
                .addOnClickListener(R.id.iv_kbis_pdf_download);
    }
}
