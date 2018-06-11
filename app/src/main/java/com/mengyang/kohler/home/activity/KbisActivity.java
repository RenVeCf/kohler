package com.mengyang.kohler.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.allyes.analytics.AIOAnalytics;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.fragment.KbisARFragment;
import com.mengyang.kohler.home.fragment.KbisAgendaFragment;
import com.mengyang.kohler.home.fragment.KbisGuideMapFragment;
import com.mengyang.kohler.home.fragment.KbisInterviewFragment;
import com.mengyang.kohler.home.fragment.KbisPhotoFragment;
import com.mengyang.kohler.home.fragment.KbisProductFragment;
import com.mengyang.kohler.home.fragment.KbisVideoFragment;
import com.mengyang.kohler.home.view.MyViewPager;
import com.mengyang.kohler.home.view.NavitationFollowScrollLayoutIonic;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.KbisBean;
import com.mengyang.kohler.whole_category.adapter.ViewPagerAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 2018科勒上海厨卫展
 */

public class KbisActivity extends BaseActivity implements KbisARFragment.OnActivityPagerView {

    @BindView(R.id.tv_kbis_top)
    TopView tvKbisTop;
    @BindView(R.id.iv_top_back)
    ImageView ivTopBack;
    @BindView(R.id.nfsl_kbis)
    NavitationFollowScrollLayoutIonic nfslKbis;
    @BindView(R.id.vp_kbis)
    MyViewPager vpKbis;
    private List<Fragment> fragments = new ArrayList<>();
    private ViewPagerAdapter viewPagerAdapter;
    private int[] titles = {R.mipmap.trade_show_guide_map, R.mipmap.trade_show_ar, R.mipmap.trade_show_agenda, R.mipmap.trade_show_video, R.mipmap.trade_show_photo, R.mipmap.trade_show_product, R.mipmap.trade_show_interview};
    private int[] unselectedcolor = {R.mipmap.trade_show_guide_map, R.mipmap.trade_show_ar, R.mipmap.trade_show_agenda, R.mipmap.trade_show_video, R.mipmap.trade_show_photo, R.mipmap.trade_show_product, R.mipmap.trade_show_interview};
    private int[] setectedcolor = {R.mipmap.trade_show_guide_map_down, R.mipmap.trade_show_ar_down, R.mipmap.trade_show_agenda_down, R.mipmap.trade_show_video_down, R.mipmap.trade_show_photo_down, R.mipmap.trade_show_product_down, R.mipmap.trade_show_interview_down};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_kbis;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //沉浸式状态栏初始化白色
        ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(false).init();
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvKbisTop);
        MobclickAgent.onEvent(KbisActivity.this, "chuweizhan");
        ivTopBack.setImageResource(R.mipmap.fanhuibai);
        KbisARFragment.setOnActivityPagerView(this);
    }

    private void showFragment(KbisBean data) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        vpKbis.setAdapter(viewPagerAdapter);
        vpKbis.setOffscreenPageLimit(2);

        nfslKbis.setViewPager(KbisActivity.this, titles, vpKbis, unselectedcolor, setectedcolor, 24, true, 2.5f, 10f, 10f, 90);
        nfslKbis.setBgLine(KbisActivity.this, 1);
        nfslKbis.setNavLine(KbisActivity.this, 2);

        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);

        //导览图
        KbisGuideMapFragment kbisGuideMapFragment = new KbisGuideMapFragment();
        fragments.add(kbisGuideMapFragment);
        //AR
        KbisARFragment kbisARFragment = new KbisARFragment();
        fragments.add(kbisARFragment);
        //议程
        KbisAgendaFragment kbisAgendaFragment = new KbisAgendaFragment();
        fragments.add(kbisAgendaFragment);
        //视频
        KbisVideoFragment kbisVideoFragment = new KbisVideoFragment();
        fragments.add(kbisVideoFragment);
        //相册
        KbisPhotoFragment kbisPhotoFragment = new KbisPhotoFragment();
        fragments.add(kbisPhotoFragment);
        //产品手册
        KbisProductFragment kbisProductFragment = new KbisProductFragment();
        fragments.add(kbisProductFragment);
        //文稿
        KbisInterviewFragment kbisInterviewFragment = new KbisInterviewFragment();
        fragments.add(kbisInterviewFragment);

        for (int i = 0; i < fragments.size(); i++) {
            fragments.get(i).setArguments(bundle);
        }

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        vpKbis.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        IdeaApi.getApiService()
                .getKbis()
                .compose(this.<BasicResponse<KbisBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<KbisBean>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<KbisBean> response) {
                        KbisBean data = response.getData();
                        showFragment(data);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        AIOAnalytics.onPageBegin("chuweizhan");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        AIOAnalytics.onPageEnd("chuweizhan");
    }

    @Override
    public void onActivityPagerView() {
        vpKbis.setCurrentItem(0);
    }
}
