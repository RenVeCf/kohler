package com.mengyang.kohler.home.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
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

public class KbisPdfAdapter extends BaseMultiItemQuickAdapter<KbisBean.PdfListBean, BaseViewHolder> {
//public class KbisPdfAdapter extends BaseQuickAdapter<KbisBean.PdfListBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public KbisPdfAdapter(List<KbisBean.PdfListBean> data) {
        super(data);
        addItemType(0,R.layout.item_kbis_pdf_01);
        addItemType(1,R.layout.item_kbis_pdf_02);
    }

    @Override
    protected void convert(BaseViewHolder helper, KbisBean.PdfListBean item) {
        switch (item.getItemType()) {
            case 0:
                helper.setText(R.id.tv_kbis_pdf_title_top, item.getTitle());
                helper.setText(R.id.tv_kbis_pdf_des_top, item.getElementDesc());
//                helper.setImageResource(R.id.iv_kbis_pdf, R.mipmap.num_1);
                Glide.with(App.getContext()).load(item.getKvUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into((ImageView) helper.getView(R.id.iv_kbis_pdf));

                helper.setBackgroundColor(R.id.iv_kbis_pdf, Color.parseColor("#000000"));

                helper.addOnClickListener(R.id.rl_body);
                break;
            case 1:

                helper.setText(R.id.tv_kbis_pdf_title, item.getTitle());
                helper.setText(R.id.tv_kbis_pdf_des, item.getElementDesc());
//                helper.setImageResource(R.id.iv_kbis_pdf, R.mipmap.num_2);
                Glide.with(App.getContext()).load(item.getKvUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into((ImageView) helper.getView(R.id.iv_kbis_pdf));

                helper.setBackgroundColor(R.id.iv_kbis_pdf, Color.parseColor("#000000"));

                helper.addOnClickListener(R.id.rl_body);
                break;
            default:
                break;
        }

    }


/*
    public KbisPdfAdapter(@Nullable List<KbisBean.PdfListBean> data) {
        super(R.layout.item_kbis_pdf, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KbisBean.PdfListBean item) {
        int position = helper.getAdapterPosition();

        switch ((position % 4)) {
            case 0:
                helper.setVisible(R.id.rl_body_long, true);
                helper.setVisible(R.id.rl_body_short, false);

                helper.setText(R.id.tv_kbis_pdf_title, item.getTitle());
                helper.setText(R.id.tv_kbis_pdf_des, item.getElementDesc());
                break;
            case 1:
                helper.setVisible(R.id.rl_body_long, false);
                helper.setVisible(R.id.rl_body_short,true);

                helper.setText(R.id.tv_kbis_pdf_title_top, item.getTitle());
                helper.setText(R.id.tv_kbis_pdf_des_top, item.getElementDesc());
                break;
            case 2:
                helper.setVisible(R.id.rl_body_long, false);
                helper.setVisible(R.id.rl_body_short,true);

                helper.setText(R.id.tv_kbis_pdf_title_top, item.getTitle());
                helper.setText(R.id.tv_kbis_pdf_des_top, item.getElementDesc());
                break;
            case 3:
                helper.setVisible(R.id.rl_body_long, true);
                helper.setVisible(R.id.rl_body_short, false);

                helper.setText(R.id.tv_kbis_pdf_title, item.getTitle());
                helper.setText(R.id.tv_kbis_pdf_des, item.getElementDesc());
                break;
            default:
                break;
        }

  *//*      if ((position % 0) == 1) {
            helper.setGone(R.id.rl_body1, false);
            helper.setGone(R.id.rl_body2,true);

            helper.setText(R.id.tv_kbis_pdf_title_top, item.getTitle());
            helper.setText(R.id.tv_kbis_pdf_des_top, item.getElementDesc());
        } else {
            helper.setGone(R.id.rl_body2, false);
            helper.setGone(R.id.rl_body1, true);

            helper.setText(R.id.tv_kbis_pdf_title, item.getTitle());
            helper.setText(R.id.tv_kbis_pdf_des, item.getElementDesc());
        }*//*

        Glide.with(App.getContext()).load(item.getKvUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into((ImageView) helper.getView(R.id.iv_kbis_pdf));

        helper.addOnClickListener(R.id.iv_kbis_pdf);
        helper.addOnClickListener(R.id.iv_kbis_pef_download);
        helper.addOnClickListener(R.id.rl_body);

//            helper.addOnClickListener(R.id.tv_kbis_agenda_time);
        }*/
}
