package com.mengyang.kohler.main.activity;

import android.content.Intent;
import android.os.Handler;

import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.module.BasicResponse;
import com.umeng.analytics.MobclickAgent;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 启动屏
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        // 判断是否是第一次开启应用
        boolean isFirstOpen = (boolean) SPUtil.get(this, IConstants.FIRST_APP, true);
        // 如果是第一次启动，则先进入功能引导页
        if (isFirstOpen) {
            Intent intent = new Intent(this, LoadingActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                enterHomeActivity();
            }
        }, 2000);
    }

    private void enterHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("pushRegisterId", SPUtil.get(SplashActivity.this, IConstants.JPUSH_SYSTEM_ID, "") + ""); //第三方推送系统注册ID
        stringMap.put("pushChannel", 2 + ""); //推送通道 (1:友盟2:极光 3:小米 )

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getEquipmentReqistration(stringMap)
                .compose(this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                    }
                });
    }
}
