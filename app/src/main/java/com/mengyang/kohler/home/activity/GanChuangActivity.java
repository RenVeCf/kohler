package com.mengyang.kohler.home.activity;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allyes.analytics.AIOAnalytics;
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
import com.mengyang.kohler.home.adapter.ArtKohlerSelectAdapter;
import com.mengyang.kohler.home.adapter.DesignerIntroductionAdapter;
import com.mengyang.kohler.home.adapter.GanChuangAdapter;
import com.mengyang.kohler.home.adapter.GoWorkOfArtAdapter;
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
 * 敢创•科勒亚太艺术展
 */

public class GanChuangActivity extends BaseActivity {
    @BindView(R.id.tv_ganchuang_top)
    TopView tvGanchuangTop;
    @BindView(R.id.rl_ganchuang_video)
    RelativeLayout rlGanchuangVideo;
    @BindView(R.id.ll_go_works_of_art)
    LinearLayout llGoWorksOfArt;
    @BindView(R.id.rv_go_works_of_art)
    RecyclerView rvGoWorksOfArt;
    @BindView(R.id.ll_go_artists)
    LinearLayout llGoArtists;
    @BindView(R.id.rv_ganchuang_artists)
    RecyclerView rvGanchuangArtists;
    @BindView(R.id.rv_ganchuang_gallery)
    RecyclerView rvGanchuangGallery;
    @BindView(R.id.tv_ganchuang_position_zero_agenda_type)
    TextView tvGanchuangPositionZeroAgendaType;
    @BindView(R.id.tv_ganchuang_position_zero_agenda_time)
    TextView tvGanchuangPositionZeroAgendaTime;
    @BindView(R.id.tv_ganchuang_position_zero_agenda_position)
    TextView tvGanchuangPositionZeroAgendaPosition;
    @BindView(R.id.tv_ganchuang_position_zero_agenda_name)
    TextView tvGanchuangPositionZeroAgendaName;
    @BindView(R.id.ll_ganchuang_position_zero)
    LinearLayout llGanchuangPositionZero;
    @BindView(R.id.rv_art_kohler_agenda)
    RecyclerView rvArtKohlerAgenda;
    @BindView(R.id.tv_ganchuang_video)
    TextView tvGanChuangVideo;

    private DesignerIntroductionAdapter mDesignerIntroductionAdapter;
    private GoWorkOfArtAdapter mGoWorkOfArtAdapter;
    private List<ArtKohlerBean.DesignersBean> mDesignerIntroductionAdapterBean;

