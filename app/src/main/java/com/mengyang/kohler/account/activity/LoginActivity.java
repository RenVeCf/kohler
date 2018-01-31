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
import com.mengyang.kohler.module.bean.LoginBean;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 登陆
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.iv_lonin_go_home)
    ImageView ivLoninGoHome;
    @BindView(R.id.et_login_phone_num)
    EditText etLoginPhoneNum;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.et_login_verification_code)
    EditText etLoginVerificationCode;
    @BindView(R.id.tv_login_verification_code)
    TextView tvLoginVerificationCode;
    @BindView(R.id.tv_login_forget_pwd)
    TextView tvLoginForgetPwd;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_login_go_register)
    TextView tvLoginGoRegister;

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

    private void Login() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("mobileNo", etLoginPhoneNum.getText().toString().trim());//手机号码
        stringMap.put("password", etLoginPwd.getText().toString().trim());//用户密码

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getLogin(stringMap)
                .compose(this.<BasicResponse<LoginBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<LoginBean>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<LoginBean> response) {
                        SPUtil.put(App.getContext(), IConstants.IS_LOGIN, true);
                        SPUtil.put(App.getContext(), IConstants.TOKEN, response.getData().getAccessToken());
                        SPUtil.put(App.getContext(), IConstants.REFRESH_TOKEN, response.getData().getRefreshToken());
                        SPUtil.put(App.getContext(), IConstants.POEN_ID, response.getData().getOpenId());
                        SPUtil.put(App.getContext(), IConstants.USER_ID, response.getData().getOpenId());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }

    @OnClick({R.id.iv_lonin_go_home, R.id.tv_login_forget_pwd, R.id.bt_login, R.id.tv_login_go_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_lonin_go_home:
                break;
            case R.id.tv_login_forget_pwd:
                startActivity(new Intent(this, ForgetPasswordTwoActivity.class));
                finish();
                break;
            case R.id.bt_login:
                if (!etLoginPhoneNum.getText().toString().trim().equals("") && !etLoginPwd.getText().toString().trim().equals("") && !etLoginVerificationCode.getText().toString().trim().equals("")) {
                    Login();
                } else {
                    ToastUtil.showToast(getString(R.string.msg_no_ok));
                }
                break;
            case R.id.tv_login_go_register:
                startActivity(new Intent(this, UserRegisterActivity.class));
                finish();
                break;
        }
    }
}