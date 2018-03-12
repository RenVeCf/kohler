package com.mengyang.kohler.home.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.DesignerIntroductionBean;
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
 * 科勒艺术
 */

public class ArtKohlerActivity extends BaseActivity {

    @BindView(R.id.tv_art_kohler_top)
    TopView tvArtKohlerTop;
    @BindView(R.id.iv_art_kohler_live)
    ImageView ivArtKohlerLive;
    @BindView(R.id.rv_art_kohler_designer)
    RecyclerView rvArtKohlerDesigner;
    @BindView(R.id.rv_art_kohler_select)
    RecyclerView rvArtKohlerSelect;
    @BindView(R.id.iv_art_kohler_video)
    ImageView ivArtKohlerVideo;
    @BindView(R.id.tv_art_kohler_position_zero_agenda_type)
    TextView tvArtKohlerPositionZeroAgendaType;
    @BindView(R.id.tv_art_kohler_position_zero_agenda_time)
    TextView tvArtKohlerPositionZeroAgendaTime;
    @BindView(R.id.tv_art_kohler_position_zero_agenda_position)
    TextView tvArtKohlerPositionZeroAgendaPosition;
    @BindView(R.id.tv_art_kohler_position_zero_agenda_name)
    TextView tvArtKohlerPositionZeroAgendaName;
    @BindView(R.id.ll_art_kohler_position_zero)
    LinearLayout llArtKohlerPositionZero;
    @BindView(R.id.rv_art_kohler_agenda)
    RecyclerView rvArtKohlerAgenda;
    @BindView(R.id.ll_view_all)
    LinearLayout llViewAll;

    //    private DesignerIntroductionAdapter mDesignerIntroductionAdapter;
    private List<DesignerIntroductionBean> mDesignerIntroductionBean;

