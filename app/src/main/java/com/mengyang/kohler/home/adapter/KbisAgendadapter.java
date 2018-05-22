package com.mengyang.kohler.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.KbisBean;
import com.mengyang.kohler.module.bean.MeetingBean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/2
 */

public class KbisAgendadapter extends BaseQuickAdapter<KbisBean.AgendaListBean, BaseViewHolder> {

    public KbisAgendadapter(@Nullable List<KbisBean.AgendaListBean> data) {
        super(R.layout.item_kbis_agenda_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KbisBean.AgendaListBean item) {
        String agendaType = "";
        int adapterPosition = helper.getAdapterPosition();
        if (adapterPosition == 0) {
            helper.setVisible(R.id.ll_first, true);
            helper.setVisible(R.id.ll_item_second, false);

        } else {

            helper.setVisible(R.id.ll_first, false);
            helper.setVisible(R.id.ll_item_second, true);
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
            helper.setText(R.id.tv_kbis_agenda_item_title, agendaType)
                    .setText(R.id.tv_kbis_agenda_time, item.getTimeSlot())
                    .setText(R.id.tv_kbis_agenda_position, item.getPlace())
                    .setText(R.id.tv_kbis_agenda_name, item.getTitle());
            helper.addOnClickListener(R.id.tv_kbis_agenda_time);
        }



    }
}