    private ArtKohlerSelectAdapter mArtKohlerSelectAdapter;
    private List<ArtKohlerBean.GalleryBean> mArtSelectAdapterBean;
    private List<ArtKohlerBean.ArtworksBean> mGoWorksOfArtAdapterBean;
    private ArtKohlerBean mArtKohlerBean;
    private List<ArtKohlerBean.AgendaListBean> mArtKohlerAdapterBean;
    private GanChuangAdapter mGanChuangAdapter;
    private String mVideo = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gan_chuang;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvGanchuangTop);
        MobclickAgent.onEvent(GanChuangActivity.this, "ganchuang");

        // 展品介绍
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvGoWorksOfArt.setLayoutManager(layoutManager);
        rvGoWorksOfArt.addItemDecoration(new SpacesItemDecoration(20));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvGoWorksOfArt.setHasFixedSize(true);
        rvGoWorksOfArt.setItemAnimator(new DefaultItemAnimator());

        mGoWorksOfArtAdapterBean = new ArrayList<>();
        mGoWorkOfArtAdapter = new GoWorkOfArtAdapter(mGoWorksOfArtAdapterBean);
        rvGoWorksOfArt.setAdapter(mGoWorkOfArtAdapter);

        // 艺术家介绍
        LinearLayoutManager layoutManager_1 = new LinearLayoutManager(App.getContext());
        layoutManager_1.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvGanchuangArtists.setLayoutManager(layoutManager_1);
        rvGanchuangArtists.addItemDecoration(new SpacesItemDecoration(20));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvGanchuangArtists.setHasFixedSize(true);
        rvGanchuangArtists.setItemAnimator(new DefaultItemAnimator());

        mDesignerIntroductionAdapterBean = new ArrayList<>();
        mDesignerIntroductionAdapter = new DesignerIntroductionAdapter(mDesignerIntroductionAdapterBean);
        rvGanchuangArtists.setAdapter(mDesignerIntroductionAdapter);

        // 图片集锦
        GridLayoutManager layoutManagerSelect = new GridLayoutManager(App.getContext(), 2);
        rvGanchuangGallery.setLayoutManager(layoutManagerSelect);
        rvGanchuangGallery.addItemDecoration(new GridSpacingItemDecoration(2, 50, false));
        rvGanchuangGallery.setHasFixedSize(true);
        rvGanchuangGallery.setItemAnimator(new DefaultItemAnimator());
        rvGanchuangGallery.setNestedScrollingEnabled(false);

        mArtSelectAdapterBean = new ArrayList<>();
        mArtKohlerSelectAdapter = new ArtKohlerSelectAdapter(mArtSelectAdapterBean);
        rvGanchuangGallery.setAdapter(mArtKohlerSelectAdapter);

        //站点
        GridLayoutManager layoutManagerActivity = new GridLayoutManager(App.getContext(), 2);
        rvArtKohlerAgenda.setLayoutManager(layoutManagerActivity);
        rvArtKohlerAgenda.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        rvArtKohlerAgenda.setHasFixedSize(true);
        rvArtKohlerAgenda.setItemAnimator(new DefaultItemAnimator());
        rvArtKohlerAgenda.setNestedScrollingEnabled(false);

        mArtKohlerAdapterBean = new ArrayList<>();
        mGanChuangAdapter = new GanChuangAdapter(mArtKohlerAdapterBean);
        rvArtKohlerAgenda.setAdapter(mGanChuangAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Map<String, Object> stringMap = IdeaApi.getSign();

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getGanChuang(stringMap)
                .compose(GanChuangActivity.this.<BasicResponse<ArtKohlerBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<ArtKohlerBean>>(GanChuangActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<ArtKohlerBean> response) {
                        mArtKohlerBean = response.getData();
                        tvGanChuangVideo.setText(mArtKohlerBean.getLive().getTitle());
                        mVideo = mArtKohlerBean.getLive().getH5Url();

                        tvGanchuangPositionZeroAgendaType.setText("当前站点");
                        tvGanchuangPositionZeroAgendaTime.setText(mArtKohlerBean.getAgendaList().get(0).getTimeSlot());
                        tvGanchuangPositionZeroAgendaPosition.setText(mArtKohlerBean.getAgendaList().get(0).getPlace());
                        tvGanchuangPositionZeroAgendaName.setText(mArtKohlerBean.getAgendaList().get(0).getTitle());

                        mArtKohlerAdapterBean.clear();
                        mArtKohlerAdapterBean.addAll(mArtKohlerBean.getAgendaList());
                        mArtKohlerAdapterBean.remove(0);

                        //站点
                        mGanChuangAdapter = new GanChuangAdapter(mArtKohlerAdapterBean);
                        rvArtKohlerAgenda.setAdapter(mGanChuangAdapter);
                        //艺术家介绍
                        mDesignerIntroductionAdapterBean.clear();
                        mDesignerIntroductionAdapterBean.addAll(response.getData().getDesigners());
                        mDesignerIntroductionAdapter = new DesignerIntroductionAdapter(mDesignerIntroductionAdapterBean);
                        rvGanchuangArtists.setAdapter(mDesignerIntroductionAdapter);
                        //展品介绍
                        mGoWorksOfArtAdapterBean.clear();
                        mGoWorksOfArtAdapterBean.addAll(response.getData().getArtworks());
                        mGoWorkOfArtAdapter = new GoWorkOfArtAdapter(mGoWorksOfArtAdapterBean);
                        rvGoWorksOfArt.setAdapter(mGoWorkOfArtAdapter);
                        mGoWorkOfArtAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                startActivity(new Intent(GanChuangActivity.this, WorksOfArtActivity.class).putExtra("WorksOfArtPosition", position));
                            }
                        });
                        //图片集锦
                        mArtSelectAdapterBean.clear();
                        mArtSelectAdapterBean.addAll(response.getData().getGallery());
                        mArtKohlerSelectAdapter = new ArtKohlerSelectAdapter(mArtSelectAdapterBean);
                        rvGanchuangGallery.setAdapter(mArtKohlerSelectAdapter);
                        mArtKohlerSelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                startActivity(new Intent(GanChuangActivity.this, ArtKohlerSelectActivity.class).putExtra("select_img", Integer.parseInt(mArtSelectAdapterBean.get(position).getParams())));
                            }
                        });
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        AIOAnalytics.onPageBegin("ganchuang");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        AIOAnalytics.onPageEnd("ganchuang");
    }

    @OnClick({R.id.rl_ganchuang_video, R.id.ll_go_works_of_art, R.id.ll_go_artists})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_ganchuang_video:
                startActivity(new Intent(this, WebViewActivity.class).putExtra("h5url", mVideo));
                break;
            case R.id.ll_go_works_of_art:
                startActivity(new Intent(this, WorksOfArtActivity.class));
                break;
            case R.id.ll_go_artists:
                startActivity(new Intent(this, GanChuangArtistActivity.class));
                break;
        }
    }
}
