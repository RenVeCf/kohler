package com.mengyang.kohler.account.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的账户——预约查询
 */

public class AccountMineReservationQueryActivity extends BaseActivity {

    @BindView(R.id.tv_account_reservation_query_top)
    TopView tvAccountReservationQueryTop;
    @BindView(R.id.rv_account_reservation_query)
    RecyclerView rvAccountReservationQuery;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_mine_reservation_query;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvAccountReservationQueryTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
