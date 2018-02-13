package com.mengyang.kohler.common.activity;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.wv_service)
    WebView mWvService;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //沉浸式状态栏初始化白色
        ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        String h5url = getIntent().getStringExtra("h5url");
        WebSettings webSettings = mWvService.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        mWvService.loadUrl(h5url);
        //设置客户端，让点击跳转操作在当前应用内存进行操作
        mWvService.setWebViewClient(new WebViewClient());
        mWvService.setWebChromeClient(new WebChromeClient() {
            //返回当前加载网页的进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            //获取当前网页的标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }
}
