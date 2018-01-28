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
 * 经销商注册
 */

public class DistributorRegisterActivity extends BaseActivity {

    @BindView(R.id.iv_distributor_register_go_home)
    ImageView ivDistributorRegisterGoHome;
    @BindView(R.id.et_distributor_register_phone_num)
    EditText etDistributorRegisterPhoneNum;
    @BindView(R.id.et_distributor_register_verification_code)
    EditText etDistributorRegisterVerificationCode;
    @BindView(R.id.tv_distributor_register_verification_code)
    TextView tvDistributorRegisterVerificationCode;
    @BindView(R.id.et_distributor_register_sms_verification_code)
    EditText etDistributorRegisterSmsVerificationCode;
    @BindView(R.id.bt_distributor_register_send_out_sms)
    Button btDistributorRegisterSendOutSms;
    @BindView(R.id.et_distributor_register_pwd)
    EditText etDistributorRegisterPwd;
    @BindView(R.id.bt_distributor_register)
    Button btDistributorRegister;
    @BindView(R.id.tv_distributor_register_go_login)
    TextView tvDistributorRegisterGoLogin;
    @BindView(R.id.tv_distributor_register_go_user_register)
    TextView tvDistributorRegisterGoUserRegister;
    @BindView(R.id.tv_distributor_register_go_designer_register)
    TextView tvDistributorRegisterGoDesignerRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_distributor_register;
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

    @OnClick({R.id.iv_distributor_register_go_home, R.id.tv_distributor_register_verification_code, R.id.bt_distributor_register_send_out_sms, R.id.bt_distributor_register, R.id.tv_distributor_register_go_login, R.id.tv_distributor_register_go_user_register, R.id.tv_distributor_register_go_designer_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_distributor_register_go_home:
                break;
            case R.id.tv_distributor_register_verification_code:
                break;
            case R.id.bt_distributor_register_send_out_sms:
                break;
            case R.id.bt_distributor_register:
                break;
            case R.id.tv_distributor_register_go_login:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.tv_distributor_register_go_user_register:
                startActivity(new Intent(this, UserRegisterActivity.class));
                finish();
                break;
            case R.id.tv_distributor_register_go_designer_register:
                startActivity(new Intent(this, DesignerRegisterActivity.class));
                finish();
                break;
        }
    }
}
