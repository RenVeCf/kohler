package com.mengyang.kohler.account.activity;

import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountAboutActivity extends BaseActivity {

    @BindView(R.id.tv_account_about_top)
    TopView tvAccountAboutTop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_about;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvAccountAboutTop);
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
