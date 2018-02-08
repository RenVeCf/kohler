package com.mengyang.kohler.account.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.account.adapter.AccountMineReservationQueryAdapter;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.ReservationQueryBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的账户——预约查询
 */

public class AccountMineReservationQueryActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.tv_account_reservation_query_top)
    TopView tvAccountReservationQueryTop;
    @BindView(R.id.rv_account_reservation_query)
    RecyclerView rvAccountReservationQuery;
    @BindView(R.id.srl_account_reservation_query)
    SwipeRefreshLayout srlAccountReservationQuery;
    private AccountMineReservationQueryAdapter mAccountMineReservationQueryAdapter;
    private List<ReservationQueryBean.ResultListBean> mReservationQueryBean;
    private int pageNum = 0; //请求页数

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_mine_reservation_query;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvAccountReservationQueryTop);

        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        rvAccountReservationQuery.setLayoutManager(layoutManager);
        rvAccountReservationQuery.addItemDecoration(new SpacesItemDecoration(16));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvAccountReservationQuery.setHasFixedSize(true);
        rvAccountReservationQuery.setItemAnimator(new DefaultItemAnimator());

        mReservationQueryBean = new ArrayList<>();
        mAccountMineReservationQueryAdapter = new AccountMineReservationQueryAdapter(mReservationQueryBean);
        rvAccountReservationQuery.setAdapter(mAccountMineReservationQueryAdapter);
    }

    @Override
    protected void initListener() {
        srlAccountReservationQuery.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 0;
                initData();
                srlAccountReservationQuery.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("pageNum", pageNum + "");
        stringMap.put("pageSize", 10 + "");

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getUserReserveMsg(stringMap)
                .compose(AccountMineReservationQueryActivity.this.<BasicResponse<ReservationQueryBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<ReservationQueryBean>>(AccountMineReservationQueryActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<ReservationQueryBean> response) {
                        if (response != null) {
                            if (pageNum == 0) {
                                mReservationQueryBean.clear();
                                mReservationQueryBean.addAll(response.getData().getResultList());
                                if (mReservationQueryBean.size() > 0) {
                                    pageNum += 1;
                                    mAccountMineReservationQueryAdapter = new AccountMineReservationQueryAdapter(mReservationQueryBean);
                                    mAccountMineReservationQueryAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM); //动画
                                    mAccountMineReservationQueryAdapter.isFirstOnly(false); //第一次
                                    rvAccountReservationQuery.setAdapter(mAccountMineReservationQueryAdapter);
                                    mAccountMineReservationQueryAdapter.setOnLoadMoreListener(AccountMineReservationQueryActivity.this, rvAccountReservationQuery); //加载更多
                                } else {
                                    mAccountMineReservationQueryAdapter.loadMoreEnd();
                                }
                            } else {
                                if (response.getData().getResultList().size() > 0) {
                                    pageNum += 1;
                                    mReservationQueryBean.addAll(response.getData().getResultList());
                                    mAccountMineReservationQueryAdapter.addData(response.getData().getResultList());
                                    mAccountMineReservationQueryAdapter.loadMoreComplete(); //完成本次
                                } else {
                                    mAccountMineReservationQueryAdapter.loadMoreEnd(); //完成所有加载
                                }
                            }
                        } else {
                            mAccountMineReservationQueryAdapter.loadMoreEnd();
                        }
                    }
                });
    }

    @Override
    public void onLoadMoreRequested() {
        initData();
    }
}