package com.mengyang.kohler.home.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.LiveRealTimeAdapter;

import butterknife.BindView;

/**
 * 现场实时投票
 */

public class LiveRealTimeActivity extends BaseActivity {

    @BindView(R.id.tv_live_real_time_top)
    TopView tvLiveRealTimeTop;
    @BindView(R.id.tv_live_real_time_prize)
    TextView tvLiveRealTimePrize;
    @BindView(R.id.rv_live_real_time)
    RecyclerView rvLiveRealTime;
    private LiveRealTimeAdapter mLiveRealTimeAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_real_time;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvLiveRealTimeTop);
        GridLayoutManager layoutManagerActivity = new GridLayoutManager(App.getContext(), 2);
        rvLiveRealTime.setLayoutManager(layoutManagerActivity);
        rvLiveRealTime.addItemDecoration(new GridSpacingItemDecoration(2, 15, false));
        rvLiveRealTime.setNestedScrollingEnabled(false);
        rvLiveRealTime.setHasFixedSize(true);
        rvLiveRealTime.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}