package com.mengyang.kohler.account.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

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
import com.mengyang.kohler.whole_category.activity.CommodityDetailsActivity;

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
    private PopupWindow mAccountMineLikePopupWindow;
    private TextView tvPopupWindowAccountMineLike;
    private Button btPopupWindowAccountMineLike;
    private View mPopImgLayout;
    private int pageNum = 0; //请求页数
    private String mId = "";

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

        mAccountMineLikePopupWindow = new PopupWindow(App.getContext());
        mAccountMineLikePopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mAccountMineLikePopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater_img = LayoutInflater.from(App.getContext());
        mPopImgLayout = inflater_img.inflate(R.layout.popup_window_account_mine_like, null);
        mAccountMineLikePopupWindow.setContentView(mPopImgLayout);
        mAccountMineLikePopupWindow.setBackgroundDrawable(new ColorDrawable(0x4c000000));
        mAccountMineLikePopupWindow.setOutsideTouchable(false);
        mAccountMineLikePopupWindow.setFocusable(true);
        tvPopupWindowAccountMineLike = mPopImgLayout.findViewById(R.id.tv_popup_window_account_mine_like);
        btPopupWindowAccountMineLike = mPopImgLayout.findViewById(R.id.bt_popup_window_account_mine_like);
        btPopupWindowAccountMineLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> stringMap = IdeaApi.getSign();
                stringMap.put("id", mId);

                IdeaApi.getRequestLogin(stringMap);
                IdeaApi.getApiService()
                        .getCancelLike(stringMap)
                        .compose(AccountMineLikeActivity.this.<BasicResponse>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultObserver<BasicResponse>(AccountMineLikeActivity.this, true) {
                            @Override
                            public void onSuccess(BasicResponse response) {
                                pageNum = 0;
                                initData();
                                mAccountMineLikePopupWindow.dismiss();
                            }
                        });
            }
        });
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
        Map<String, Object> stringMap = IdeaApi.getSign();
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
                                    rvAccountMineLike.setAdapter(mAccountMineLikeAdapter);
                                    mAccountMineLikeAdapter.setOnLoadMoreListener(AccountMineLikeActivity.this, rvAccountMineLike); //加载更多
                                    mAccountMineLikeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                        @Override
                                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                            switch (view.getId()) {
                                                case R.id.iv_account_mine_like_adapter_item:
                                                    //跳转到该商品详情
                                                    startActivity(new Intent(AccountMineLikeActivity.this, CommodityDetailsActivity.class).putExtra("CommodityDetails_two", likeListBean.get(position).getSkuCode()));
                                                    break;
                                                case R.id.iv_account_mine_like_adapter_remove:
                                                    //取消收藏
                                                    mId = likeListBean.get(position).getId() + "";
                                                    tvPopupWindowAccountMineLike.setText(likeListBean.get(position).getName());
                                                    if (Build.VERSION.SDK_INT == 24) {//android7.0需要单独做适配
                                                        mAccountMineLikePopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, getStatusBarHeight());
                                                    } else {
                                                        mAccountMineLikePopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
                                                    }
                                                    break;
                                            }
                                        }
                                    });
                                } else {
                                    mAccountMineLikeAdapter.notifyDataSetChanged();
                                    rvAccountMineLike.setAdapter(mAccountMineLikeAdapter);
                                    mAccountMineLikeAdapter.loadMoreEnd();
                                }
                            } else {
                                if (response.getData().getResultList().size() > 0) {
                                    pageNum += 1;
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
