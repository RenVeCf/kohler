package com.mengyang.kohler.home.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.activity.WebViewActivity;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.FileUtil;
import com.mengyang.kohler.common.utils.FileUtils;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.BrochureListAdapter;
import com.mengyang.kohler.home.adapter.MyBrochureAdapter6;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.PdfBean;
import com.mengyang.kohler.module.bean.BooksListBean;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * 我的手册/我的图册
 */

public class MineManualActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.tv_mine_manual_top)
    TopView tvMineManualTop;
    @BindView(R.id.rv_mine_manual_brochure_list)
    RecyclerView rvMineManualBrochureList;
    @BindView(R.id.rv_mine_manual_my_brochure)
    RecyclerView rvMineManualMyBrochure;
    @BindView(R.id.srl_mine_manual)
    SwipeRefreshLayout srlMineManual;
    private BrochureListAdapter mMineManualAdapter;
    private List<BooksListBean.ResultListBean> mBooksListBean;


    private List<PdfBean> mPdfBeanList = new ArrayList<>();
    private List<PdfBean.UserNameBean> mUserNameBeanList = new ArrayList<>();
//    private BooksBean mDeletePDF;
    private int pageNum = 0; //请求页数
    private String mPdfTotalPath;
    private int mCurPosition;
    private String mPdfPathSuccess;

    LinkedHashMap<String, String> mapUrl = new LinkedHashMap<String, String>();
    private String mDownLoadKvUrl;
    private PdfBean mPdfBean = new PdfBean();
    private List<PdfBean.UserNameBean.UserPdfItemBean> mPdfItemList = new ArrayList<>();;
    private MyBrochureAdapter6 mMyBrochureAdapter6;
    private List<String> mNameList= new ArrayList<>();

    private List<String> mLocalTempPdfFileName = new ArrayList<>();
    private PdfBean.UserNameBean.UserPdfItemBean mUserPdfItemBean = new PdfBean.UserNameBean.UserPdfItemBean();
    private PdfBean.UserNameBean mUserNameBean;
