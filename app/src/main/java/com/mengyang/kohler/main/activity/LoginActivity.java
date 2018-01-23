package com.mengyang.kohler.main.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.ev_login_phone_num)
    EditText evLoginPhoneNum;
    @BindView(R.id.ev_login_pwd)
    EditText evLoginPwd;
    @BindView(R.id.tv_login_forget_pwd)
    TextView tvLoginForgetPwd;
    @BindView(R.id.bt_login)
    Button btLogin;

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

    @OnClick({R.id.tv_login_forget_pwd, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_forget_pwd:
                break;
            case R.id.bt_login:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}