package com.mengyang.kohler.home.activity;

import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveRealTimeActivity extends BaseActivity {

    @BindView(R.id.tv_live_real_time_top)
    TopView tvLiveRealTimeTop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_real_time;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvLiveRealTimeTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
