package com.mengyang.kohler.home.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.DatabaseUtils;
import com.mengyang.kohler.common.utils.FileUtils;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.BrochureListAdapter;
import com.mengyang.kohler.home.adapter.MyBrochureAdapter;
import com.mengyang.kohler.home.adapter.MyBrochureAdapter2;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.BooksBean;
import com.mengyang.kohler.module.PdfBean;
import com.mengyang.kohler.module.bean.BooksListBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的手册
 */

public class MineManualActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {
    String mRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();



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
    private MyBrochureAdapter2 mMyBrochureAdapter;

    private List<PdfBean> mPdfBeanList = new ArrayList<>();
//    private BooksBean mDeletePDF;
    private int pageNum = 0; //请求页数
    private String mPdfTotalPath;
    private List<PdfBean.userPdfListBean> mUserPdfListBeans;
    private boolean mIsDownLoadOver;

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

        //所有下载好的PDF集合
//        List<BooksBean> list = DatabaseUtils.getHelper().queryAll(BooksBean.class);




        String pefData = (String) SPUtil.get(App.getContext(), IConstants.USER_PDF_DATA, "");
        if (!TextUtils.isEmpty(pefData)) {
            Gson gson = new Gson();
            List<PdfBean> pefDataList = gson.fromJson(pefData, new TypeToken<List<PdfBean>>() {}.getType());

            for (int i = 0; i < pefDataList.size(); i++) {
                if (pefDataList.get(i).equals((String) SPUtil.get(App.getContext(), IConstants.USER_NIKE_NAME, ""))) {
                    mUserPdfListBeans = pefDataList.get(i).getList();
                    mMyBrochureAdapter = new MyBrochureAdapter2(mUserPdfListBeans);
                    rvMineManualMyBrochure.setAdapter(mMyBrochureAdapter);
                }
            }
        }



  /*      mMyBrochureAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //删除指定pdf文件
                switch (view.getId()) {
                    case R.id.iv_my_brochure_adapter_remove_item_img:
                        if (FileUtils.isFileExist(mUserPdfListBeans.get(position).getPathUrl())) {
                            startActivity(new Intent(MineManualActivity.this, PDFActivity.class).putExtra("PdfUrl", mUserPdfListBeans.get(position).getBookKVUrl()));
                        }
                        break;
                    case R.id.iv_my_brochure_adapter_remove_item:
                        mUserPdfListBeans.remove(position);
                        Gson gson = new Gson();
                        String jsonStr =gson.toJson(mUserPdfListBeans);
                        SPUtil.put(App.getContext(),IConstants.USER_PDF_DATA,jsonStr);

                        mMyBrochureAdapter.remove(position);
                        mMineManualAdapter.remove(position);
                        break;
                    default:
                        break;
                }
            }
        });*/


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
        stringMap.put("pageSize", 3 + "");

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
                                    mMineManualAdapter.setOnLoadMoreListener(MineManualActivity.this, rvMineManualMyBrochure); //加载更多

                                    mMineManualAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                        @Override
                                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                            //判断是否这个pdf文件已存在
                                            mPdfTotalPath = mRootPath+ "/" +mBooksListBean.get(position).getPdfUrl().substring(mBooksListBean.get(position).getPdfUrl().lastIndexOf("/") + 1);
                                            if (FileUtils.isFileExist(mPdfTotalPath)) {
                                                startActivity(new Intent(MineManualActivity.this, PDFActivity.class).putExtra("PdfUrl", mBooksListBean.get(position).getPdfUrl()));
                                            } else {//没找到就去下载

                                                PdfBean pdfBean = new PdfBean();
                                                pdfBean.setUserName((String) SPUtil.get(App.getContext(), IConstants.USER_NIKE_NAME, ""));
                                                List<PdfBean.userPdfListBean> list = pdfBean.getList();

                                                PdfBean.userPdfListBean userPdfBean = new PdfBean.userPdfListBean();
                                                userPdfBean.setBookKVUrl(mBooksListBean.get(position).getKvUrl());
                                                userPdfBean.setPathUrl(mPdfTotalPath);
                                                list.add(userPdfBean);

                                                mPdfBeanList.add(pdfBean);
                                                Gson gson = new Gson();
                                                String jsonStr=gson.toJson(mPdfBeanList); //将List转换成Json
                                                SPUtil.put(App.getContext(),IConstants.USER_PDF_DATA,jsonStr);

                                                Intent intent = new Intent(MineManualActivity.this, DownLoaderPDFActivity.class).putExtra("PdfUrl", mBooksListBean.get(position).getPdfUrl());
                                                startActivityForResult(intent, IConstants.REQUEST_CODE_DOWN_LOAD);
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
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IConstants.REQUEST_CODE_DOWN_LOAD && resultCode == RESULT_OK) {
            mIsDownLoadOver = true;
        }
    }
}
