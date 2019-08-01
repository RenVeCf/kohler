package com.mengyang.kohler.main.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 启动屏
 */

public class SplashActivity extends BaseActivity {

    private static final int PERMISSON_REQUESTCODE = 0;
    private String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.WAKE_LOCK,
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        checkPermissions();//检查权限
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
        Map<String, Object> stringMap = IdeaApi.getSign();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSON_REQUESTCODE) {
            boolean allGranted = true;
            for (int i = 0; i < permissions.length; ++i) {
                String perm = permissions[i];
                int req = grantResults[i];

                if (req == PackageManager.PERMISSION_GRANTED) {
                    Log.i("cohler", "onRequestPermissionsResult [" + perm + "]: PERMISSION_GRANTED");

                } else {
                    Log.i("cohler", "onRequestPermissionsResult [" + perm + "]: PERMISSION_DENIED");
                }

                if (req == PackageManager.PERMISSION_DENIED) {// 权限被用户拒绝
                    allGranted = false;
                    break;
                }
            }
        }
    }

    /**
     * 数据统计需要的动态权限
     */
    private void checkPermissions() {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : needPermissions) {
            if (ContextCompat.checkSelfPermission(this, perm) == PackageManager.PERMISSION_GRANTED) {
                Log.i("cohler", "check permission [" + perm + "]: PERMISSION_GRANTED");//有权限了
            } else {
                Log.i("cohler", "check permission [" + perm + "]: PERMISSION_DENIED");//没有权限
                //判断是否需要显示申请原因
                if (true == ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                    //ConsoleLog.debug("shouldShowRequestPermissionRationale == true");
                } else {
                    //ConsoleLog.debug("shouldShowRequestPermissionRationale == false");
                }
                needRequestPermissonList.add(perm);
            }
        }

        if (needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }
}
