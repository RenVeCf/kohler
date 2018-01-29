package com.mengyang.kohler.main.activity;

import android.widget.Button;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPasswordTwoActivity extends BaseActivity {

    @BindView(R.id.tv_forget_password_two_top)
    TopView tvForgetPasswordTwoTop;
    @BindView(R.id.et_forget_password_two_input_new_pwd)
    EditText etForgetPasswordTwoInputNewPwd;
    @BindView(R.id.et_forget_password_two_input_pwd_again)
    EditText etForgetPasswordTwoInputPwdAgain;
    @BindView(R.id.bt_distributor_register_two)
    Button btDistributorRegisterTwo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password_two;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvForgetPasswordTwoTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.bt_distributor_register_two)
    public void onViewClicked() {
    }
}
