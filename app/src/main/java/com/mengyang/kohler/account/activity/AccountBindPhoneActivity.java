package com.mengyang.kohler.account.activity;

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
import com.mengyang.kohler.common.view.TopView;
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
 * 修改绑定手机
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

    private TimerTask timerTask;
    private Timer timer;
    private int timess;

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

    }

    @Override
    protected void initData() {

    }

    private void SendSMS() {
        btModifyBindPhoneSendOutNewNum.setClickable(false);
        //开始倒计时
        startTimer();
        SMSSDK.getInstance().getSmsCodeAsyn(etModifyBindPhoneNewNum.getText().toString().trim(), 1 + "", new SmscodeListener() {
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
//        SMSSDK.getInstance().checkSmsCodeAsyn(etModifyBindPhoneNewNum.getText().toString().trim(), etModifyBindPhoneVerificationCode.getText().toString().trim(), new SmscheckListener() {
//            @Override
//            public void checkCodeSuccess(final String code) {
//                //有问题
                Map<String, String> stringMap = IdeaApi.getSign();
                stringMap.put("mobileNo", etModifyBindPhoneOldNum.getText().toString().trim());//原手机号码
                stringMap.put("verifyCode", etModifyBindPhoneVerificationCode.getText().toString().trim());//换绑时的验证码
                stringMap.put("newMobileNo", etModifyBindPhoneNewNum.getText().toString().trim());//新手机号码
                stringMap.put("newVerifyCode", etModifyBindPhonePwd.getText().toString().trim());//密码

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
//
//            @Override
//            public void checkCodeFail(int errCode, final String errmsg) {
//                ToastUtil.showToast(errmsg);
//            }
//        });
//    }

    private void startTimer() {
        timess = (int) (SMSSDK.getInstance().getIntervalTime() / 1000);
        btModifyBindPhoneSendOutNewNum.setText(timess + "s");
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
                            btModifyBindPhoneSendOutNewNum.setText(timess + "s");
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
        btModifyBindPhoneSendOutNewNum.setText("重新获取");
        btModifyBindPhoneSendOutNewNum.setClickable(true);
    }

    @OnClick({R.id.bt_modify_bind_phone_send_out_new_num, R.id.bt_modify_bind_phone_determine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_modify_bind_phone_send_out_new_num:
                if (!etModifyBindPhoneNewNum.getText().toString().trim().equals(""))
//                    SendSMS();
                break;
            case R.id.bt_modify_bind_phone_determine:
                if (!etModifyBindPhoneOldNum.getText().toString().trim().equals("") && !etModifyBindPhoneNewNum.getText().toString().trim().equals("") && !etModifyBindPhoneVerificationCode.getText().toString().trim().equals("") && !etModifyBindPhonePwd.getText().toString().trim().equals(""))
                    ModifyBindPhone();
                break;
        }
    }
}
