package com.mengyang.kohler.account.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 绑定手机
 */

public class AccountBindPhoneActivity extends BaseActivity {

    @BindView(R.id.tv_account_bind_phone_top)
    TopView tvAccountBindPhoneTop;
    @BindView(R.id.et_modify_bind_phone_old_num)
    EditText etModifyBindPhoneOldNum;
    @BindView(R.id.et_modify_bind_phone_new_num)
    EditText etModifyBindPhoneNewNum;
    @BindView(R.id.bt_modify_bind_phone_send_out_new_num)
    Button btModifyBindPhoneSendOutNewNum;
    @BindView(R.id.et_modify_bind_phone_verification_code)
    EditText etModifyBindPhoneVerificationCode;
    @BindView(R.id.et_modify_bind_phone_pwd)
    EditText etModifyBindPhonePwd;
    @BindView(R.id.bt_modify_bind_phone_determine)
    Button btModifyBindPhoneDetermine;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_bind_phone;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvAccountBindPhoneTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.bt_modify_bind_phone_send_out_new_num, R.id.bt_modify_bind_phone_determine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_modify_bind_phone_send_out_new_num:
                break;
            case R.id.bt_modify_bind_phone_determine:
                break;
        }
    }
}
