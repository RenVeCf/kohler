package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.ArtKohlerBean;
import com.mengyang.kohler.module.bean.MeetingBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/2
 */

public class ArtKohlerAdapter extends BaseQuickAdapter<ArtKohlerBean.AgendaListBean, BaseViewHolder> {

    public ArtKohlerAdapter(@Nullable List<ArtKohlerBean.AgendaListBean> data) {
        super(R.layout.item_meeting_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArtKohlerBean.AgendaListBean item) {
        String agendaType = "";
        switch (item.getAgendaType()) {
            case -1:
                agendaType = "过期议程";
                break;
            case 0:
                agendaType = "未来议程";
                break;
            case 1:
                agendaType = " 明日议程";
                break;
            case 2:
                agendaType = "下一议程";
                break;
            case 3:
                agendaType = "当前议程";
                break;
        }
        helper.setText(R.id.tv_agenda, agendaType)
                .setText(R.id.tv_meeting_next_agenda_time, item.getTimeSlot())
                .setText(R.id.tv_meeting_next_agenda_position, item.getPlace())
                .setText(R.id.tv_meeting_next_agenda_name, item.getTitle());
        helper.addOnClickListener(R.id.tv_meeting_next_agenda_time);
    }
}
