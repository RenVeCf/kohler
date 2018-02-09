package com.mengyang.kohler.home.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.DatabaseUtils;
import com.mengyang.kohler.common.utils.FileUtils;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.BrochureListAdapter;
import com.mengyang.kohler.home.adapter.MyBrochureAdapter;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.BooksBean;
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
    private MyBrochureAdapter mMyBrochureAdapter;
    private BooksBean mDeletePDF;
    private int pageNum = 0; //请求页数

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_manual;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvMineManualTop);

        //必须先初始化SQLite
        DatabaseUtils.initHelper(MineManualActivity.this, "books.db");

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
        List<BooksBean> list = DatabaseUtils.getHelper().queryAll(BooksBean.class);
        mMyBrochureAdapter = new MyBrochureAdapter(list);
        rvMineManualMyBrochure.setAdapter(mMyBrochureAdapter);
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
                                            if (FileUtils.isFileExist(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + mBooksListBean.get(position).getPdfUrl().substring(mBooksListBean.get(position).getPdfUrl().lastIndexOf("/") + 1)))
                                                startActivity(new Intent(MineManualActivity.this, PDFActivity.class).putExtra("PdfUrl", mBooksListBean.get(position).getPdfUrl()));
                                            else {//没找到就去下载

                                                //图下载的pdf封面url放入SQLite
                                                mDeletePDF = new BooksBean(mBooksListBean.get(position).getKvUrl());
                                                //保存
                                                DatabaseUtils.getHelper().save(mDeletePDF);
                                                startActivity(new Intent(MineManualActivity.this, DownLoaderPDFActivity.class).putExtra("PdfUrl", mBooksListBean.get(position).getPdfUrl()));

                                                //所有下载好的PDF集合
                                                List<BooksBean> list = DatabaseUtils.getHelper().queryAll(BooksBean.class);
                                                mMyBrochureAdapter = new MyBrochureAdapter(list);
                                                rvMineManualMyBrochure.setAdapter(mMyBrochureAdapter);
                                            }
                                        }
                                    });
                                    //                                    mMyBrochureAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                    //                                        @Override
                                    //                                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                    //                                            //删除指定pdf文件
                                    //
                                    //                                        }
                                    //                                    });
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
}
