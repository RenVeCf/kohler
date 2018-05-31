package com.mengyang.kohler.account.activity;

import android.content.Intent;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.activity.AzureCustomerServiceActivity;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;

/**
 * 隐私政策
 */

public class AccountPrivacyPolicyActivity extends BaseActivity {
    @BindView(R.id.tv_account_privacy_policy_top)
    TopView tvAccountPrivacyPolicyTop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_privacy_policy;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvAccountPrivacyPolicyTop);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

    }
}
