package com.mengyang.kohler.home.activity;

import android.annotation.SuppressLint;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.allyes.analytics.AIOAnalytics;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

/**
 * 你好科勒
 */

public class HiKohlerActivity extends BaseActivity {

    @BindView(R.id.tv_hi_kohler_top)
    TopView tvHiKohlerTop;
    @BindView(R.id.wv_hi_kohler)
    WebView wvHiKohler;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hi_kohler;
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvHiKohlerTop);
        AIOAnalytics.onEvent("hello_kohler");
        MobclickAgent.onEvent(HiKohlerActivity.this, "hello_kohler");
        wvHiKohler.loadUrl(getIntent().getStringExtra("h5url"));
        wvHiKohler.addJavascriptInterface(this, "android");//添加js监听 这样html就能调用客户端
        WebSettings webSettings = wvHiKohler.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        webSettings.setUseWideViewPort(true);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        AIOAnalytics.onPageBegin("hello_kohler");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        AIOAnalytics.onPageEnd("hello_kohler");
    }
}
