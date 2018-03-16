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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.activity.WebViewActivity;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.ArtKohlerAdapter;
import com.mengyang.kohler.home.adapter.ArtKohlerSelectAdapter;
import com.mengyang.kohler.home.adapter.DesignerIntroductionAdapter;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.ArtKohlerBean;
import com.umeng.analytics.MobclickAgent;

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
    @BindView(R.id.tv_art_kohler_live)
    TextView tvArtKohlerLive;
    @BindView(R.id.rl_art_kohler_live)
    RelativeLayout rlArtKohlerLive;
    @BindView(R.id.tv_art_kohler_video)
    TextView tvArtKohlerVideo;
    @BindView(R.id.rl_art_kohler_video)
    RelativeLayout rlArtKohlerVideo;

    private DesignerIntroductionAdapter mDesignerIntroductionAdapter;
    private List<ArtKohlerBean.DesignersBean> mDesignerIntroductionAdapterBean;

    private ArtKohlerSelectAdapter mArtKohlerSelectAdapter;
    private List<ArtKohlerBean.GalleryBean> mArtSelectAdapterBean;

    private PopupWindow mMeetingPopupWindow;
    private View mPopLayout;
    private ArtKohlerBean mArtKohlerBean;
    private List<ArtKohlerBean.AgendaListBean> mArtKohlerAdapterBean;
    private ArtKohlerAdapter mArtKohlerAdapter;
    //popupwindow 控件
    private TextView tvPopMeetingDate;
    private TextView tvPopMeetingTime;
    private TextView tvPopMeetingPosition;
    private TextView tvPopMeetingName;
    private TextView tvPopMeetingAbstract;

    String mLive = "";
    String mVideo = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_art_kohler;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvArtKohlerTop);
        MobclickAgent.onEvent(ArtKohlerActivity.this, "shejishanghai");
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
        rvArtKohlerDesigner.addItemDecoration(new SpacesItemDecoration(20));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvArtKohlerDesigner.setHasFixedSize(true);
        rvArtKohlerDesigner.setItemAnimator(new DefaultItemAnimator());

        mDesignerIntroductionAdapterBean = new ArrayList<>();
        mDesignerIntroductionAdapter = new DesignerIntroductionAdapter(mDesignerIntroductionAdapterBean);
        rvArtKohlerDesigner.setAdapter(mDesignerIntroductionAdapter);

        //精选图片
        GridLayoutManager layoutManagerSelect = new GridLayoutManager(App.getContext(), 2);
        rvArtKohlerSelect.setLayoutManager(layoutManagerSelect);
        rvArtKohlerSelect.addItemDecoration(new GridSpacingItemDecoration(2, 50, false));
        rvArtKohlerSelect.setHasFixedSize(true);
        rvArtKohlerSelect.setItemAnimator(new DefaultItemAnimator());
        rvArtKohlerSelect.setNestedScrollingEnabled(false);

        mArtSelectAdapterBean = new ArrayList<>();
        mArtKohlerSelectAdapter = new ArtKohlerSelectAdapter(mArtSelectAdapterBean);
        rvArtKohlerSelect.setAdapter(mArtKohlerSelectAdapter);

        //议程
        GridLayoutManager layoutManagerActivity = new GridLayoutManager(App.getContext(), 2);
        rvArtKohlerAgenda.setLayoutManager(layoutManagerActivity);
        rvArtKohlerAgenda.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        rvArtKohlerAgenda.setHasFixedSize(true);
        rvArtKohlerAgenda.setItemAnimator(new DefaultItemAnimator());
        rvArtKohlerAgenda.setNestedScrollingEnabled(false);

        mArtKohlerAdapterBean = new ArrayList<>();
        mArtKohlerAdapter = new ArtKohlerAdapter(mArtKohlerAdapterBean);
        rvArtKohlerAgenda.setAdapter(mArtKohlerAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Map<String, String> stringMap = IdeaApi.getSign();

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getArtKohler(stringMap)
                .compose(ArtKohlerActivity.this.<BasicResponse<ArtKohlerBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<ArtKohlerBean>>(ArtKohlerActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<ArtKohlerBean> response) {
                        mArtKohlerBean = response.getData();
                        Glide.with(App.getContext()).load(mArtKohlerBean.getLive().getKvUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into(ivArtKohlerLive);
                        tvArtKohlerLive.setText(mArtKohlerBean.getLive().getTitle());
                        mLive = mArtKohlerBean.getLive().getH5Url();
                        Glide.with(App.getContext()).load(mArtKohlerBean.getVideo().getKvUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into(ivArtKohlerVideo);
                        tvArtKohlerVideo.setText(mArtKohlerBean.getVideo().getTitle());
                        mVideo = mArtKohlerBean.getVideo().getH5Url();
                        String agendaType = "";
                        switch (mArtKohlerBean.getAgendaList().get(0).getAgendaType()) {
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
                        tvArtKohlerPositionZeroAgendaTime.setText(mArtKohlerBean.getAgendaList().get(0).getTimeSlot());
                        tvArtKohlerPositionZeroAgendaPosition.setText(mArtKohlerBean.getAgendaList().get(0).getPlace());
                        tvArtKohlerPositionZeroAgendaName.setText(mArtKohlerBean.getAgendaList().get(0).getTitle());

                        mArtKohlerAdapterBean.clear();
                        mArtKohlerAdapterBean.addAll(mArtKohlerBean.getAgendaList());
                        mArtKohlerAdapterBean.remove(0);

                        //议程
                        mArtKohlerAdapter = new ArtKohlerAdapter(mArtKohlerAdapterBean);
                        rvArtKohlerAgenda.setAdapter(mArtKohlerAdapter);
                        //设计介绍
                        mDesignerIntroductionAdapterBean.clear();
                        mDesignerIntroductionAdapterBean.addAll(response.getData().getDesigners());
                        mDesignerIntroductionAdapter = new DesignerIntroductionAdapter(mDesignerIntroductionAdapterBean);
                        rvArtKohlerDesigner.setAdapter(mDesignerIntroductionAdapter);
                        //精选图片
                        mArtSelectAdapterBean.clear();
                        mArtSelectAdapterBean.addAll(response.getData().getGallery());
                        mArtKohlerSelectAdapter = new ArtKohlerSelectAdapter(mArtSelectAdapterBean);
                        rvArtKohlerSelect.setAdapter(mArtKohlerSelectAdapter);
                        mArtKohlerSelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                if (position == 0)
                                    startActivity(new Intent(ArtKohlerActivity.this, ArtKohlerSelectActivity.class).putExtra("select_img", 2));
                                else if (position == 1)
                                    startActivity(new Intent(ArtKohlerActivity.this, ArtKohlerSelectActivity.class).putExtra("select_img", 3));
                                else if (position == 2)
                                    startActivity(new Intent(ArtKohlerActivity.this, ArtKohlerSelectActivity.class).putExtra("select_img", 4));
                                else if (position == 3)
                                    startActivity(new Intent(ArtKohlerActivity.this, ArtKohlerSelectActivity.class).putExtra("select_img", 5));
                            }
                        });

                        mArtKohlerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                tvPopMeetingDate.setText(mArtKohlerBean.getAgendaList().get(position + 1).getDateStr());
                                tvPopMeetingTime.setText(mArtKohlerBean.getAgendaList().get(position + 1).getTimeSlot());
                                tvPopMeetingPosition.setText(mArtKohlerBean.getAgendaList().get(position + 1).getPlace());
                                tvPopMeetingName.setText(mArtKohlerBean.getAgendaList().get(position + 1).getTitle());
                                tvPopMeetingAbstract.setText(mArtKohlerBean.getAgendaList().get(position + 1).getAgendaDesc());
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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @OnClick({R.id.rl_art_kohler_live, R.id.rl_art_kohler_video, R.id.ll_view_all, R.id.ll_art_kohler_position_zero})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_art_kohler_position_zero:
                tvPopMeetingDate.setText(mArtKohlerBean.getAgendaList().get(0).getDateStr());
                tvPopMeetingTime.setText(mArtKohlerBean.getAgendaList().get(0).getTimeSlot());
                tvPopMeetingPosition.setText(mArtKohlerBean.getAgendaList().get(0).getPlace());
                tvPopMeetingName.setText(mArtKohlerBean.getAgendaList().get(0).getTitle());
                tvPopMeetingAbstract.setText(mArtKohlerBean.getAgendaList().get(0).getAgendaDesc());
                if (Build.VERSION.SDK_INT == 24) {//android7.0需要单独做适配
                    mMeetingPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, getStatusBarHeight());
                } else {
                    mMeetingPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
                }
                break;
            case R.id.ll_view_all:
                startActivity(new Intent(this, DesignerIntroductionActivity.class));
                break;
            case R.id.rl_art_kohler_live:
                startActivity(new Intent(this, WebViewActivity.class).putExtra("h5url", mLive));
                break;
            case R.id.rl_art_kohler_video:
                startActivity(new Intent(this, WebViewActivity.class).putExtra("h5url", mVideo));
                break;
        }
    }
}
