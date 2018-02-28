package com.mengyang.kohler;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.utils.SPUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.sms.SMSSDK;

/**
 * Description : 全局
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2017/12/28
 */

public class App extends Application {
    private static Application instance;
    private static Context context;
    public static String registrationId; //极光的系统注册ID
    private static App sManager;
    private Stack<WeakReference<Activity>> mActivityStack;
    private static Map<String, Activity> destoryMap = new HashMap<>();
    private Activity currentActivity;

    public App() {
    }

    public static App getManager() {
        if (sManager == null) {
            synchronized (App.class) {
                if (sManager == null) {
                    sManager = new App();
                }
            }
        }
        return sManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Config.DEBUG = true;// TODO: 2017/12/1 ,用于友盟的log跟踪,请勿删除
        UMShareAPI.get(this);
        instance = this;
        context = getApplicationContext();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //极光SMS
        SMSSDK.getInstance().initSdk(this);
        SMSSDK.getInstance().setIntervalTime(30 * 1000);

        registrationId = JPushInterface.getRegistrationID(this);
        SPUtil.put(context, IConstants.JPUSH_SYSTEM_ID, registrationId);

        CrashReport.initCrashReport(getApplicationContext(), "4390a5fb6e", true);
    }

    /**
     * 配置三方平台的appkey
     */ {
        //        PlatformConfig.setWeixin("wx3a3e72f3c0cf486c", "afc866a07ef9b96734d3e5b1ab061eb5");
        PlatformConfig.setWeixin("wx3a3e72f3c0cf486c", "2e8c62fa24ea6184660a703c9609d056");
    }


    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */

    public static void addDestoryActivity(Activity activity, String activityName) {
        destoryMap.put(activityName, activity);
    }

    /**
     * 销毁指定Activity
     */
    public static void destoryActivity(String activityName) {
        Set<String> keySet = destoryMap.keySet();
        for (String key : keySet) {
            destoryMap.get(key).finish();
        }
    }

    /**
     * 添加Activity到栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(new WeakReference<>(activity));
    }

    /**
     * 检查弱引用是否释放，若释放，则从栈中清理掉该元素
     */
    public void checkWeakReference() {
        if (mActivityStack != null) {
            // 使用迭代器进行安全删除
            for (Iterator<WeakReference<Activity>> it = mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity temp = activityReference.get();
                if (temp == null) {
                    it.remove();
                }
            }
        }
    }

    /**
     * 获取当前Activity（栈中最后一个压入的）
     *
     * @return
     */
    public Activity currentActivity() {
        checkWeakReference();
        if (mActivityStack != null && !mActivityStack.isEmpty()) {
            return mActivityStack.lastElement().get();
        }
        return null;
    }

    /**
     * 关闭当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = currentActivity();
        if (activity != null) {
            finishActivity(activity);
        }
    }

    /**
     * 关闭指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && mActivityStack != null) {
            // 使用迭代器进行安全删除
            for (Iterator<WeakReference<Activity>> it = mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity temp = activityReference.get();
                // 清理掉已经释放的activity
                if (temp == null) {
                    it.remove();
                    continue;
                }
                if (temp == activity) {
                    it.remove();
                }
            }
            activity.finish();
        }
    }

    /**
     * 关闭指定类名的所有Activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        if (mActivityStack != null) {
            // 使用迭代器进行安全删除
            for (Iterator<WeakReference<Activity>> it = mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity activity = activityReference.get();
                // 清理掉已经释放的activity
                if (activity == null) {
                    it.remove();
                    continue;
                }
                if (activity.getClass().equals(cls)) {
                    it.remove();
                    activity.finish();
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (mActivityStack != null) {
            for (WeakReference<Activity> activityReference : mActivityStack) {
                Activity activity = activityReference.get();
                if (activity != null) {
                    activity.finish();
                }
            }
            mActivityStack.clear();
        }
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        try {
            finishAllActivity();
            // 退出JVM,释放所占内存资源,0表示正常退出
            System.exit(0);
            // 从系统中kill掉应用程序
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized Application getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }


}
