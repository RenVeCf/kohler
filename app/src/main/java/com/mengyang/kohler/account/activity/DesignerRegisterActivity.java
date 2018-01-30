package com.mengyang.kohler.account.activity;

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
 * 设计师注册
 */

public class DesignerRegisterActivity extends BaseActivity {

    @BindView(R.id.iv_designer_register_go_home)
    ImageView ivDesignerRegisterGoHome;
    @BindView(R.id.et_designer_register_phone_num)
    EditText etDesignerRegisterPhoneNum;
    @BindView(R.id.et_designer_register_verification_code)
    EditText etDesignerRegisterVerificationCode;
    @BindView(R.id.tv_designer_register_verification_code)
    TextView tvDesignerRegisterVerificationCode;
    @BindView(R.id.et_designer_register_sms_verification_code)
    EditText etDesignerRegisterSmsVerificationCode;
    @BindView(R.id.bt_designer_register_send_out_sms)
    Button btDesignerRegisterSendOutSms;
    @BindView(R.id.et_designer_register_pwd)
    EditText etDesignerRegisterPwd;
    @BindView(R.id.bt_designer_register)
    Button btDesignerRegister;
    @BindView(R.id.tv_designer_register_go_login)
    TextView tvDesignerRegisterGoLogin;
    @BindView(R.id.tv_designer_register_go_user_register)
    TextView tvDesignerRegisterGoUserRegister;
    @BindView(R.id.tv_designer_register_go_distributor_register)
    TextView tvDesignerRegisterGoDistributorRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_designer_register;
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

    @OnClick({R.id.iv_designer_register_go_home, R.id.tv_designer_register_verification_code, R.id.bt_designer_register_send_out_sms, R.id.bt_designer_register, R.id.tv_designer_register_go_login, R.id.tv_designer_register_go_user_register, R.id.tv_designer_register_go_distributor_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_designer_register_go_home:
                break;
            case R.id.tv_designer_register_verification_code:
                break;
            case R.id.bt_designer_register_send_out_sms:
                break;
            case R.id.bt_designer_register:
                break;
            case R.id.tv_designer_register_go_login:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.tv_designer_register_go_user_register:
                startActivity(new Intent(this, UserRegisterActivity.class));
                finish();
                break;
            case R.id.tv_designer_register_go_distributor_register:
                startActivity(new Intent(this, DistributorRegisterActivity.class));
                finish();
                break;
        }
    }
}
