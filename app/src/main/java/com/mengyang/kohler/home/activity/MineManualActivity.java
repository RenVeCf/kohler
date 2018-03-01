package com.mengyang.kohler.home.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.activity.CustomerServiceActivity;
import com.mengyang.kohler.common.activity.WebViewActivity;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.net.IdeaApi;
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
 * 我的手册
 */

public class MineManualActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener {
//    String mRootPath = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+ "kohlerPdf";
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
    private List<PdfBean.UserNameBean.UserPdfItemBean> mPdfItemList;
    private MyBrochureAdapter6 mMyBrochureAdapter6;
    private List<String> mNameList= new ArrayList<>();
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



        String pefData2 = (String) SPUtil.get(App.getContext(), IConstants.USER_PDF_DATA, "");

        if (!TextUtils.isEmpty(pefData2) && pefData2.length() >5) {//pefData2.length() >5 s 临时定义的
            Gson gson = new Gson();
            mPdfBean = gson.fromJson(pefData2, new TypeToken<PdfBean>() {}.getType());
            for (int i = 0; i < mPdfBean.getList().size(); i++) {
                if (mPdfBean.getList().get(i).getUserName().equals((String) SPUtil.get(App.getContext(), IConstants.USER_NIKE_NAME, ""))) {
                    mPdfItemList = new ArrayList<>();
                    mPdfItemList = mPdfBean.getList().get(i).getPdfItemList();

                    // TODO: 2018/2/23 ,有可能用户手动将文件删除了，所以这边需要在进行判断文件是否存在
                    boolean exists = false;
                    for (int i1 = 0; i1 < mPdfItemList.size(); i1++) {
                        String bookPathUrl = mPdfItemList.get(i1).getPathUrl();
                        File file = new File(bookPathUrl);
                        exists = file.exists();
                        if (!exists) {
                            mPdfItemList.remove(i1);
                        }

                    }



                    if (mMyBrochureAdapter6 == null) {
                        mMyBrochureAdapter6 = new MyBrochureAdapter6(mPdfItemList);
                        rvMineManualMyBrochure.setAdapter(mMyBrochureAdapter6);
                        mMyBrochureAdapter6.setOnItemChildClickListener(this);
                    } else {
                        mMyBrochureAdapter6.addData(mUserPdfItemBean);
                    }
                }
            }
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
//                                    mMineManualAdapter.setOnLoadMoreListener(MineManualActivity.this, rvMineManualMyBrochure); //加载更多

/*
                                    mMineManualAdapter.setLoadMoreView(new LoadMoreView() {
                                        @Override
                                        public int getLayoutId() {
                                            return R.layout.load_more_null_layout;
                                        }

                                        */
/**
                                         * 如果返回true，数据全部加载完毕后会隐藏加载更多
                                         * 如果返回false，数据全部加载完毕后会显示getLoadEndViewId()布局
                                         *//*

                                        @Override public boolean isLoadEndGone() {
                                            return true;
                                        }

                                        @Override
                                        protected int getLoadingViewId() {
                                            return R.id.load_more_loading_view;
                                        }

                                        @Override
                                        protected int getLoadFailViewId() {
                                            return R.id.load_more_load_fail_view;
                                        }

                                        @Override
                                        protected int getLoadEndViewId() {
                                            return 0;
                                        }
                                    });
*/

                                    mMineManualAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                        @Override
                                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                            mCurPosition = position;
                                            //判断是否这个pdf文件已存在
                                            mPdfTotalPath = mRootPath+ "/" +mBooksListBean.get(position).getPdfUrl().substring(mBooksListBean.get(position).getPdfUrl().lastIndexOf("/") + 1);
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
                                                    startActivityForResult(intent, IConstants.REQUEST_CODE_DOWN_LOAD);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IConstants.REQUEST_CODE_DOWN_LOAD && resultCode == RESULT_OK) {

            // TODO: 2018/2/12 ,本地做存储的缺点，若是用户修改了名字，那么他的数据就需要重新的存储

            if (mPdfBean.getList() != null && mPdfBean.getList().size() > 0) {

                for (int i = 0; i < mPdfBean.getList().size(); i++) {
                    mNameList.add(mPdfBean.getList().get(i).getUserName());
                }

                //有这个用户
                if (mNameList.contains((String) SPUtil.get(App.getContext(), IConstants.USER_NIKE_NAME, ""))) {
                    for (int i = 0; i < mPdfBean.getList().size(); i++) {
                        if (mPdfBean.getList().get(i).getUserName().equals((String) SPUtil.get(App.getContext(), IConstants.USER_NIKE_NAME, ""))) {

                            for (int j = 0; j < mPdfBean.getList().get(i).getPdfItemList().size(); j++) {
                                mPdfItemList.add(mPdfBean.getList().get(i).getPdfItemList().get(j));
                            }


//                            mUserPdfItemBean = new PdfBean.UserNameBean.UserPdfItemBean();
//                            mUserPdfItemBean.setBookKVUrl(mDownLoadKvUrl);
//                            mUserPdfItemBean.setPathUrl(mPdfTotalPath);
//
//                            mPdfBean.getList().get(i).getPdfItemList().add(mUserPdfItemBean);
//                            saveUserPdfData(mPdfBean);


                            mMyBrochureAdapter6.notifyDataSetChanged();
                        }
                    }

                } else {
                    //没有该用户,创建用户信息
//                    createUserPdfData();
                }

            } else {
                //没有该用户,创建用户信息
//                createUserPdfData();
            }
        }
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

                for (int i = 0; i < mPdfBean.getList().size(); i++) {
                    // TODO: 2018/2/13 ,还没有找到为什么会是空的
                    if (mPdfBean.getList().get(i).getPdfItemList() != null && mPdfBean.getList().get(i).getPdfItemList().size() > 0) {

                        if (mPdfBean.getList().get(i).getUserName().equals(mUserName)) {
                            mPdfBean.getList().get(i).getPdfItemList().remove(position);
                        }
                    }
                }

                saveUserPdfData(mPdfBean);
                break;
            default:
                break;
        }
    }
}
