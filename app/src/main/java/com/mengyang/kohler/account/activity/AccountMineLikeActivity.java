package com.mengyang.kohler.account.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.account.adapter.AccountMineLikeAdapter;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.LikeListBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的账户——我的收藏
 */

public class AccountMineLikeActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.tv_account_mine_like_top)
    TopView tvAccountMineLikeTop;
    @BindView(R.id.rv_account_mine_like)
    RecyclerView rvAccountMineLike;
    @BindView(R.id.srl_account_mine_like)
    SwipeRefreshLayout srlAccountMineLike;
    private AccountMineLikeAdapter mAccountMineLikeAdapter;
    private List<LikeListBean.ResultListBean> likeListBean;
    private int pageNum = 0; //请求页数

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_mine_like;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvAccountMineLikeTop);
        GridLayoutManager layoutManagerActivity = new GridLayoutManager(App.getContext(), 2);
        rvAccountMineLike.setLayoutManager(layoutManagerActivity);
        rvAccountMineLike.addItemDecoration(new GridSpacingItemDecoration(2, 15, false));
        rvAccountMineLike.setNestedScrollingEnabled(true);
        rvAccountMineLike.setHasFixedSize(true);
        rvAccountMineLike.setItemAnimator(new DefaultItemAnimator());
        likeListBean = new ArrayList<>();
        mAccountMineLikeAdapter = new AccountMineLikeAdapter(likeListBean);
        rvAccountMineLike.setAdapter(mAccountMineLikeAdapter);
    }

    @Override
    protected void initListener() {
        srlAccountMineLike.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 0;
                initData();
                srlAccountMineLike.setRefreshing(false);
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
                .getLikeList(stringMap)
                .compose(AccountMineLikeActivity.this.<BasicResponse<LikeListBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<LikeListBean>>(AccountMineLikeActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<LikeListBean> response) {
                        if (response != null) {
                            if (pageNum == 0) {
                                likeListBean.clear();
                                likeListBean.addAll(response.getData().getResultList());
                                if (likeListBean.size() > 0) {
                                    pageNum += 1;
                                    mAccountMineLikeAdapter = new AccountMineLikeAdapter(likeListBean);
                                    mAccountMineLikeAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM); //动画
                                    mAccountMineLikeAdapter.isFirstOnly(false); //第一次
                                    rvAccountMineLike.setAdapter(mAccountMineLikeAdapter);
                                    mAccountMineLikeAdapter.setOnLoadMoreListener(AccountMineLikeActivity.this, rvAccountMineLike); //加载更多
                                    mAccountMineLikeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                        @Override
                                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                            Map<String, String> stringMap = IdeaApi.getSign();
                                            stringMap.put("id", likeListBean.get(position).getId() + "");

                                            IdeaApi.getRequestLogin(stringMap);
                                            IdeaApi.getApiService()
                                                    .getCancelLike(stringMap)
                                                    .compose(AccountMineLikeActivity.this.<BasicResponse>bindToLifecycle())
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new DefaultObserver<BasicResponse>(AccountMineLikeActivity.this, true) {
                                                        @Override
                                                        public void onSuccess(BasicResponse response) {
                                                            initData();
                                                        }
                                                    });
                                        }
                                    });
                                } else {
                                    mAccountMineLikeAdapter.loadMoreEnd();
                                }
                            } else {
                                if (response.getData().getResultList().size() > 0) {
                                    pageNum += 1;
                                    likeListBean.addAll(response.getData().getResultList());
                                    mAccountMineLikeAdapter.addData(response.getData().getResultList());
                                    mAccountMineLikeAdapter.loadMoreComplete(); //完成本次
                                } else {
                                    mAccountMineLikeAdapter.loadMoreEnd(); //完成所有加载
                                }
                            }
                        } else {
                            mAccountMineLikeAdapter.loadMoreEnd();
                        }
                    }
                });
    }

    @Override
    public void onLoadMoreRequested() {
        initData();
    }
}
