package com.mengyang.kohler.account.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.mengyang.kohler.common.net.IdeaApiService;
import com.mengyang.kohler.common.net.Config;
import com.mengyang.kohler.common.utils.DateUtils;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.main.activity.MainActivity;
import com.mengyang.kohler.module.BasicResponse;

import java.io.IOException;
import java.util.Iterator;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    @BindView(R.id.iv_user_register_verification_code)
    ImageView ivUserRegisterVerificationCode;
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
    private TimerTask timerTask;
    private Timer timer;
    private int timess;
    private byte[] bytes; //图片验证码进制流
    private String time;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_register;
    }

    @Override
    protected void initValues() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        App.getManager().addActivity(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Map<String, String> stringMap = IdeaApi.getSign();
        time = DateUtils.dataOne(DateUtils.getCurrentTime_Today());
        stringMap.put("time", time);//时间戳
        postAsynHttp(stringMap);
    }

    /**
     * 获取验证码图片
     *
     * @param map
     */
    private void postAsynHttp(Map map) {
        Iterator entries = map.entrySet().iterator();
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = entry.getKey() + "";
            String value = entry.getValue() + "";
            builder.add(key, value);
        }
        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(IdeaApiService.API_SERVER_URL + Config.LOGIN_VERIFICATION_IMG)
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                bytes = response.body().bytes();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        ivUserRegisterVerificationCode.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    //发送短信验证码
    private void SendSMS() {
        btUserRegisterSendOutSms.setClickable(false);
        //开始倒计时
        startTimer();
        SMSSDK.getInstance().getSmsCodeAsyn(etUserRegisterPhoneNum.getText().toString().trim(), 1 + "", new SmscodeListener() {
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
        //        SMSSDK.getInstance().checkSmsCodeAsyn(etUserRegisterPhoneNum.getText().toString().trim(), etUserRegisterSmsVerificationCode.getText().toString().trim(), new SmscheckListener() {
        //            @Override
        //            public void checkCodeSuccess(final String code) {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("mobileNo", etUserRegisterPhoneNum.getText().toString().trim());//手机号码
        stringMap.put("code", etUserRegisterVerificationCode.getText().toString().trim());//图片验证码
        stringMap.put("password", etUserRegisterPwd.getText().toString().trim());//用户密码
        stringMap.put("type", "commonUser");//用户类型化
        stringMap.put("time", time);//时间戳
        stringMap.put("verifyCode", etUserRegisterPwd.getText().toString().trim());//验证码

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getUserRegister(stringMap)
                .compose(UserRegisterActivity.this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(UserRegisterActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        startActivity(new Intent(UserRegisterActivity.this, LoginActivity.class));
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

    private void startTimer() {
        timess = (int) (SMSSDK.getInstance().getIntervalTime() / 1000);
        btUserRegisterSendOutSms.setText(timess + "s");
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
                            btUserRegisterSendOutSms.setText(timess + "s");
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
        btUserRegisterSendOutSms.setText("重新获取");
        btUserRegisterSendOutSms.setClickable(true);
    }

    @OnClick({R.id.iv_user_register_go_home, R.id.iv_user_register_verification_code, R.id.bt_user_register_send_out_sms, R.id.bt_user_register, R.id.tv_user_register_go_login, R.id.tv_user_register_go_designer_register, R.id.tv_user_register_go_distributor_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_register_go_home:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.iv_user_register_verification_code:
                initData();
                break;
            case R.id.bt_user_register_send_out_sms:
                if (!etUserRegisterPhoneNum.getText().toString().trim().equals(""))
                    //                    SendSMS();
                    break;
            case R.id.bt_user_register:
                if (!etUserRegisterPhoneNum.getText().toString().trim().equals("") && !etUserRegisterVerificationCode.getText().toString().trim().equals("") && !etUserRegisterPwd.getText().toString().trim().equals("")) {
                    ModifyBindPhone();
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
