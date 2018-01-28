package com.mengyang.kohler.main.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登陆
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.iv_lonin_go_home)
    ImageView ivLoninGoHome;
    @BindView(R.id.et_login_phone_num)
    EditText etLoginPhoneNum;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.et_login_verification_code)
    EditText etLoginVerificationCode;
    @BindView(R.id.tv_login_verification_code)
    TextView tvLoginVerificationCode;
    @BindView(R.id.tv_login_forget_pwd)
    TextView tvLoginForgetPwd;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_login_go_register)
    TextView tvLoginGoRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initValues() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_lonin_go_home, R.id.tv_login_forget_pwd, R.id.bt_login, R.id.tv_login_go_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_lonin_go_home:
                break;
            case R.id.tv_login_forget_pwd:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                finish();
                break;
            case R.id.bt_login:
                break;
            case R.id.tv_login_go_register:
                startActivity(new Intent(this, UserRegisterActivity.class));
                finish();
                break;
        }
    }
}