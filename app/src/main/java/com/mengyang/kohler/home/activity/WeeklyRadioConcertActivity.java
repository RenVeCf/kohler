package com.mengyang.kohler.home.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.WeeklyRadioConcertAdapter;

import butterknife.BindView;

public class WeeklyRadioConcertActivity extends BaseActivity {

    @BindView(R.id.tv_home_weekly_radio_concert)
    TopView tvHomeWeeklyRadioConcert;
    @BindView(R.id.rv_weekly_radio_concert)
    RecyclerView rvWeeklyRadioConcert;
    private WeeklyRadioConcertAdapter mWeeklyRadioConcertAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weekly_radio_concert;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvHomeWeeklyRadioConcert);

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvWeeklyRadioConcert.setLayoutManager(layoutManager);
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvWeeklyRadioConcert.setHasFixedSize(true);
        rvWeeklyRadioConcert.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
