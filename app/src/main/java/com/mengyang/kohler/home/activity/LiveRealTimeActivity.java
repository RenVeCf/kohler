package com.mengyang.kohler.home.activity;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;

/**
 * 现场实时投票
 */

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
}