package com.mengyang.kohler.home.activity;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;

/**
 * 经销商大会
 */

public class MeetingActivity extends BaseActivity {
    @BindView(R.id.tv_meeting_top)
    TopView tvMeetingTop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meeting;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvMeetingTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
