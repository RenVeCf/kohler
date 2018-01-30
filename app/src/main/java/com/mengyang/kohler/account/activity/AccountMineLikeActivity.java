package com.mengyang.kohler.account.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.account.adapter.AccountMineLikeAdapter;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;

/**
 * 我的账户——我的收藏
 */

public class AccountMineLikeActivity extends BaseActivity {

    @BindView(R.id.tv_account_mine_like_top)
    TopView tvAccountMineLikeTop;
    @BindView(R.id.rv_account_mine_like)
    RecyclerView rvAccountMineLike;
    private AccountMineLikeAdapter mAccountMineLikeAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_mine_like;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvAccountMineLikeTop);
        GridLayoutManager layoutManagerActivity = new GridLayoutManager(App.getContext(), 2);
        rvAccountMineLike.setLayoutManager(layoutManagerActivity);
        rvAccountMineLike.addItemDecoration(new GridSpacingItemDecoration(2, 15, false));
        rvAccountMineLike.setNestedScrollingEnabled(true);
        rvAccountMineLike.setHasFixedSize(true);
        rvAccountMineLike.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
