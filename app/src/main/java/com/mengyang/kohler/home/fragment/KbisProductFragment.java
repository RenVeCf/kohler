package com.mengyang.kohler.home.fragment;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class KbisProductFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    private KbisBean mMeetingBean;
    private List<KbisBean.PdfListBean> mKbisPdfBean = new ArrayList<>();
    private KbisPdfAdapter mKbisPdfAdapter;

    @BindView(R.id.rv_kbis_pdf)
    RecyclerView mRvKbisPdf;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kbis_product;
    }

    @Override
    protected void initValues() {
        GridLayoutManager layoutManagerActivity = new GridLayoutManager(App.getContext(), 2);
        mRvKbisPdf.setLayoutManager(layoutManagerActivity);
        mRvKbisPdf.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        mRvKbisPdf.setHasFixedSize(true);
        mRvKbisPdf.setItemAnimator(new DefaultItemAnimator());
        mRvKbisPdf.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        IdeaApi.getApiService()
                .getKbis()
                .compose(KbisProductFragment.this.<BasicResponse<KbisBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<KbisBean>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BasicResponse<KbisBean> response) {
                        mMeetingBean = response.getData();

                        KbisBean.AgendaListBean first = mMeetingBean.getAgendaList().get(0);

                        mKbisPdfBean.clear();
                        mKbisPdfBean.addAll(mMeetingBean.getPdfList());

                        mKbisPdfAdapter = new KbisPdfAdapter(mKbisPdfBean);
                        mRvKbisPdf.setAdapter(mKbisPdfAdapter);
                        mKbisPdfAdapter.setOnItemClickListener(KbisProductFragment.this);
                        mKbisPdfAdapter.setOnItemChildClickListener(KbisProductFragment.this);
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        //判断是否这个pdf文件已存在
        String pdfTotalPath = IConstants.ROOT_PATH+ "/" +mKbisPdfBean.get(position).getH5Url().substring(mKbisPdfBean.get(position).getH5Url().lastIndexOf("/") + 1);
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
}
