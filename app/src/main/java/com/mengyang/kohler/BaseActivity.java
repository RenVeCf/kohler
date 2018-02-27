package com.mengyang.kohler;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.common.utils.PermissionUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * Description : activity 父类
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2017/9/22
 */

public abstract class BaseActivity extends RxAppCompatActivity {
    PermissionUtils.PermissionGrant mPermissionGrant;
    public Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //防止getFragment为null
        if (savedInstanceState != null) {
            savedInstanceState.remove("android:support:fragments");
        }
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(getLayoutId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //控件注释框架
        ButterKnife.bind(this);
        //沉浸式状态栏初始化
        ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
        //初始化本地数据
        initValues();
        //初始化布局监听器
        initListener();
        //初始化网络数据
        initData();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    //权限回调
    private OnPermissionListener OnPermissionListener;

    public void setOnPermissionListener(OnPermissionListener OnPermissionListener) {
        this.OnPermissionListener = OnPermissionListener;
    }

    public interface OnPermissionListener {
        void openIntent();
    }

    //权限请求
    public void openPermission(int[] code_permission) {
        mPermissionGrant = new PermissionUtils.PermissionGrant() {
            @Override
            public void onPermissionGranted(int requestCode) {
                if (OnPermissionListener != null) {
                    OnPermissionListener.openIntent();
                }
            }
        };
        if (code_permission.length <= 0) {
            return;
        } else if (code_permission.length == 1) {
            PermissionUtils.requestPermission(BaseActivity.this, code_permission[0], mPermissionGrant);
        } else {
            PermissionUtils.requestMultiPermissions(BaseActivity.this, code_permission, mPermissionGrant);
        }
    }

    //请求权限回调方法（必须实现OnRequestPermissionsResultCallback接口)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.requestPermissionsResult(BaseActivity.this, requestCode, permissions, grantResults, mPermissionGrant);
    }

    /**
     * @return 返回xml布局 R.layout.xxx  布局文件
     */
    protected abstract @LayoutRes
    int getLayoutId();

    /**
     * 初始化本地数据 比如List等 new出来
     */
    protected abstract void initValues();

    /**
     * 初始化布局监听器
     */
    protected abstract void initListener();

    /**
     * 初始话网络数据
     */
    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //沉浸式状态栏注销
        ImmersionBar.with(this).destroy(); //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }


    /**
     * 获取状态栏高度
     * @return
     */
    public int getStatusBarHeight()
    {
        try
        {
            Resources resource = App.getInstance().getResources();
            int resourceId = resource.getIdentifier("status_bar_height", "dimen", "Android");
            if (resourceId != 0)
            {
                return resource.getDimensionPixelSize(resourceId);
            }
        } catch (Exception e)
        {
        }
        return 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

}
