package com.mengyang.kohler.account.activity;

import android.content.Intent;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.activity.KbisActivity;

import butterknife.BindView;

public class AccountAboutActivity extends BaseActivity {

    @BindView(R.id.tv_account_about_top)
    TopView tvAccountAboutTop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_about;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvAccountAboutTop);
    }

    @Override
    protected void initListener() {
        startActivity(new Intent(this, KbisActivity.class));
    }

    @Override
    protected void initData() {

    }
}
