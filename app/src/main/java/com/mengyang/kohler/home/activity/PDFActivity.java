package com.mengyang.kohler.home.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.SPUtil;

import butterknife.BindView;

/**
 * PDF
 */

public class PDFActivity extends BaseActivity {

    @BindView(R.id.pdf_home)
    PDFView pdfHome;
    @BindView(R.id.pageTv1)
    TextView pageTv1;
    @BindView(R.id.pageTv)
    TextView pageTv;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private int p;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pdf;
    }

    @Override
    protected void initValues() {
        final int myPage = (int) SPUtil.get(PDFActivity.this, "page", 0);
        //选择pdf
        pdfHome.fromAsset("android_pdf_test.pdf")
                //                .pages(0, loading2, loading3, 4, 5); // 把0 , loading2 , loading3 , 4 , 5 过滤掉
                //是否允许翻页，默认是允许翻页
                .enableSwipe(true)
                //pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
                .swipeHorizontal(true)
                //
                .enableDoubletap(false)
                //设置默认显示第0页
                .defaultPage(myPage)
                //允许在当前页面上绘制一些内容，通常在屏幕中间可见。
                //                .onDraw(onDrawListener)
                //                // 允许在每一页上单独绘制一个页面。只调用可见页面
                //                .onDrawAll(onDrawListener)
                //设置加载监听
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        pageTv.setText(nbPages + "");
                        pageTv1.setText(myPage + "/");
                    }
                })
                //设置翻页监听
                .onPageChange(new OnPageChangeListener() {

                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        p = page;
                        pageTv1.setText(page + "/");
                    }
                })
                //设置页面滑动监听
                //                .onPageScroll(onPageScrollListener)
                //                .onError(onErrorListener)
                // 首次提交文档后调用。
                //                .onRender(onRenderListener)
                // 渲染风格（就像注释，颜色或表单）
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                // 改善低分辨率屏幕上的渲染
                .enableAntialiasing(true)
                // 页面间的间距。定义间距颜色，设置背景视图
                .spacing(0)
                .load();

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    private void getPermission() {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(PDFActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(PDFActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(PDFActivity.this,
                        PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }

            ActivityCompat.requestPermissions(PDFActivity.this,
                    PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }

        while ((ContextCompat.checkSelfPermission(PDFActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) != PackageManager.PERMISSION_GRANTED) {
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当activity销毁的时候，保存当前的页数，下次打开的时候，直接翻到这个页
        SPUtil.put(PDFActivity.this, "page", p);
    }
}
