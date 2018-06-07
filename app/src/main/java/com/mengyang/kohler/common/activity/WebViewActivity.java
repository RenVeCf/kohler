package com.mengyang.kohler.common.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.CommonDialogUtils;
import com.mengyang.kohler.common.utils.ItemLongClickedPopWindow;
import com.mengyang.kohler.common.utils.SizeUtil;
import com.mengyang.kohler.common.view.TopView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.wv_service)
    WebView mWvService;
    @BindView(R.id.tv_webview_top)
    TopView tvWebviewTop;
    private CommonDialogUtils dialogUtils;
    private int mFlag = 0;
    String h5url = "";
    // 长按查看图片
    private ItemLongClickedPopWindow itemLongClickedPopWindow;
    // 手指触发屏幕的坐标
    private int downX, downY;
    // 需要保存图片的路径
    private String saveImgUrl = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvWebviewTop);

        mFlag = getIntent().getIntExtra("flag", 0);
        if (mFlag == 1)
            tvWebviewTop.setVisibility(View.VISIBLE);
        else
            tvWebviewTop.setVisibility(View.GONE);
        dialogUtils = new CommonDialogUtils();
        dialogUtils.showProgress(this, "Loading...");
        h5url = getIntent().getStringExtra("h5url");
        WebSettings webSettings = mWvService.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);// 排版适应屏幕
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWvService.setOnTouchListener(listener);

        mWvService.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult result = ((WebView) v).getHitTestResult();
                if (null == result)
                    return false;
                int type = result.getType();
                if (type == WebView.HitTestResult.UNKNOWN_TYPE)
                    return false;
                if (type == WebView.HitTestResult.EDIT_TEXT_TYPE) {

                }

                itemLongClickedPopWindow = new ItemLongClickedPopWindow(this, ItemLongClickedPopWindow.IMAGE_VIEW_POPUPWINDOW, SizeUtil.dp2px(App.getContext(), 120), SizeUtil.dp2px(App.getContext(), 90));

                switch (type) {
                    case WebView.HitTestResult.PHONE_TYPE: // 处理拨号
                        break;
                    case WebView.HitTestResult.EMAIL_TYPE: // 处理Email
                        break;
                    case WebView.HitTestResult.GEO_TYPE: // TODO
                        break;
                    case WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
                        break;
                    case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                        break;
                    case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
                        // 获取图片的路径
                        saveImgUrl = result.getExtra();
                        //通过GestureDetector获取按下的位置，来定位PopWindow显示的位置
                        itemLongClickedPopWindow.showAtLocation(v, Gravity.TOP | Gravity.LEFT, downX, downY + 10);
                        break;
                    default:
                        break;
                }

                //                itemLongClickedPopWindow.getView(R.id.item_longclicked_viewImage)
                //                        .setOnClickListener(new View.OnClickListener() {
                //                            @Override
                //                            public void onClick(View v) {
                //                                itemLongClickedPopWindow.dismiss();
                //                                Intent intent = new Intent(App.getContext(), ShowImgActivity.class);
                //                                intent.putExtra("info", saveImgUrl);
                //                                startActivity(intent);
                //                            }
                //                        });
                itemLongClickedPopWindow.getView(R.id.item_longclicked_saveImage)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                itemLongClickedPopWindow.dismiss();
                                new SaveImage().execute(); // Android 4.0以后要使用线程来访问网络
                            }
                        });
                return true;
            }
        });
        mWvService.loadUrl(h5url);
    }

    View.OnTouchListener listener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            downX = (int) arg1.getX();
            downY = (int) arg1.getY();
            return false;
        }
    };

    /***
     * 功能：用线程保存图片
     *
     * @author wangyp
     */
    private class SaveImage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                String sdcard = Environment.getExternalStorageDirectory().toString();
                File file = new File(sdcard + "/Download");
                if (!file.exists()) {
                    file.mkdirs();
                }
                int idx = saveImgUrl.lastIndexOf(".");
                String ext = saveImgUrl.substring(idx);
                file = new File(sdcard + "/Download/" + new Date().getTime() + ext);
                InputStream inputStream = null;
                URL url = new URL(saveImgUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(20000);
                if (conn.getResponseCode() == 200) {
                    inputStream = conn.getInputStream();
                }
                byte[] buffer = new byte[4096];
                int len = 0;
                FileOutputStream outStream = new FileOutputStream(file);
                while ((len = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                result = "图片已保存至：" + file.getAbsolutePath();

                Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                final Uri localUri = Uri.fromFile(file);
                localIntent.setData(localUri);
                sendBroadcast(localIntent);
            } catch (Exception e) {
                result = "保存失败！" + e.getLocalizedMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(App.getContext(), result, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        //设置客户端，让点击跳转操作在当前应用内存进行操作
        mWvService.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (dialogUtils != null) {
                    dialogUtils.dismissProgress();
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //当发生证书认证错误时，采用默认的处理方法handler.cancel()，停止加载问题页面
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.cancel();
            }
        });

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
