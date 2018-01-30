package com.mengyang.kohler.account.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPasswordOneActivity extends BaseActivity {

    @BindView(R.id.tv_forget_password_one_top)
    TopView tvForgetPasswordOneTop;
    @BindView(R.id.et_forget_password_one_phone_num)
    EditText etForgetPasswordOnePhoneNum;
    @BindView(R.id.bt_forget_password_one_send_phone_num)
    Button btForgetPasswordOneSendPhoneNum;
    @BindView(R.id.et_forget_password_one_verification_code)
    EditText etForgetPasswordOneVerificationCode;
    @BindView(R.id.bt_forget_password_one_next)
    Button btForgetPasswordOneNext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password_one;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvForgetPasswordOneTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.bt_forget_password_one_send_phone_num, R.id.bt_forget_password_one_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_forget_password_one_send_phone_num:
                break;
            case R.id.bt_forget_password_one_next:
                startActivity(new Intent(this, ForgetPasswordTwoActivity.class));
                break;
        }
    }
}
