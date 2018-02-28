package com.mengyang.kohler.account.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.utils.VerifyUtils;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    private TimerTask timerTask;
    private Timer timer;
    private int timess;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password_one;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvForgetPasswordOneTop);
    }

    @Override
    protected void initListener() {
        etForgetPasswordOnePhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //输入文本之前的状态
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //输入文字中的状态，count是一次性输入字符数
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //输入文字后的状态
                if (etForgetPasswordOnePhoneNum.getText().toString().trim().length() == 11 && VerifyUtils.isMobileNumber(etForgetPasswordOnePhoneNum.getText().toString().trim())) {

                } else {
                    ToastUtil.showToast("请输入正确的手机号码！");
                    etForgetPasswordOnePhoneNum.setText("");
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void LoginSMS() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("mobileNo", etForgetPasswordOnePhoneNum.getText().toString().trim());//手机号码

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getLoginSMS(stringMap)
                .compose(ForgetPasswordOneActivity.this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(ForgetPasswordOneActivity.this, false) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                    }
                });
    }

    @OnClick({R.id.bt_forget_password_one_send_phone_num, R.id.bt_forget_password_one_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_forget_password_one_send_phone_num:
                if (!etForgetPasswordOnePhoneNum.getText().toString().trim().equals(""))
                    LoginSMS();
                else
                    ToastUtil.showToast(getString(R.string.msg_no_ok));
                break;
            case R.id.bt_forget_password_one_next:
                if (!etForgetPasswordOnePhoneNum.getText().toString().trim().equals("") && !etForgetPasswordOneVerificationCode.getText().toString().trim().equals("")) {
                    startActivity(new Intent(ForgetPasswordOneActivity.this, ForgetPasswordTwoActivity.class).putExtra("mobileNo", etForgetPasswordOnePhoneNum.getText().toString().trim() + "").putExtra("verifyCode", etForgetPasswordOneVerificationCode.getText().toString().trim() + ""));
                    finish();
                }
                break;
        }
    }
}
