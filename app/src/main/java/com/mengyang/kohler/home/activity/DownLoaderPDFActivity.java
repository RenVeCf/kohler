package com.mengyang.kohler.home.activity;

import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.CommonDialogUtils;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoaderPDFActivity extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener, OnDrawListener {
    /* 请求识别码 */
    private static final int MY_PERMISSIONS_REQUEST_READ = 6;


    @BindView(R.id.pdf_view)
    PDFView pdfView;
    @BindView(R.id.pb_pdf)
    ProgressBar pbPdf;
    private OkHttpClient okHttpClient;
    private String url = "";
    private CommonDialogUtils dialogUtils;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //进度条的值
                    int i = msg.arg1;
                    pbPdf.setProgress(i);
            }
            if (msg.arg1 == 100) {
                displayFromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), url.substring(url.lastIndexOf("/") + 1)));
            }
        }
    };
//    private Response mResponse;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_down_loader_pdf;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        url = getIntent().getStringExtra("PdfUrl");



    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
////        if (Build.VERSION.SDK_INT >= 23) {
////            ActivityCompat.requestPermissions(DownLoaderPDFActivity.this,
////                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
////                    MY_PERMISSIONS_REQUEST_READ);
////
////        }
//
//        dialogUtils = new CommonDialogUtils();
//        dialogUtils.showProgress(this, "Loading...");
//        okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().url(url).build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                LogUtils.i("kohler6","网络请求失败"+e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                mResponse = response;
//
//                saveFile(response);
//
//            }
//        });

        dialogUtils = new CommonDialogUtils();
        dialogUtils.showProgress(this, "Loading...");
        okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(SDPath, url.substring(url.lastIndexOf("/") + 1));
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
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    private void saveFile(Response response) {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            long total = response.body().contentLength();

            String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            File file = new File(SDPath, url.substring(url.lastIndexOf("/") + 1));
            if (!file.exists()) {
                file.mkdirs();
            }
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
            LogUtils.i("kohler6","出错了"+e);
        } finally {
            dialogUtils.dismissProgress();
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }
    }

    private void displayFromFile(File file) {
        pdfView.fromFile(file)//设置pdf文件地址
                .defaultPage(1)//设置默认显示第1页
                .onPageChange(this)//设置翻页监听
                .onLoad(this)//设置加载监听
                .onDraw(this)//绘图监听
                .swipeHorizontal(true)//pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
                .enableSwipe(true)//是否允许翻页，默认是允许翻
                .scrollHandle(null)
                .enableAntialiasing(true)// 改善低分辨率屏幕上的渲染
                .load();
    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

    }

    @Override
    public void loadComplete(int nbPages) {
        if (dialogUtils != null) {
            dialogUtils.dismissProgress();
        }
        ToastUtil.showToast("加载完成" + nbPages);
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        int pageNum = page + 1;
        ToastUtil.showToast(" " + pageNum + " / " + pageCount);
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
}
