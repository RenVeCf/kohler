package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.ArtKohlerBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/2
 */

public class GanChuangAdapter extends BaseQuickAdapter<ArtKohlerBean.AgendaListBean, BaseViewHolder> {

    public GanChuangAdapter(@Nullable List<ArtKohlerBean.AgendaListBean> data) {
        super(R.layout.item_meeting_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArtKohlerBean.AgendaListBean item) {
        helper.setText(R.id.tv_agenda, "下期站点")
                .setText(R.id.tv_meeting_next_agenda_time, item.getOnlyDateStr())
                .setText(R.id.tv_meeting_next_agenda_position, item.getPlace())
                .setText(R.id.tv_meeting_next_agenda_name, item.getTitle());
        helper.addOnClickListener(R.id.tv_meeting_next_agenda_time);
    }
}
