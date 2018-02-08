package com.mengyang.kohler;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.KeyEvent;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * Description : activity 父类
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2017/9/22
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    public Bundle savedInstanceState;
    private long exitTime;
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
    }

    /**
     * @return 返回xml布局 R.layout.xxx  布局文件
     */
    protected abstract @LayoutRes int getLayoutId();

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

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
//                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
}
