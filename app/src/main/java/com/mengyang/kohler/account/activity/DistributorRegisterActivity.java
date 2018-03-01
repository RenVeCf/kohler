package com.mengyang.kohler.account.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.DateUtils;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.utils.VerifyUtils;
import com.mengyang.kohler.main.activity.MainActivity;
import com.mengyang.kohler.module.BasicResponse;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 经销商注册
 */

public class DistributorRegisterActivity extends BaseActivity {

    @BindView(R.id.rl_distributor_register_go_home)
    RelativeLayout ivDistributorRegisterGoHome;
    @BindView(R.id.et_distributor_register_phone_num)
    EditText etDistributorRegisterPhoneNum;
    @BindView(R.id.et_distributor_register_distributor_code)
    EditText etDistributorRegisterDistributorCode;
    @BindView(R.id.et_distributor_register_login_pwd)
    EditText etDistributorRegisterLoginPwd;
    @BindView(R.id.bt_distributor_register)
    Button btDistributorRegister;
    @BindView(R.id.tv_distributor_register_go_user_register)
    TextView tvDistributorRegisterGoUserRegister;
    @BindView(R.id.tv_distributor_register_go_designer_register)
    TextView tvDistributorRegisterGoDesignerRegister;
    @BindView(R.id.et_distributor_register_phone_num_again)
    EditText etDistributorRegisterPhoneNumAgain;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_distributor_register;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    protected void initListener() {
        etDistributorRegisterPhoneNum.addTextChangedListener(new TextWatcher() {
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
                if (etDistributorRegisterPhoneNum.getText().toString().trim().length() == 11 && VerifyUtils.isMobileNumber(etDistributorRegisterPhoneNum.getText().toString().trim())) {

                } else if (etDistributorRegisterPhoneNum.getText().toString().trim().length() == 11){
                    ToastUtil.showToast("请输入正确的手机号码！");
                    etDistributorRegisterPhoneNum.setText("");
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void ModifyBindPhone() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("mobileNo", etDistributorRegisterPhoneNum.getText().toString().trim());//手机号码
        stringMap.put("mobileNoAgain", etDistributorRegisterPhoneNumAgain.getText().toString().trim());//重新输入手机号码
        stringMap.put("inviteCode", etDistributorRegisterDistributorCode.getText().toString().trim());//专用码
        stringMap.put("password", etDistributorRegisterLoginPwd.getText().toString().trim());//用户密码
        stringMap.put("type", "dealer");//用户类型
        stringMap.put("time", DateUtils.dataOne(DateUtils.getCurrentTime_Today()));//时间戳

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getUserRegister(stringMap)
                .compose(DistributorRegisterActivity.this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(DistributorRegisterActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        startActivity(new Intent(DistributorRegisterActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }

    @OnClick({R.id.rl_distributor_register_go_home, R.id.bt_distributor_register, R.id.tv_distributor_register_go_user_register, R.id.tv_distributor_register_go_designer_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_distributor_register_go_home:
                hideInput();

                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.bt_distributor_register:
                String phoneNum = etDistributorRegisterPhoneNum.getText().toString().trim();
                String phoneNumAgain = etDistributorRegisterPhoneNumAgain.getText().toString().trim();
                String distributorCode = etDistributorRegisterDistributorCode.getText().toString().trim();
                String loginPwd = etDistributorRegisterLoginPwd.getText().toString().trim();

                if (checkPwd(loginPwd)) {
                    ToastUtil.showToast("密码格式不正确");
                    return;
                }


                if (!phoneNum.equals("") && !phoneNumAgain.equals("") && !distributorCode.equals("") && !loginPwd.equals("") && phoneNum.length() == 11) {
                    if (etDistributorRegisterPhoneNum.getText().toString().trim().equals(etDistributorRegisterPhoneNumAgain.getText().toString().trim()))
                        ModifyBindPhone();
                    else
                        ToastUtil.showToast(getString(R.string.phone_num_agreement));
                } else {
                    ToastUtil.showToast(getString(R.string.msg_no_ok));
                }
                break;
            case R.id.tv_distributor_register_go_user_register:
                startActivity(new Intent(this, UserRegisterActivity.class));
                break;
            case R.id.tv_distributor_register_go_designer_register:
                startActivity(new Intent(this, DesignerRegisterActivity.class));
                break;
            default:
                break;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
