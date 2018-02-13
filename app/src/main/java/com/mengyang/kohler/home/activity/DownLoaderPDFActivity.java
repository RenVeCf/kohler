package com.mengyang.kohler.home.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
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

import javax.xml.transform.Result;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoaderPDFActivity extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener, OnDrawListener {
    /* 请求识别码 */
    private static final int MY_PERMISSIONS_REQUEST_READ = 6;
    String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    @BindView(R.id.pdf_view)
    PDFView pdfView;
    @BindView(R.id.pb_pdf)
    ProgressBar pbPdf;
    private OkHttpClient okHttpClient;
    private String url = "";
    private CommonDialogUtils dialogUtils;

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
                Intent intent = new Intent();
                intent.putExtra("pdfPath", mFileAbsolutePath);
                setResult(RESULT_OK);
                displayFromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), url.substring(url.lastIndexOf("/") + 1)));
            }
        }
    };
    private String mFileAbsolutePath;
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
                        Log.i("kohler","下载PDF，progress = "+progress );
                        handler.sendMessage(msg);
                    }
                    fos.flush();
                } catch (Exception e) {
                    dialogUtils.dismissProgress();
                    Log.i("kohler","下载PDF，出错了 = "+e );
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
        });
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
