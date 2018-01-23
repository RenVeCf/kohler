package com.mengyang.kohler.main.activity;

import android.content.Intent;
import android.os.Handler;

import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.IConstants;
import com.mengyang.kohler.common.utils.SPUtil;

/**
 * 启动屏
 *
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initValues() {
        // 判断是否是第一次开启应用
        boolean isFirstOpen = (boolean) SPUtil.get(this, IConstants.FIRST_APP, false);
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpen) {
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

    }
}
