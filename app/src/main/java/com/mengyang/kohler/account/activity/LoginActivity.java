package com.mengyang.kohler.account.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
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
import com.mengyang.kohler.common.net.IdeaApiService;
import com.mengyang.kohler.common.net.Config;
import com.mengyang.kohler.common.utils.DateUtils;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.main.activity.MainActivity;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.LoginBean;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

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
 * 登录
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
    @BindView(R.id.iv_login_verification_code)
    ImageView ivLoginVerificationCode;
    @BindView(R.id.tv_login_forget_pwd)
    TextView tvLoginForgetPwd;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_login_go_register)
    TextView tvLoginGoRegister;
    private byte[] bytes;//图片验证码进制流
    private String time = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initValues() {
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
                        ivLoginVerificationCode.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    private void Login() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("mobileNo", etLoginPhoneNum.getText().toString().trim());//手机号码
        stringMap.put("password", etLoginPwd.getText().toString().trim());//用户密码
        stringMap.put("time", time);//时间戳得和图片验证码时的一样
        stringMap.put("code", etLoginVerificationCode.getText().toString().trim());//验证码

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getLogin(stringMap)
                .compose(this.<BasicResponse<LoginBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<LoginBean>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<LoginBean> response) {
                        App.destoryActivity("MainActivity");
                        SPUtil.put(LoginActivity.this, IConstants.IS_LOGIN, true);
                        SPUtil.put(LoginActivity.this, IConstants.TOKEN, response.getData().getAccessToken());
                        SPUtil.put(LoginActivity.this, IConstants.REFRESH_TOKEN, response.getData().getRefreshToken());
                        SPUtil.put(LoginActivity.this, IConstants.TYPE, response.getData().getType());
                        SPUtil.put(LoginActivity.this, IConstants.POEN_ID, response.getData().getOpenId());
                        SPUtil.put(LoginActivity.this, IConstants.USER_ID, response.getData().getOpenId());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                    //                    /**
                    //                     * token刷新成功后重新请求数据,每个请求都重写
                    //                     */
                    //                    @Override
                    //                    public void onTokenUpdateSuccess() {
                    //                        super.onTokenUpdateSuccess();
                    //                    }
                });
    }

    @OnClick({R.id.iv_lonin_go_home, R.id.tv_login_forget_pwd, R.id.bt_login, R.id.tv_login_go_register, R.id.iv_login_verification_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_lonin_go_home:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.tv_login_forget_pwd:
                startActivity(new Intent(this, ForgetPasswordOneActivity.class));
                finish();
                break;
            case R.id.bt_login:
                String phoneNum = etLoginPhoneNum.getText().toString().trim();
                String loginPwd = etLoginPwd.getText().toString().trim();
                String verificationCode = etLoginVerificationCode.getText().toString().trim();

                if (checkPwd(loginPwd)) {
                    ToastUtil.showToast("密码格式不正确");
                    return;
                }

                if (!phoneNum.equals("") && !loginPwd.equals("") && !verificationCode.equals("")) {
                    Login();
                } else {
                    ToastUtil.showToast(getString(R.string.msg_no_ok));
                    return;
                }
                break;
            case R.id.tv_login_go_register:
                startActivity(new Intent(this, UserRegisterActivity.class));
                finish();
                break;
            case R.id.iv_login_verification_code:
                initData();
                break;
            default:
                break;
        }
    }
}