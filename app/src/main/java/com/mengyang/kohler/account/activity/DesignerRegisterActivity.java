package com.mengyang.kohler.account.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.Config;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.net.IdeaApiService;
import com.mengyang.kohler.common.utils.DateUtils;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.utils.VerifyUtils;
import com.mengyang.kohler.main.activity.MainActivity;
import com.mengyang.kohler.module.BasicResponse;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
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
 * 设计师注册
 */

public class DesignerRegisterActivity extends BaseActivity {

    @BindView(R.id.rl_designer_register_go_home)
    RelativeLayout rlDesignerRegisterGoHome;
    @BindView(R.id.et_designer_register_phone_num)
    EditText etDesignerRegisterPhoneNum;
    @BindView(R.id.et_designer_register_verification_code)
    EditText etDesignerRegisterVerificationCode;
    @BindView(R.id.iv_designer_register_verification_code)
    ImageView ivDesignerRegisterVerificationCode;
    @BindView(R.id.et_designer_register_sms_verification_code)
    EditText etDesignerRegisterSmsVerificationCode;
    @BindView(R.id.bt_designer_register_send_out_sms)
    Button btDesignerRegisterSendOutSms;
    @BindView(R.id.et_designer_register_pwd)
    EditText etDesignerRegisterPwd;
    @BindView(R.id.bt_designer_register)
    Button btDesignerRegister;
    @BindView(R.id.tv_designer_register_go_login)
    TextView tvDesignerRegisterGoLogin;
    @BindView(R.id.tv_designer_register_go_user_register)
    TextView tvDesignerRegisterGoUserRegister;
    @BindView(R.id.tv_designer_register_go_distributor_register)
    TextView tvDesignerRegisterGoDistributorRegister;
    @BindView(R.id.sv_desiger)
    ScrollView mSvDesiger;
    private byte[] bytes;//图片验证码进制流
    private String time;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_designer_register;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    @Override
    protected void initListener() {
        etDesignerRegisterPhoneNum.addTextChangedListener(new TextWatcher() {
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
                if (etDesignerRegisterPhoneNum.getText().toString().trim().length() == 11 && VerifyUtils.isMobileNumber(etDesignerRegisterPhoneNum.getText().toString().trim())) {

                } else if (etDesignerRegisterPhoneNum.getText().toString().trim().length() == 11){
                    ToastUtil.showToast("请输入正确的手机号码！");
                    etDesignerRegisterPhoneNum.setText("");
                }
            }
        });

        mSvDesiger.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideInput();
                return false;
            }
        });

    }

    @Override
    protected void initData() {
        Map<String, Object> stringMap = IdeaApi.getSign();
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
                        ivDesignerRegisterVerificationCode.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    private void LoginSMS() {
        Map<String, Object> stringMap = IdeaApi.getSign();
        stringMap.put("mobileNo", etDesignerRegisterPhoneNum.getText().toString().trim());//手机号码

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getLoginSMS(stringMap)
                .compose(DesignerRegisterActivity.this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(DesignerRegisterActivity.this, false) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                    }
                });
    }

    private void ModifyBindPhone() {
        Map<String, Object> stringMap = IdeaApi.getSign();
        stringMap.put("mobileNo", etDesignerRegisterPhoneNum.getText().toString().trim());//手机号码
        stringMap.put("code", etDesignerRegisterVerificationCode.getText().toString().trim());//验证码
        stringMap.put("verifyCode", etDesignerRegisterSmsVerificationCode.getText().toString().trim());//短信验证码
        stringMap.put("password", etDesignerRegisterPwd.getText().toString().trim());//用户密码
        stringMap.put("type", "designer");//用户类型
        stringMap.put("time", time);//时间戳

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getUserRegister(stringMap)
                .compose(DesignerRegisterActivity.this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(DesignerRegisterActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        startActivity(new Intent(DesignerRegisterActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }

    @OnClick({R.id.rl_designer_register_go_home, R.id.iv_designer_register_verification_code, R.id.bt_designer_register_send_out_sms, R.id.bt_designer_register, R.id.tv_designer_register_go_login, R.id.tv_designer_register_go_user_register, R.id.tv_designer_register_go_distributor_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_designer_register_go_home:
                hideInput();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.iv_designer_register_verification_code:
                initData();
                break;
            case R.id.bt_designer_register_send_out_sms:
                if (!etDesignerRegisterPhoneNum.getText().toString().trim().equals("") && etDesignerRegisterPhoneNum.getText().toString().trim().length() == 11)
                    LoginSMS();
                else
                    ToastUtil.showToast(getString(R.string.msg_no_ok));
                break;
            case R.id.bt_designer_register:
                String phoneNum = etDesignerRegisterPhoneNum.getText().toString().trim();
                String verificationCode = etDesignerRegisterVerificationCode.getText().toString().trim();
                String smsCode = etDesignerRegisterSmsVerificationCode.getText().toString().trim();
                String registerPwd = etDesignerRegisterPwd.getText().toString().trim();

                if (checkPwd(registerPwd)) {
                    ToastUtil.showToast("密码格式不正确");
                    return;
                }

                if (!phoneNum.equals("") && !verificationCode.equals("") && !smsCode.equals("") && !registerPwd.equals("")) {
                    ModifyBindPhone();
                } else {
                    ToastUtil.showToast(getString(R.string.msg_no_ok));
                    return;
                }
                break;
            case R.id.tv_designer_register_go_login:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.tv_designer_register_go_user_register:
                startActivity(new Intent(this, UserRegisterActivity.class));
                finish();
                break;
            case R.id.tv_designer_register_go_distributor_register:
                startActivity(new Intent(this, DistributorRegisterActivity.class));
                finish();
                break;
            default:
                break;
        }
    }
}
