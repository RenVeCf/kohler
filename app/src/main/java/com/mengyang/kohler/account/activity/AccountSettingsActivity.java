package com.mengyang.kohler.account.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.main.activity.MainActivity;
import com.mengyang.kohler.module.BasicResponse;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    MainActivity mMainActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_settings;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvAccountSettingsTop);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

    }

    private void UserGoOut() {
        Map<String, String> stringMap = IdeaApi.getSign();

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getUserGoOut(stringMap)
                .compose(this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        SPUtil.put(App.getContext(), IConstants.IS_LOGIN, false);
                        App.destoryActivity("MainActivity");
                        startActivity(new Intent(AccountSettingsActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }

    @OnClick({R.id.tv_account_settings_about, R.id.tv_account_settings_privacy_policy, R.id.tv_account_settings_modify_pwd, R.id.tv_account_settings_modify_bind_phone, R.id.bt_account_settings_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_account_settings_about:
                startActivity(new Intent(this, AccountAboutActivity.class));
                break;
            case R.id.tv_account_settings_privacy_policy:
                startActivity(new Intent(this, AccountPrivacyPolicyActivity.class));
                break;
            case R.id.tv_account_settings_modify_pwd:
                startActivity(new Intent(this, ModifyPwdActivity.class));
                break;
            case R.id.tv_account_settings_modify_bind_phone:
                startActivity(new Intent(this, AccountBindPhoneActivity.class));
                break;
            case R.id.bt_account_settings_sign_out:
                if ((boolean) SPUtil.get(this, IConstants.IS_LOGIN, false))
                    UserGoOut();
                else {
                    startActivity(new Intent(AccountSettingsActivity.this, LoginActivity.class));
                }
                break;
        }
    }
}
