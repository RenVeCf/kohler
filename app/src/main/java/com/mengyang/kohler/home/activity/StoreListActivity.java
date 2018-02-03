package com.mengyang.kohler.home.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.StoreListAdapter;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.StoreListBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 门店列表
 */

public class StoreListActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.tv_store_list_top)
    TopView tvStoreListTop;
    @BindView(R.id.rv_store_list)
    RecyclerView rvStoreList;
    @BindView(R.id.srl_store_list)
    SwipeRefreshLayout srlStoreList;
    @BindView(R.id.bt_store_list)
    Button btStoreList;
    private StoreListAdapter mStoreListAdapter;
    private List<StoreListBean.ResultListBean> mStoreListBean;
    private int pageNum = 0; //请求页数
    private double latitude; //纬度
    private double longitude; //经度

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_list;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvStoreListTop);
        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        rvStoreList.setLayoutManager(layoutManager);
        rvStoreList.addItemDecoration(new SpacesItemDecoration(16));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvStoreList.setHasFixedSize(true);
        rvStoreList.setItemAnimator(new DefaultItemAnimator());
        mStoreListBean = new ArrayList<>();
        mStoreListAdapter = new StoreListAdapter(mStoreListBean);
        rvStoreList.setAdapter(mStoreListAdapter);
        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);
    }

    @Override
    protected void initListener() {
        srlStoreList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 0;
                initData();
                srlStoreList.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData() {
        Map<String, String> stringMap = IdeaApi.getSign();
        //接口有问题
        stringMap.put("pageNum", 0 + "");
        stringMap.put("pageSize", 10 + "");
        stringMap.put("latitude", "30.628679");
        stringMap.put("longitude", "114.289438");

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getStoreList(stringMap)
                .compose(StoreListActivity.this.<BasicResponse<StoreListBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<StoreListBean>>(StoreListActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<StoreListBean> response) {
                        if (response != null) {
                            if (pageNum == 0) {
                                mStoreListBean.clear();
                                mStoreListBean.addAll(response.getData().getResultList());
                                if (mStoreListBean.size() > 0) {
                                    pageNum = pageNum + 1;
                                    mStoreListAdapter = new StoreListAdapter(mStoreListBean);
                                    mStoreListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM); //动画
//                                    mStoreListAdapter.isFirstOnly(false); //第一次
                                    rvStoreList.setAdapter(mStoreListAdapter);
                                    mStoreListAdapter.setOnLoadMoreListener(StoreListActivity.this); //加载更多
                                } else {
                                    mStoreListAdapter.loadMoreEnd();
                                }
                            } else {
                                if (response.getData().getResultList().size() > 0) {
                                    pageNum = pageNum + 1;
                                    mStoreListBean.addAll(response.getData().getResultList());
                                    mStoreListAdapter.addData(response.getData().getResultList());
                                    mStoreListAdapter.loadMoreComplete(); //完成本次
                                } else {
                                    mStoreListAdapter.loadMoreEnd(); //完成所有加载
                                }
                            }
                        } else {
                            mStoreListAdapter.loadMoreEnd();
                        }
                    }
                });
    }

    @Override
    public void onLoadMoreRequested() {
        initData();
    }

    @OnClick(R.id.bt_store_list)
    public void onViewClicked() {
        startActivity(new Intent(this, StoreMapActivity.class));
        finish();
    }
}
