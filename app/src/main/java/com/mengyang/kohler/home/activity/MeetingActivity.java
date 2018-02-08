package com.mengyang.kohler.home.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.MeetingBean;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    @BindView(R.id.ll_meeting_next)
    LinearLayout llMeetingNext;
    @BindView(R.id.tv_meeting_desc)
    TextView tvMeetingDesc;
    private PopupWindow mMeetingPopupWindow;
    private View mPopLayout;
    private MeetingBean mMeetingBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meeting;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvMeetingTop);
        mMeetingPopupWindow = new PopupWindow(this);
        mMeetingPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mMeetingPopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater = LayoutInflater.from(App.getContext());
        mPopLayout = inflater.inflate(R.layout.popup_window_meeting, null);
        mMeetingPopupWindow.setContentView(mPopLayout);
        mMeetingPopupWindow.setBackgroundDrawable(new ColorDrawable(0x4c000000));
        mMeetingPopupWindow.setOutsideTouchable(false);
        mMeetingPopupWindow.setFocusable(true);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Map<String, String> stringMap = IdeaApi.getSign();

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getMeeting(stringMap)
                .compose(MeetingActivity.this.<BasicResponse<MeetingBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<MeetingBean>>(MeetingActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<MeetingBean> response) {
                        mMeetingBean = response.getData();
                        tvMeetingDesc.setText(mMeetingBean.getMeetingDesc());
                    }
                });
    }

    @OnClick({R.id.tv_meeting_next_agenda_time, R.id.tv_meeting_next_agenda_position, R.id.tv_meeting_next_agenda_name, R.id.iv_real_time_dynamic, R.id.tv_meeting_now_agenda_time, R.id.tv_meeting_now_agenda_position, R.id.tv_meeting_now_agenda_name, R.id.tv_meeting_day_agenda_time, R.id.tv_meeting_dayagenda_position, R.id.tv_meeting_day_agenda_name, R.id.tv_meeting_over_agenda_time, R.id.tv_meeting_over_agenda_position, R.id.tv_meeting_over_agenda_name, R.id.ll_meeting_next})
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
            case R.id.ll_meeting_next:
                mMeetingPopupWindow.showAsDropDown(view, 0, 0);
                break;
        }
    }
}
