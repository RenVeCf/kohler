package com.mengyang.kohler.account.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.main.activity.LoginActivity;
import com.mengyang.kohler.main.activity.ModifyPwdActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的账户——设置
 */

public class AccountSettingsActivity extends BaseActivity {

    @BindView(R.id.tv_account_settings_top)
    TopView tvAccountSettingsTop;
    @BindView(R.id.tv_account_settings_about)
    TextView tvAccountSettingsAbout;
    @BindView(R.id.tv_account_settings_privacy_policy)
    TextView tvAccountSettingsPrivacyPolicy;
    @BindView(R.id.tv_account_settings_modify_pwd)
    TextView tvAccountSettingsModifyPwd;
    @BindView(R.id.tv_account_settings_modify_bind_phone)
    TextView tvAccountSettingsModifyBindPhone;
    @BindView(R.id.bt_account_settings_sign_out)
    Button btAccountSettingsSignOut;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_settings;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvAccountSettingsTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_account_settings_about, R.id.tv_account_settings_privacy_policy, R.id.tv_account_settings_modify_pwd, R.id.tv_account_settings_modify_bind_phone, R.id.bt_account_settings_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_account_settings_about:
                break;
            case R.id.tv_account_settings_privacy_policy:
                break;
            case R.id.tv_account_settings_modify_pwd:
                startActivity(new Intent(this, ModifyPwdActivity.class));
                break;
            case R.id.tv_account_settings_modify_bind_phone:
                startActivity(new Intent(this, AccountBindPhoneActivity.class));
                break;
            case R.id.bt_account_settings_sign_out:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
