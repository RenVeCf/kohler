package com.mengyang.kohler.home.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.allyes.analytics.AIOAnalytics;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.fragment.KbisARFragment;
import com.mengyang.kohler.home.fragment.KbisAgendaFragment;
import com.mengyang.kohler.home.fragment.KbisGuideMapFragment;
import com.mengyang.kohler.home.fragment.KbisInterviewFragment;
import com.mengyang.kohler.home.fragment.KbisPhotoFragment;
import com.mengyang.kohler.home.fragment.KbisProductFragment;
import com.mengyang.kohler.home.fragment.KbisVideoFragment;
import com.mengyang.kohler.home.view.NavitationFollowScrollLayoutIonic;
import com.mengyang.kohler.whole_category.adapter.ViewPagerAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 2018科勒上海厨卫展
 */

public class KbisActivity extends BaseActivity {

    @BindView(R.id.tv_kbis_top)
    TopView tvKbisTop;
    @BindView(R.id.nfsl_kbis)
    NavitationFollowScrollLayoutIonic nfslKbis;
    @BindView(R.id.vp_kbis)
    ViewPager vpKbis;
    private List<Fragment> fragments;
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
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvKbisTop);
        MobclickAgent.onEvent(KbisActivity.this, "shanghai_chu_wei_zhan");

        fragments = new ArrayList<>();
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        vpKbis.setAdapter(viewPagerAdapter);
        vpKbis.setOffscreenPageLimit(2);

        nfslKbis.setViewPager(KbisActivity.this, titles, vpKbis, unselectedcolor, setectedcolor, 24, true, 2.5f, 10f, 10f, 90);
        nfslKbis.setBgLine(KbisActivity.this, 1, R.color.black);
        nfslKbis.setNavLine(KbisActivity.this, 2, R.color.black);

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

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        vpKbis.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        AIOAnalytics.onPageBegin("shanghai_chu_wei_zhan");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        AIOAnalytics.onPageEnd("shanghai_chu_wei_zhan");
    }
}
