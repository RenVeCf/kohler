package com.mengyang.kohler.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DistributorVerificationCodeActivity extends BaseActivity {

    @BindView(R.id.iv_designer_verification_code_go_home)
    ImageView ivDesignerVerificationCodeGoHome;
    @BindView(R.id.et_designer_verification_code)
    EditText etDesignerVerificationCode;
    @BindView(R.id.bt_designer_register_2)
    Button btDesignerRegister2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_distributor_verification_code;
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

    @OnClick({R.id.iv_designer_verification_code_go_home, R.id.bt_designer_register_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_designer_verification_code_go_home:
                break;
            case R.id.bt_designer_register_2:
                break;
        }
    }
}
