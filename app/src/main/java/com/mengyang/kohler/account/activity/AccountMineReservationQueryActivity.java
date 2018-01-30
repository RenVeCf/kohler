package com.mengyang.kohler.account.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.account.adapter.AccountMineReservationQueryAdapter;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;

/**
 * 我的账户——预约查询
 */

public class AccountMineReservationQueryActivity extends BaseActivity {

    @BindView(R.id.tv_account_reservation_query_top)
    TopView tvAccountReservationQueryTop;
    @BindView(R.id.rv_account_reservation_query)
    RecyclerView rvAccountReservationQuery;
    private AccountMineReservationQueryAdapter mAccountMineReservationQueryAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_mine_reservation_query;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvAccountReservationQueryTop);

        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        rvAccountReservationQuery.setLayoutManager(layoutManager);
        rvAccountReservationQuery.addItemDecoration(new SpacesItemDecoration(16));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvAccountReservationQuery.setHasFixedSize(true);
        rvAccountReservationQuery.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}