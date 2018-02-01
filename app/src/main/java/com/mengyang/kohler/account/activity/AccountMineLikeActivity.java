package com.mengyang.kohler.account.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.account.adapter.AccountMineLikeAdapter;
import com.mengyang.kohler.account.adapter.AccountMineReservationQueryAdapter;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.LikeListBean;

import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的账户——我的收藏
 */

public class AccountMineLikeActivity extends BaseActivity {

    @BindView(R.id.tv_account_mine_like_top)
    TopView tvAccountMineLikeTop;
    @BindView(R.id.rv_account_mine_like)
    RecyclerView rvAccountMineLike;
    private AccountMineLikeAdapter mAccountMineLikeAdapter;
    private LikeListBean likeListBean;

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
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        //有问题（Adapter不确定取哪个字段）
        Map<String, String> stringMap = IdeaApi.getSign();

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getLikeList(stringMap)
                .compose(AccountMineLikeActivity.this.<BasicResponse<LikeListBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<LikeListBean>>(AccountMineLikeActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<LikeListBean> response) {
                        likeListBean = response.getData();
                        mAccountMineLikeAdapter = new AccountMineLikeAdapter(likeListBean);
                        rvAccountMineLike.setAdapter(mAccountMineLikeAdapter);
                    }
                });
    }
}
