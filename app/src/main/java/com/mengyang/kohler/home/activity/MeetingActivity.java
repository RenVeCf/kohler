package com.mengyang.kohler.home.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.MeetingBean;
import com.mengyang.kohler.whole_category.adapter.MeetingAdapter;

import java.util.ArrayList;
import java.util.List;
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
    @BindView(R.id.ll_meeting_next)
    LinearLayout llMeetingNext;
    @BindView(R.id.tv_meeting_desc)
    TextView tvMeetingDesc;
    @BindView(R.id.rl_invitation_h5)
    RelativeLayout rlInvitationH5;
    @BindView(R.id.rv_meeting)
    RecyclerView rvMeeting;
    @BindView(R.id.tv_meeting_msg_reminder_push)
    TextView tvMeetingMsgReminderPush;
    private PopupWindow mMeetingPopupWindow;
    private View mPopLayout;
    private MeetingBean mMeetingBean;
    private List<MeetingBean.AgendaListBean> mMeetingAdapterBean;
    private MeetingAdapter mMeetingAdapter;

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

        GridLayoutManager layoutManagerActivity = new GridLayoutManager(App.getContext(), 2);
        rvMeeting.setLayoutManager(layoutManagerActivity);
        rvMeeting.addItemDecoration(new GridSpacingItemDecoration(2, 35, false));
        rvMeeting.setHasFixedSize(true);
        rvMeeting.setItemAnimator(new DefaultItemAnimator());
        //        rvMeeting.setNestedScrollingEnabled(false);

        mMeetingAdapterBean = new ArrayList<>();
        mMeetingAdapter = new MeetingAdapter(mMeetingAdapterBean);
        rvMeeting.setAdapter(mMeetingAdapter);

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
                        Glide.with(App.getContext()).load(mMeetingBean.getKvUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into(ivRealTimeDynamic);
                        mMeetingAdapterBean.clear();
                        mMeetingAdapterBean.addAll(mMeetingBean.getAgendaList());
                        mMeetingAdapter = new MeetingAdapter(mMeetingAdapterBean);
                        rvMeeting.setAdapter(mMeetingAdapter);
                    }
                });
    }

    private void AgendaMsgPush(boolean pushMsg) {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("pushMsg", pushMsg + "");

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getMeetingUserSettings(stringMap)
                .compose(MeetingActivity.this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(MeetingActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {

                    }
                });
    }

    @OnClick({R.id.tv_meeting_next_agenda_time, R.id.tv_meeting_next_agenda_position, R.id.tv_meeting_next_agenda_name, R.id.iv_real_time_dynamic, R.id.ll_meeting_next, R.id.rl_invitation_h5, R.id.ll_meeting_msg_reminder_push})
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
            case R.id.ll_meeting_next:
                mMeetingPopupWindow.showAsDropDown(view, 0, 0);
                break;
            case R.id.rl_invitation_h5:
                startActivity(new Intent(this, MeetingWebActivity.class).putExtra("meeting_web", mMeetingBean.getInvitationH5Url()));
                break;
            case R.id.ll_meeting_msg_reminder_push:
                if (((boolean) SPUtil.get(App.getContext(), IConstants.MEETING_PUSH_MSG, true)) == true) {
                    tvMeetingMsgReminderPush.setText(getResources().getString(R.string.msg_reminder_push_off));
                    AgendaMsgPush(false);
                    SPUtil.put(App.getContext(), IConstants.MEETING_PUSH_MSG, false);
                } else {
                    tvMeetingMsgReminderPush.setText(getResources().getString(R.string.msg_reminder_push_open));
                    AgendaMsgPush(true);
                    SPUtil.put(App.getContext(), IConstants.MEETING_PUSH_MSG, true);
                }
                break;
        }
    }
}
