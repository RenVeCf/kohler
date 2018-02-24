package com.mengyang.kohler.account.activity;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.DateUtils;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.main.activity.MainActivity;
import com.mengyang.kohler.module.BasicResponse;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import cn.jpush.sms.listener.SmscodeListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 经销商注册
 */

public class DistributorRegisterActivity extends BaseActivity {

    @BindView(R.id.iv_distributor_register_go_home)
    ImageView ivDistributorRegisterGoHome;
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
    @BindView(R.id.et_distributor_register_sms_verification_code)
    EditText etDistributorRegisterSmsVerificationCode;
    @BindView(R.id.bt_distributor_register_send_out_sms)
    Button btDistributorRegisterSendOutSms;
    private TimerTask timerTask;
    private Timer timer;
    private int timess;

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

    }

    @Override
    protected void initData() {

    }

    //发送短信验证码
    private void SendSMS() {
        btDistributorRegister.setClickable(false);
        //开始倒计时
        startTimer();
        SMSSDK.getInstance().getSmsCodeAsyn(etDistributorRegisterPhoneNum.getText().toString().trim(), 1 + "", new SmscodeListener() {
            @Override
            public void getCodeSuccess(final String uuid) {
                ToastUtil.showToast(uuid);
            }

            @Override
            public void getCodeFail(int errCode, final String errmsg) {
                //失败后停止计时
                stopTimer();
                ToastUtil.showToast(errmsg);
            }
        });
    }

    private void ModifyBindPhone() {
//        SMSSDK.getInstance().checkSmsCodeAsyn(etDistributorRegisterPhoneNum.getText().toString().trim(), etDistributorRegisterSmsVerificationCode.getText().toString().trim(), new SmscheckListener() {
//            @Override
//            public void checkCodeSuccess(final String code) {
                Map<String, String> stringMap = IdeaApi.getSign();
                stringMap.put("mobileNo", etDistributorRegisterPhoneNum.getText().toString().trim());//手机号码
                stringMap.put("verifyCode", etDistributorRegisterSmsVerificationCode.getText().toString().trim());//短信验证码
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
//
//            @Override
//            public void checkCodeFail(int errCode, final String errmsg) {
//                ToastUtil.showToast(errmsg);
//            }
//        });
//    }

    private void startTimer() {
        timess = (int) (SMSSDK.getInstance().getIntervalTime() / 1000);
        btDistributorRegister.setText(timess + "s");
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timess--;
                            if (timess <= 0) {
                                stopTimer();
                                return;
                            }
                            btDistributorRegister.setText(timess + "s");
                        }
                    });
                }
            };
        }
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(timerTask, 1000, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        btDistributorRegister.setText("重新获取");
        btDistributorRegister.setClickable(true);
    }

    @OnClick({R.id.iv_distributor_register_go_home, R.id.bt_distributor_register, R.id.tv_distributor_register_go_user_register, R.id.tv_distributor_register_go_designer_register, R.id.bt_distributor_register_send_out_sms})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_distributor_register_go_home:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.bt_distributor_register:
                if (!etDistributorRegisterPhoneNum.getText().toString().trim().equals("") && !etDistributorRegisterSmsVerificationCode.getText().toString().trim().equals("") && !etDistributorRegisterDistributorCode.getText().toString().trim().equals("") && !etDistributorRegisterLoginPwd.getText().toString().trim().equals("")) {
                    ModifyBindPhone();
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
            case R.id.bt_distributor_register_send_out_sms:
                if (!etDistributorRegisterPhoneNum.getText().toString().trim().equals(""))
//                    SendSMS();
                break;
        }
    }
}