//    private String mUserName = (String) SPUtil.get(App.getContext(), IConstants.USER_PDF_DATA, "");
    private String mUserName = (String) SPUtil.get(App.getContext(), IConstants.USER_NIKE_NAME, "");

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_manual;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvMineManualTop);

        //        //必须先初始化SQLite
        //        DatabaseUtils.initHelper(MineManualActivity.this, "books.db");

        // 下载图册设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvMineManualBrochureList.setLayoutManager(layoutManager);
        rvMineManualBrochureList.addItemDecoration(new SpacesItemDecoration(13));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvMineManualBrochureList.setHasFixedSize(true);
        rvMineManualBrochureList.setItemAnimator(new DefaultItemAnimator());
        mBooksListBean = new ArrayList<>();
        mMineManualAdapter = new BrochureListAdapter(mBooksListBean);
        rvMineManualBrochureList.setAdapter(mMineManualAdapter);


        // 删除图册设置管理器
        LinearLayoutManager layoutManager_delete = new LinearLayoutManager(App.getContext());
        layoutManager_delete.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvMineManualMyBrochure.setLayoutManager(layoutManager_delete);
        rvMineManualMyBrochure.addItemDecoration(new SpacesItemDecoration(13));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvMineManualMyBrochure.setHasFixedSize(true);
        rvMineManualMyBrochure.setItemAnimator(new DefaultItemAnimator());


        final List<String> listFileName = FileUtil.judgePdfIsExit(mLocalTempPdfFileName);

        //本地有文件，从数据库中获取相应数据
        if (listFileName != null && listFileName.size() > 0) {
            // TODO: 2018/3/2 ,请求网络获取PDF数据进行对比。

            Map<String, String> stringMap = IdeaApi.getSign();
            stringMap.put("pageNum", 0 + "");
            IdeaApi.getRequestLogin(stringMap);
            IdeaApi.getApiService()
                    .getBooksList(stringMap)
                    .compose(this.<BasicResponse<BooksListBean>>bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver<BasicResponse<BooksListBean>>(MineManualActivity.this, true) {
                        @Override
                        public void onSuccess(BasicResponse<BooksListBean> response) {
                            //KVurl = http://ojd06y9cv.bkt.clouddn.com/4969f7828af22e1ec8d30ecc9e7d2c22.png?/0/w/1280/h/960
                            //pdfUrl = http://ojd06y9cv.bkt.clouddn.com/619820641c5890217b99e5cc968e526c.pdf
                            String substring = "";
                            mPdfItemList.clear();

                            if (response != null) {
                                for (int i = 0; i < response.getData().getResultList().size(); i++) {
                                    String pdfUrl = response.getData().getResultList().get(i).getPdfUrl();
                                    mDownLoadKvUrl = response.getData().getResultList().get(i).getKvUrl();

                                    if (pdfUrl != null && !TextUtils.isEmpty(pdfUrl)) {

                                        substring = pdfUrl.substring(pdfUrl.lastIndexOf("/") + 1);

                                        mPdfTotalPath = IConstants.mRootPath + "/" + substring;

                                        if (listFileName.contains(substring)) {
                                            //添加到bean里面
                                            mUserPdfItemBean.setBookKVUrl(mDownLoadKvUrl);
                                            mUserPdfItemBean.setPathUrl(mPdfTotalPath);
                                            mPdfItemList.add(mUserPdfItemBean);
                                        }
                                    }
                                    //之后添加到bean放到条目展示
                                }

                                if (mMyBrochureAdapter6 == null) {
                                    mMyBrochureAdapter6 = new MyBrochureAdapter6(mPdfItemList);
                                    rvMineManualMyBrochure.setAdapter(mMyBrochureAdapter6);
                                } else {
                                    mMyBrochureAdapter6.notifyDataSetChanged();
                                }
                                mMyBrochureAdapter6.setOnItemChildClickListener(MineManualActivity.this);
                            }
                            listFileName.clear();
                            mLocalTempPdfFileName.clear();
                        }
                    });
        }
    }

    @Override
    protected void initListener() {
        srlMineManual.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 0;
                initData();
                srlMineManual.setRefreshing(false);
            }
        });
        //        mMyBrochureAdapter.setOnLoadMoreListener(MineManualActivity.this, rvMineManualMyBrochure); //加载更多
    }

    @Override
    protected void initData() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("pageNum", pageNum + "");
