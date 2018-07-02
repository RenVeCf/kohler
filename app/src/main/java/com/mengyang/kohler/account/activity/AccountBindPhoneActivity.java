package com.mengyang.kohler.account.activity;

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

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 修改绑定手机
 */

public class AccountBindPhoneActivity extends BaseActivity {
    // 这是一个外包小公司，在甲方公司里办公的...别被忽悠了
    // 这家老板人品不好，进来说好交社保，但实际上不给你交，工资能拖就拖(有次一拖就3个月)
    // 说好的有年终奖也是没有的别想了，过节没有任何东西发，自己带电脑也没有补贴金的~
    // 这公司管理混乱趁早走吧兄弟...

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
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvAccountBindPhoneTop);
    }

    @Override
    protected void initListener() {
        etModifyBindPhoneOldNum.addTextChangedListener(new TextWatcher() {
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
                if (etModifyBindPhoneOldNum.getText().toString().trim().length() == 11 && VerifyUtils.isMobileNumber(etModifyBindPhoneOldNum.getText().toString().trim())) {

                } else if (etModifyBindPhoneOldNum.getText().toString().trim().length() == 11){
                    ToastUtil.showToast("请输入正确的手机号码！");
                    etModifyBindPhoneOldNum.setText("");
                }
            }
        });
        etModifyBindPhoneNewNum.addTextChangedListener(new TextWatcher() {
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
                if (etModifyBindPhoneNewNum.getText().toString().trim().length() == 11 && VerifyUtils.isMobileNumber(etModifyBindPhoneNewNum.getText().toString().trim())) {

                } else {
                    ToastUtil.showToast("请输入正确的手机号码！");
                    etModifyBindPhoneNewNum.setText("");
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void ModifyBindPhone() {
        Map<String, Object> stringMap = IdeaApi.getSign();
        stringMap.put("mobileNo", etModifyBindPhoneOldNum.getText().toString().trim());//原手机号码
        stringMap.put("verifyCode", etModifyBindPhoneVerificationCode.getText().toString().trim());//换绑时的验证码
        stringMap.put("newMobileNo", etModifyBindPhoneNewNum.getText().toString().trim());//新手机号码
        stringMap.put("pwd", etModifyBindPhonePwd.getText().toString().trim());//密码


        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getModifyBindPhone(stringMap)
                .compose(AccountBindPhoneActivity.this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(AccountBindPhoneActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        finish();
                    }
                });
    }

    private void LoginSMS() {
        Map<String, Object> stringMap = IdeaApi.getSign();
        stringMap.put("mobileNo", etModifyBindPhoneNewNum.getText().toString().trim());//手机号码

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getLoginSMS(stringMap)
                .compose(AccountBindPhoneActivity.this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(AccountBindPhoneActivity.this, false) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                    }
                });
    }

    @OnClick({R.id.bt_modify_bind_phone_send_out_new_num, R.id.bt_modify_bind_phone_determine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_modify_bind_phone_send_out_new_num:
                if (!etModifyBindPhoneNewNum.getText().toString().trim().equals(""))
                    LoginSMS();
                else
                    ToastUtil.showToast(getString(R.string.msg_no_ok));
                break;
            case R.id.bt_modify_bind_phone_determine:
                if (!etModifyBindPhoneOldNum.getText().toString().trim().equals("") && !etModifyBindPhoneNewNum.getText().toString().trim().equals("") && !etModifyBindPhoneVerificationCode.getText().toString().trim().equals("") && !etModifyBindPhonePwd.getText().toString().trim().equals("") && etModifyBindPhoneOldNum.getText().toString().trim().length() == 11)
                    ModifyBindPhone();
                else
                    ToastUtil.showToast(getString(R.string.msg_no_ok));
                break;
        }
    }
}
