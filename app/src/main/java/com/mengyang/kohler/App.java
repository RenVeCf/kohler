package com.mengyang.kohler;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.mengyang.kohler.common.utils.IConstants;
import com.mengyang.kohler.common.utils.SPUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Description : 全局
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2017/12/28
 */

public class App extends Application {
    private static Application instance;
    private Activity currentActivity;
    private static Context context;
    public static String registrationId; //极光的系统注册ID


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        registrationId = JPushInterface.getRegistrationID(this);
        SPUtil.put(context, IConstants.JPUSH_SYSTEM_ID, registrationId);
    }

    public static synchronized Application getInstance() {
        return instance;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
