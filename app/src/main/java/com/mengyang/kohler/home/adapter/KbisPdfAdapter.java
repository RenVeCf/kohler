package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
        int position = helper.getAdapterPosition();

        if (position % 2 == 1) {
            helper.setVisible(R.id.ll_kbis_pdf_top, true);
            helper.setVisible(R.id.ll_kbis_pdf_down, false);

            helper.setText(R.id.tv_kbis_pdf_title_top, item.getTitle());
            helper.setText(R.id.tv_kbis_pdf_des_top, item.getElementDesc());
        } else {
            helper.setVisible(R.id.ll_kbis_pdf_down, true);
            helper.setVisible(R.id.ll_kbis_pdf_top, false);

            helper.setText(R.id.tv_kbis_pdf_title, item.getTitle());
            helper.setText(R.id.tv_kbis_pdf_des, item.getElementDesc());
        }

        Glide.with(App.getContext()).load(item.getKvUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into((ImageView) helper.getView(R.id.iv_kbis_pdf));

        helper.addOnClickListener(R.id.iv_kbis_pdf);
        helper.addOnClickListener(R.id.iv_kbis_pef_download);
        helper.addOnClickListener(R.id.rl_body);

//            helper.addOnClickListener(R.id.tv_kbis_agenda_time);
        }
}
