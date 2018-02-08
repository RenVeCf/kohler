package com.mengyang.kohler.account.activity;

import android.content.Intent;
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

    }

    @Override
    protected void initData() {

    }

    private void SendSMS() {
        btForgetPasswordOneSendPhoneNum.setClickable(false);
        //开始倒计时
        startTimer();
        SMSSDK.getInstance().getSmsCodeAsyn(etForgetPasswordOnePhoneNum.getText().toString().trim(), 1 + "", new SmscodeListener() {
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

    private void startTimer() {
        timess = (int) (SMSSDK.getInstance().getIntervalTime() / 1000);
        btForgetPasswordOneSendPhoneNum.setText(timess + "s");
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
                            btForgetPasswordOneSendPhoneNum.setText(timess + "s");
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
        btForgetPasswordOneSendPhoneNum.setText("重新获取");
        btForgetPasswordOneSendPhoneNum.setClickable(true);
    }

    @OnClick({R.id.bt_forget_password_one_send_phone_num, R.id.bt_forget_password_one_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_forget_password_one_send_phone_num:
                if (!etForgetPasswordOnePhoneNum.getText().toString().trim().equals(""))
//                    SendSMS();
                break;
            case R.id.bt_forget_password_one_next:
                if (!etForgetPasswordOnePhoneNum.getText().toString().trim().equals("") && !etForgetPasswordOneVerificationCode.getText().toString().trim().equals("")) {
                    startActivity(new Intent(ForgetPasswordOneActivity.this, ForgetPasswordTwoActivity.class).putExtra("mobileNo", etForgetPasswordOnePhoneNum.getText().toString().trim()).putExtra("verifyCode", etForgetPasswordOneVerificationCode.getText().toString().trim()));
                    finish();
                }
                break;
        }
    }
}
