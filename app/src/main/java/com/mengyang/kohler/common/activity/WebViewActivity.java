package com.mengyang.kohler.common.activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.CommonDialogUtils;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.wv_service)
    WebView mWvService;
    @BindView(R.id.tv_webview_top)
    TopView tvWebviewTop;
    private CommonDialogUtils dialogUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvWebviewTop);

        dialogUtils = new CommonDialogUtils();
        dialogUtils.showProgress(this, "Loading...");
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
        mWvService.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("123456","加載完畢");

                if (dialogUtils != null) {
                    dialogUtils.dismissProgress();
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

        mWvService.setWebChromeClient(new WebChromeClient() {
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
            }
        });
    }
}
