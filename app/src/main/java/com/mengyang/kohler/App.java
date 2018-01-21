package com.mengyang.kohler;

import android.app.Application;
import android.content.Context;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2017/12/28
 */

public class App extends Application{
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
