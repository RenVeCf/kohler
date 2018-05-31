package com.mengyang.kohler.home.fragment;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.activity.WebViewActivity;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.FileUtils;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.home.activity.DownLoaderPDFActivity;
import com.mengyang.kohler.home.activity.MineManualActivity;
import com.mengyang.kohler.home.activity.PDFActivity;
import com.mengyang.kohler.home.adapter.KbisPdfAdapter;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.KbisBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KbisProductFragment extends BaseFragment {

    @BindView(R.id.rv_kbis_pdf)
    RecyclerView mRvKbisPdf;

    private List<KbisBean.PdfListBean> mKbisPdfBean = new ArrayList<>();
    private KbisPdfAdapter mKbisPdfAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kbis_product;
    }

    @Override
    protected void initValues() {
        mRvKbisPdf.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SpacesItemDecoration decoration = new SpacesItemDecoration(25);
        mRvKbisPdf.addItemDecoration(decoration);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        KbisBean data = (KbisBean) getArguments().getSerializable("data");
        List<KbisBean.PdfListBean> pdfList = data.getPdfList();
        mKbisPdfBean.clear();
        for (int i = 0; i < pdfList.size(); i++) {
            KbisBean.PdfListBean pdfListBean = pdfList.get(i);
            mKbisPdfBean.add(pdfListBean);
        }
        mKbisPdfAdapter = new KbisPdfAdapter(mKbisPdfBean);
        mRvKbisPdf.setAdapter(mKbisPdfAdapter);
        mKbisPdfAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //判断是否这个pdf文件已存在
                String pdfTotalPath = IConstants.ROOT_PATH + "/" + mKbisPdfBean.get(position).getH5Url().substring(mKbisPdfBean.get(position).getH5Url().lastIndexOf("/") + 1);
                if (FileUtils.isFileExist(pdfTotalPath)) {
                    startActivity(new Intent(KbisProductFragment.this.getActivity(), PDFActivity.class).putExtra("PdfUrl", mKbisPdfBean.get(position).getH5Url()));
                } else {//没找到就去下载
                    // TODO: 2018/2/11 ,还需要考虑到断点续传的功能,若是客户在下载的过程中退出应用，下次在进来的时候，PDF虽然有了，但是不能显示
                    String pdfUrl = mKbisPdfBean.get(position).getH5Url();
                    if (pdfUrl != null && !TextUtils.isEmpty(pdfUrl)) {
                        Intent intent = new Intent(KbisProductFragment.this.getActivity(), DownLoaderPDFActivity.class);
                        intent.putExtra("PdfUrl", pdfUrl);
                        intent.putExtra("mPdfTotalPath", pdfTotalPath);
                        startActivity(intent);
                        return;
                    } else {
                        ToastUtil.showToast("PDF url is null");
                    }
                }
            }
        });
    }
}
