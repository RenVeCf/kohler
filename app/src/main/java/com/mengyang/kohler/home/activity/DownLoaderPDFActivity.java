package com.mengyang.kohler.home.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.gson.Gson;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.utils.CommonDialogUtils;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.home.adapter.MyBrochureAdapter6;
import com.mengyang.kohler.module.PdfBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载PDF
 */

public class DownLoaderPDFActivity extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener, OnDrawListener, OnErrorListener {
    /* 请求识别码 */
    private static final int MY_PERMISSIONS_REQUEST_READ = 6;
//    String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+ "kohlerPdf";
    String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    @BindView(R.id.pdf_view)
    PDFView pdfView;
    @BindView(R.id.pb_pdf)
    ProgressBar pbPdf;
    private OkHttpClient okHttpClient;
    private String url = "";
    private CommonDialogUtils dialogUtils;
    private PdfBean mPdfBean = new PdfBean();
    private List<String> mNameList= new ArrayList<>();
    private PdfBean.UserNameBean.UserPdfItemBean mUserPdfItemBean = new PdfBean.UserNameBean.UserPdfItemBean();
    private String mUserName = (String) SPUtil.get(App.getContext(), IConstants.USER_NIKE_NAME, "");
    private List<PdfBean.UserNameBean.UserPdfItemBean> mPdfItemList;
    private PdfBean.UserNameBean mUserNameBean;
    private List<PdfBean.UserNameBean> mUserNameBeanList = new ArrayList<>();

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //进度条的值
                    int i = msg.arg1;
                    pbPdf.setProgress(i);
                    break;
                default:
                    break;
            }

            if (msg.arg1 == 100) {
                displayFromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), url.substring(url.lastIndexOf("/") + 1)));
            }
        }
    };

    private String mFileAbsolutePath;
    private boolean mIsOnlyPreview;//是否只是预览
    private String mPdfTotalPath;
    private String mDownLoadKvUrl;
    //    private Response mResponse;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_down_loader_pdf;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        url = getIntent().getStringExtra("PdfUrl");
        mPdfTotalPath = getIntent().getStringExtra("mPdfTotalPath");
        mDownLoadKvUrl = getIntent().getStringExtra("mDownLoadKvUrl");
        mIsOnlyPreview = getIntent().getBooleanExtra("isPreview", false);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        dialogUtils = new CommonDialogUtils();
        dialogUtils.showProgress(this, "Loading...");
        okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
//        Request request = new Request.Builder().url("http://ojd06y9cv.bkt.clouddn.com/619820641c5890217b99e5cc968e526c.pdf").build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (mIsOnlyPreview) {
                    byte[] bytes = response.body().bytes();
                    displayFromFile2(bytes);
                } else {
                    mIsOnlyPreview = false;
//                    dismiss();

                    InputStream is = null;
                    byte[] buf = new byte[1024*10];
                    int len = 0;
                    FileOutputStream fos = null;

                    try {
                        is = response.body().byteStream();
                        long total = response.body().contentLength();
                        File file = new File(SDPath, url.substring(url.lastIndexOf("/") + 1));
                        mFileAbsolutePath = file.getAbsolutePath();
                        fos = new FileOutputStream(file);
                        long sum = 0;
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                            sum += len;
                            int progress = (int) (sum * 1.0f / total * 100);
                            Message msg = handler.obtainMessage();
                            msg.what = 1;
                            msg.arg1 = progress;
                            handler.sendMessage(msg);
                        }
                        fos.flush();
                    } catch (Exception e) {
                        dialogUtils.dismissProgress();
                    } finally {
                        try {
                            if (is != null) {
                                is.close();
                            }
                        } catch (IOException e) {
                        }
                        try {
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (IOException e) {
                        }
                    }
                }
            }
        });
    }

    private void dismiss() {
        if (dialogUtils != null) {
            dialogUtils.dismissProgress();
        }
    }

    private void displayFromFile(File file) {
        pdfView.fromFile(file)//设置pdf文件地址
                .defaultPage(0)//设置默认显示第1页
                .onPageChange(this)//设置翻页监听
                .onLoad(this)//设置加载监听
                .onDraw(this)//绘图监听
                .swipeHorizontal(false)//pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
                .enableSwipe(true)//是否允许翻页，默认是允许翻
                .scrollHandle(null)
                .enableAntialiasing(true)// 改善低分辨率屏幕上的渲染
                .onError(this)
                .pageFitPolicy(FitPolicy.WIDTH)
                .load();
    }

    private void displayFromFile2(byte[] bytes) {
        pdfView.fromBytes(bytes)//设置pdf文件地址
                .defaultPage(0)//设置默认显示第1页
                .onPageChange(this)//设置翻页监听
                .onLoad(this)//设置加载监听
                .onDraw(this)//绘图监听
                .swipeHorizontal(false)//pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
                .enableSwipe(true)//是否允许翻页，默认是允许翻
                .scrollHandle(null)
                .enableAntialiasing(true)// 改善低分辨率屏幕上的渲染
                .onError(this)
                .pageFitPolicy(FitPolicy.WIDTH)
                .load();
    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

    }

    @Override
    public void loadComplete(int nbPages) {
        dismiss();
        ToastUtil.showToast("加载完成" + nbPages);
        Log.i("123", "nbPages = " + nbPages);
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        int pageNum = page + 1;
        ToastUtil.showToast(" " + pageNum + " / " + pageCount);
        Log.i("123", "pageCount = " + pageCount);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                saveFile(mResponse);
            } else {
                // Permission Denied
                //  Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    @Override
    public void onError(Throwable t) {
        Log.i("123", "onError = 出错了" + t);
    }

}
