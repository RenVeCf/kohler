package com.mengyang.kohler.account.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.IConstants;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.main.activity.MainActivity;
import com.mengyang.kohler.module.BasicResponse;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        App.getManager().addActivity(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    private void UserRegister() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("mobileNo", etUserRegisterPhoneNum.getText().toString().trim());//手机号码
        stringMap.put("verifyCode", etUserRegisterVerificationCode.getText().toString().trim());//验证码
        stringMap.put("password", etUserRegisterPwd.getText().toString().trim());//用户密码

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getUserRegister(stringMap)
                .compose(this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        SPUtil.put(App.getContext(), IConstants.IS_LOGIN, true);
                        startActivity(new Intent(UserRegisterActivity.this, MainActivity.class));
                        finish();
                    }
                });
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
                if (!etUserRegisterPhoneNum.getText().toString().trim().equals("") && !etUserRegisterVerificationCode.getText().toString().trim().equals("") && !etUserRegisterPwd.getText().toString().trim().equals("")) {
                    UserRegister();
                } else {
                    ToastUtil.showToast(getString(R.string.msg_no_ok));
                }
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
