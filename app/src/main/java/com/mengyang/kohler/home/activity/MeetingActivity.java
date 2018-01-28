package com.mengyang.kohler.home.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 经销商大会
 */

public class MeetingActivity extends BaseActivity {
    @BindView(R.id.tv_meeting_top)
    TopView tvMeetingTop;
    @BindView(R.id.ll_meeting_msg_reminder_push)
    LinearLayout llMeetingMsgReminderPush;
    @BindView(R.id.tv_meeting_next_agenda_time)
    TextView tvMeetingNextAgendaTime;
    @BindView(R.id.tv_meeting_next_agenda_position)
    TextView tvMeetingNextAgendaPosition;
    @BindView(R.id.tv_meeting_next_agenda_name)
    TextView tvMeetingNextAgendaName;
    @BindView(R.id.iv_real_time_dynamic)
    ImageView ivRealTimeDynamic;
    @BindView(R.id.tv_meeting_now_agenda_time)
    TextView tvMeetingNowAgendaTime;
    @BindView(R.id.tv_meeting_now_agenda_position)
    TextView tvMeetingNowAgendaPosition;
    @BindView(R.id.tv_meeting_now_agenda_name)
    TextView tvMeetingNowAgendaName;
    @BindView(R.id.tv_meeting_day_agenda_time)
    TextView tvMeetingDayAgendaTime;
    @BindView(R.id.tv_meeting_dayagenda_position)
    TextView tvMeetingDayagendaPosition;
    @BindView(R.id.tv_meeting_day_agenda_name)
    TextView tvMeetingDayAgendaName;
    @BindView(R.id.tv_meeting_over_agenda_time)
    TextView tvMeetingOverAgendaTime;
    @BindView(R.id.tv_meeting_over_agenda_position)
    TextView tvMeetingOverAgendaPosition;
    @BindView(R.id.tv_meeting_over_agenda_name)
    TextView tvMeetingOverAgendaName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meeting;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvMeetingTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_meeting_next_agenda_time, R.id.tv_meeting_next_agenda_position, R.id.tv_meeting_next_agenda_name, R.id.iv_real_time_dynamic, R.id.tv_meeting_now_agenda_time, R.id.tv_meeting_now_agenda_position, R.id.tv_meeting_now_agenda_name, R.id.tv_meeting_day_agenda_time, R.id.tv_meeting_dayagenda_position, R.id.tv_meeting_day_agenda_name, R.id.tv_meeting_over_agenda_time, R.id.tv_meeting_over_agenda_position, R.id.tv_meeting_over_agenda_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_meeting_next_agenda_time:
                break;
            case R.id.tv_meeting_next_agenda_position:
                break;
            case R.id.tv_meeting_next_agenda_name:
                break;
            case R.id.iv_real_time_dynamic:
                startActivity(new Intent(this, LiveRealTimeActivity.class));
                break;
            case R.id.tv_meeting_now_agenda_time:
                break;
            case R.id.tv_meeting_now_agenda_position:
                break;
            case R.id.tv_meeting_now_agenda_name:
                break;
            case R.id.tv_meeting_day_agenda_time:
                break;
            case R.id.tv_meeting_dayagenda_position:
                break;
            case R.id.tv_meeting_day_agenda_name:
                break;
            case R.id.tv_meeting_over_agenda_time:
                break;
            case R.id.tv_meeting_over_agenda_position:
                break;
            case R.id.tv_meeting_over_agenda_name:
                break;
        }
    }
}
