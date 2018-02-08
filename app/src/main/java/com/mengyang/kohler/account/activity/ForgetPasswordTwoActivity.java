package com.mengyang.kohler.account.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ForgetPasswordTwoActivity extends BaseActivity {

    @BindView(R.id.tv_forget_password_two_top)
    TopView tvForgetPasswordTwoTop;
    @BindView(R.id.et_forget_password_two_input_new_pwd)
    EditText etForgetPasswordTwoInputNewPwd;
    @BindView(R.id.et_forget_password_two_input_pwd_again)
    EditText etForgetPasswordTwoInputPwdAgain;
    @BindView(R.id.bt_distributor_register_two)
    Button btDistributorRegisterTwo;
    String mMobileNo;
    String mVerifyCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password_two;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvForgetPasswordTwoTop);
        mMobileNo = getIntent().getStringExtra("mobileNo");
        mVerifyCode = getIntent().getStringExtra("verifyCode");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    private void Next() {
//        SMSSDK.getInstance().checkSmsCodeAsyn(mMobileNo, mVerifyCode, new SmscheckListener() {
//            @Override
//            public void checkCodeSuccess(final String code) {
                Map<String, String> stringMap = IdeaApi.getSign();
                stringMap.put("mobileNo", mMobileNo); //访问凭证
                stringMap.put("verifyCode", mVerifyCode); //验证码
                stringMap.put("newPwd", etForgetPasswordTwoInputNewPwd.getText().toString().trim()); //新密码
                stringMap.put("newAgainPwd", etForgetPasswordTwoInputPwdAgain.getText().toString().trim()); //再次新密码

                IdeaApi.getRequestLogin(stringMap);
                IdeaApi.getApiService()
                        .getForgetPwd(stringMap)
                        .compose(ForgetPasswordTwoActivity.this.<BasicResponse>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultObserver<BasicResponse>(ForgetPasswordTwoActivity.this, true) {
                            @Override
                            public void onSuccess(BasicResponse response) {
                                startActivity(new Intent(ForgetPasswordTwoActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
            }

//            @Override
//            public void checkCodeFail(int errCode, final String errmsg) {
//                ToastUtil.showToast(errmsg);
//            }
//        });
//    }

    @OnClick(R.id.bt_distributor_register_two)
    public void onViewClicked() {
        if (!etForgetPasswordTwoInputNewPwd.getText().toString().trim().equals("") && !etForgetPasswordTwoInputPwdAgain.getText().toString().trim().equals(""))
            Next();
    }
}