    private PopupWindow mMeetingPopupWindow;
    private View mPopLayout;
    private MeetingBean mMeetingBean;
    private List<MeetingBean.AgendaListBean> mMeetingAdapterBean;
    private MeetingAdapter mMeetingAdapter;
    //popupwindow 控件
    private TextView tvPopMeetingDate;
    private TextView tvPopMeetingTime;
    private TextView tvPopMeetingPosition;
    private TextView tvPopMeetingName;
    private TextView tvPopMeetingAbstract;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_art_kohler;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvArtKohlerTop);

        mMeetingPopupWindow = new PopupWindow(this);
        mMeetingPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mMeetingPopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater = LayoutInflater.from(App.getContext());
        mPopLayout = inflater.inflate(R.layout.popup_window_meeting, null);
        mPopLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMeetingPopupWindow.dismiss();
            }
        });
        mMeetingPopupWindow.setContentView(mPopLayout);
        mMeetingPopupWindow.setBackgroundDrawable(new ColorDrawable(0x4c000000));
        mMeetingPopupWindow.setOutsideTouchable(false);
        mMeetingPopupWindow.setFocusable(true);

        tvPopMeetingDate = mPopLayout.findViewById(R.id.tv_pop_meeting_date);
        tvPopMeetingTime = mPopLayout.findViewById(R.id.tv_pop_meeting_time);
        tvPopMeetingPosition = mPopLayout.findViewById(R.id.tv_pop_meeting_position);
        tvPopMeetingName = mPopLayout.findViewById(R.id.tv_pop_meeting_name);
        tvPopMeetingAbstract = mPopLayout.findViewById(R.id.tv_pop_meeting_abstract);

        // 设计师介绍
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvArtKohlerDesigner.setLayoutManager(layoutManager);
        rvArtKohlerDesigner.addItemDecoration(new SpacesItemDecoration(13));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvArtKohlerDesigner.setHasFixedSize(true);
        rvArtKohlerDesigner.setItemAnimator(new DefaultItemAnimator());
        //        mDesignerIntroductionBean = new ArrayList<>();
        //        mDesignerIntroductionAdapter = new DesignerIntroductionAdapter(mDesignerIntroductionBean);
        //        rvArtKohlerDesigner.setAdapter(mDesignerIntroductionAdapter);
        mMeetingAdapterBean = new ArrayList<>();
        mMeetingAdapter = new MeetingAdapter(mMeetingAdapterBean);
        rvArtKohlerDesigner.setAdapter(mMeetingAdapter);

        //精选图片
        GridLayoutManager layoutManagerSelect = new GridLayoutManager(App.getContext(), 2);
        rvArtKohlerSelect.setLayoutManager(layoutManagerSelect);
        rvArtKohlerSelect.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        rvArtKohlerSelect.setHasFixedSize(true);
        rvArtKohlerSelect.setItemAnimator(new DefaultItemAnimator());
        rvArtKohlerSelect.setNestedScrollingEnabled(false);

        mMeetingAdapterBean = new ArrayList<>();
        mMeetingAdapter = new MeetingAdapter(mMeetingAdapterBean);
        rvArtKohlerSelect.setAdapter(mMeetingAdapter);

        //议程
        GridLayoutManager layoutManagerActivity = new GridLayoutManager(App.getContext(), 2);
        rvArtKohlerAgenda.setLayoutManager(layoutManagerActivity);
        rvArtKohlerAgenda.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        rvArtKohlerAgenda.setHasFixedSize(true);
        rvArtKohlerAgenda.setItemAnimator(new DefaultItemAnimator());
        rvArtKohlerAgenda.setNestedScrollingEnabled(false);

        mMeetingAdapterBean = new ArrayList<>();
        mMeetingAdapter = new MeetingAdapter(mMeetingAdapterBean);
        rvArtKohlerAgenda.setAdapter(mMeetingAdapter);
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
                .compose(ArtKohlerActivity.this.<BasicResponse<MeetingBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<MeetingBean>>(ArtKohlerActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<MeetingBean> response) {
                        mMeetingBean = response.getData();

                        String agendaType = "";
                        switch (mMeetingBean.getAgendaList().get(0).getAgendaType()) {
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

                        tvArtKohlerPositionZeroAgendaType.setText(agendaType);
                        tvArtKohlerPositionZeroAgendaTime.setText(mMeetingBean.getAgendaList().get(0).getTimeSlot());
                        tvArtKohlerPositionZeroAgendaPosition.setText(mMeetingBean.getAgendaList().get(0).getPlace());
                        tvArtKohlerPositionZeroAgendaName.setText(mMeetingBean.getAgendaList().get(0).getTitle());

                        mMeetingAdapterBean.clear();
                        mMeetingAdapterBean.addAll(mMeetingBean.getAgendaList());
                        mMeetingAdapterBean.remove(0);

                        mMeetingAdapter = new MeetingAdapter(mMeetingAdapterBean);
                        rvArtKohlerAgenda.setAdapter(mMeetingAdapter);
                        rvArtKohlerDesigner.setAdapter(mMeetingAdapter);
                        rvArtKohlerSelect.setAdapter(mMeetingAdapter);
                        mMeetingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                tvPopMeetingDate.setText(mMeetingBean.getAgendaList().get(position + 1).getDateStr());
                                tvPopMeetingTime.setText(mMeetingBean.getAgendaList().get(position + 1).getTimeSlot());
                                tvPopMeetingPosition.setText(mMeetingBean.getAgendaList().get(position + 1).getPlace());
                                tvPopMeetingName.setText(mMeetingBean.getAgendaList().get(position + 1).getTitle());
                                tvPopMeetingAbstract.setText(mMeetingBean.getAgendaList().get(position + 1).getAgendaDesc());
                                if (Build.VERSION.SDK_INT == 24) {//android7.0需要单独做适配
                                    mMeetingPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, getStatusBarHeight());
                                } else {
                                    mMeetingPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
                                }
                            }
                        });
                    }
                });
    }

    @OnClick({R.id.ll_view_all, R.id.iv_art_kohler_live, R.id.iv_art_kohler_video, R.id.ll_art_kohler_position_zero})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_art_kohler_live:
                break;
            case R.id.iv_art_kohler_video:
                break;
            case R.id.ll_art_kohler_position_zero:
                tvPopMeetingDate.setText(mMeetingBean.getAgendaList().get(0).getDateStr());
                tvPopMeetingTime.setText(mMeetingBean.getAgendaList().get(0).getTimeSlot());
                tvPopMeetingPosition.setText(mMeetingBean.getAgendaList().get(0).getPlace());
                tvPopMeetingName.setText(mMeetingBean.getAgendaList().get(0).getTitle());
                tvPopMeetingAbstract.setText(mMeetingBean.getAgendaList().get(0).getAgendaDesc());
                if (Build.VERSION.SDK_INT == 24) {//android7.0需要单独做适配
                    mMeetingPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, getStatusBarHeight());
                } else {
                    mMeetingPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
                }
                break;
            case R.id.ll_view_all:
                startActivity(new Intent(this, DesignerIntroductionActivity.class));
                break;
        }
    }
}
