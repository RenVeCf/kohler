package com.mengyang.kohler.main.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity {

    @BindView(R.id.tv_forget_password_top)
    TopView tvForgetPasswordTop;
    @BindView(R.id.et_forget_password_input_new_pwd)
    EditText etForgetPasswordInputNewPwd;
    @BindView(R.id.et_forget_password_input_pwd_again)
    EditText etForgetPasswordInputPwdAgain;
    @BindView(R.id.bt_distributor_register)
    Button btDistributorRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvForgetPasswordTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.bt_distributor_register)
    public void onViewClicked() {
    }
}
