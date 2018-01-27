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
 * 用户注册
 */

public class UserRegisterActivity extends BaseActivity {

    @BindView(R.id.iv_user_register_go_home)
    ImageView ivUserRegisterGoHome;
    @BindView(R.id.et_user_register_phone_num)
    EditText etUserRegisterPhoneNum;
    @BindView(R.id.et_user_register_verification_code)
    EditText etUserRegisterVerificationCode;
    @BindView(R.id.tv_user_register_verification_code)
    TextView tvUserRegisterVerificationCode;
    @BindView(R.id.et_user_register_sms_verification_code)
    EditText etUserRegisterSmsVerificationCode;
    @BindView(R.id.bt_user_register_send_out_sms)
    Button btUserRegisterSendOutSms;
    @BindView(R.id.et_user_register_pwd)
    EditText etUserRegisterPwd;
    @BindView(R.id.bt_user_register)
    Button btUserRegister;
    @BindView(R.id.tv_user_register_go_login)
    TextView tvUserRegisterGoLogin;
    @BindView(R.id.tv_user_register_go_designer_register)
    TextView tvUserRegisterGoDesignerRegister;
    @BindView(R.id.tv_user_register_go_distributor_register)
    TextView tvUserRegisterGoDistributorRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_register;
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

    @OnClick({R.id.iv_user_register_go_home, R.id.tv_user_register_verification_code, R.id.bt_user_register_send_out_sms, R.id.bt_user_register, R.id.tv_user_register_go_login, R.id.tv_user_register_go_designer_register, R.id.tv_user_register_go_distributor_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_register_go_home:
                break;
            case R.id.tv_user_register_verification_code:
                break;
            case R.id.bt_user_register_send_out_sms:
                break;
            case R.id.bt_user_register:
                break;
            case R.id.tv_user_register_go_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.tv_user_register_go_designer_register:
                startActivity(new Intent(this, DesignerRegisterActivity.class));
                break;
            case R.id.tv_user_register_go_distributor_register:
                startActivity(new Intent(this, DistributorRegisterActivity.class));
                break;
        }
    }
}
