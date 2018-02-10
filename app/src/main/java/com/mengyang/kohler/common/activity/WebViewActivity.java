package com.mengyang.kohler.common.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.wv_service)
    WebView mWvService;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initValues() {




    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        String h5url = getIntent().getStringExtra("h5url");
        WebSettings webSettings = mWvService.getSettings();

        //        webSettings.setBuiltInZoomControls(true);// 显示缩放按钮(wap网页不支持)
//        webSettings.setUseWideViewPort(true);// 支持双击缩放(wap网页不支持)
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        mWvService.loadUrl(h5url);


        //设置客户端，让点击跳转操作在当前应用内存进行操作
        mWvService.setWebViewClient(new WebViewClient());
        mWvService.setWebChromeClient(new WebChromeClient(){
            //返回当前加载网页的进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.i("123456","newProgress:"+newProgress);
            }

            //获取当前网页的标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.i("123456","title:"+title);
            }
        });
    }

}