//        stringMap.put("pageSize", 3 + "");

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getBooksList(stringMap)
                .compose(MineManualActivity.this.<BasicResponse<BooksListBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<BooksListBean>>(MineManualActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<BooksListBean> response) {
                        if (response != null) {
                            if (pageNum == 0) {
                                mBooksListBean.clear();
                                mBooksListBean.addAll(response.getData().getResultList());
                                if (mBooksListBean.size() > 0) {
                                    pageNum += 1;
                                    mMineManualAdapter = new BrochureListAdapter(mBooksListBean);
                                    rvMineManualBrochureList.setAdapter(mMineManualAdapter);

                                    mMineManualAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                        @Override
                                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                            mCurPosition = position;
                                            //判断是否这个pdf文件已存在
                                            mPdfTotalPath = IConstants.mRootPath+ "/" +mBooksListBean.get(position).getPdfUrl().substring(mBooksListBean.get(position).getPdfUrl().lastIndexOf("/") + 1);
                                            if (FileUtils.isFileExist(mPdfTotalPath)) {
                                                startActivity(new Intent(MineManualActivity.this, PDFActivity.class).putExtra("PdfUrl", mBooksListBean.get(position).getPdfUrl()));
                                            } else {//没找到就去下载
                                                mDownLoadKvUrl = mBooksListBean.get(mCurPosition).getKvUrl();
                                                // TODO: 2018/2/11 ,还需要考虑到断点续传的功能,若是客户在下载的过程中退出应用，下次在进来的时候，PDF虽然有了，但是不能显示
                                                String pdfUrl = mBooksListBean.get(position).getPdfUrl();
                                                if (pdfUrl != null && !TextUtils.isEmpty(pdfUrl)) {
                                                    Intent intent = new Intent(MineManualActivity.this, DownLoaderPDFActivity.class);
                                                    intent.putExtra("PdfUrl", pdfUrl);
                                                    intent.putExtra("mPdfTotalPath", mPdfTotalPath);
                                                    intent.putExtra("mDownLoadKvUrl", mDownLoadKvUrl);
                                                    startActivity(intent);
                                                    return;
                                                } else {
                                                    String videoUrl = mBooksListBean.get(position).getVideoUrl();
                                                    if (videoUrl != null && !TextUtils.isEmpty(videoUrl)) {
                                                        startActivity(new Intent(MineManualActivity.this, WebViewActivity.class).putExtra("h5url", videoUrl).putExtra("flag", 2));
                                                    }
                                                }
                                            }
                                        }
                                    });
                                } else {
                                    mMineManualAdapter.loadMoreEnd();
                                }
                            } else {
                                if (response.getData().getResultList().size() > 0) {
                                    pageNum += 1;
                                    mBooksListBean.addAll(response.getData().getResultList());
                                    mMineManualAdapter.addData(response.getData().getResultList());
                                    mMineManualAdapter.loadMoreComplete(); //完成本次
                                } else {
                                    mMineManualAdapter.loadMoreEnd(); //完成所有加载
                                }
                            }
                        } else {
                            mMineManualAdapter.loadMoreEnd();
                        }
                    }
                });
    }

    @Override
    public void onLoadMoreRequested() {
//        mMineManualAdapter.loadMoreEnd(false);
//        mMyBrochureAdapter6.loadMoreEnd(false);
        initData();
    }

    private void saveUserPdfData(PdfBean pdfBean) {
        Gson gson = new Gson();
        String jsonStr=gson.toJson(pdfBean); //将List转换成Json
        SPUtil.put(App.getContext(),IConstants.USER_PDF_DATA,jsonStr);
    }

    private void createUserPdfData() {
        mUserPdfItemBean.setBookKVUrl(mDownLoadKvUrl);
        mUserPdfItemBean.setPathUrl(mPdfTotalPath);

        mUserNameBean = new PdfBean.UserNameBean();
        mUserNameBean.setUserName(mUserName);

        mPdfItemList = new ArrayList<>();
        mPdfItemList.add(mUserPdfItemBean);
        mUserNameBean.setPdfItemList(mPdfItemList);
        mUserNameBeanList.add(mUserNameBean);
        mPdfBean.setList(mUserNameBeanList);

        saveUserPdfData(mPdfBean);

        if (mMyBrochureAdapter6 == null) {
            mMyBrochureAdapter6 = new MyBrochureAdapter6(mPdfItemList);
            rvMineManualMyBrochure.setAdapter(mMyBrochureAdapter6);
            mMyBrochureAdapter6.setOnItemChildClickListener(this);
        } else {
            mMyBrochureAdapter6.addData(mUserPdfItemBean);
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.iv_my_brochure_adapter_remove_item_img:
                if (FileUtils.isFileExist(mPdfItemList.get(position).getPathUrl())) {
                    startActivity(new Intent(MineManualActivity.this, PDFActivity.class).putExtra("PdfUrl", mPdfItemList.get(position).getPathUrl()));
                }
                break;
            case R.id.iv_my_brochure_adapter_remove_item://删除指定pdf文件
                File file = new File(mPdfItemList.get(position).getPathUrl());
                if (!file.exists()) {
                    file.mkdirs();
                }

                file.delete();

                mMyBrochureAdapter6.remove(position);
                break;
            default:
                break;
        }
    }
}
