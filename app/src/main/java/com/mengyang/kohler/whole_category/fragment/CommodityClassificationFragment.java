package com.mengyang.kohler.whole_category.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.CommodityClassificationFragmentBean;
import com.mengyang.kohler.whole_category.activity.CommodityDetailsActivity;
import com.mengyang.kohler.whole_category.adapter.CommodityClassificationAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 商品分类/产品清单
 */
public class CommodityClassificationFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_commodity_classification)
    RecyclerView rvCommodityClassification;
    @BindView(R.id.srl_commodity_classification)
    SwipeRefreshLayout srlCommodityClassification;
    private CommodityClassificationAdapter mCommodityClassificationAdapter;
    private List<CommodityClassificationFragmentBean.ResultListBean> mCommodityClassificationFragmentBean;
    private int pageNum = 0; //请求页数
    private String mCmsId;

    public static CommodityClassificationFragment newInstance(String cmsId) {
        CommodityClassificationFragment frag = new CommodityClassificationFragment();
        Bundle args = new Bundle();
        args.putString("cmsId", cmsId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_commodity_classification;
    }

    @Override
    protected void initValues() {
        mCmsId = getArguments().getString("cmsId");
        GridLayoutManager layoutManagerActivity = new GridLayoutManager(App.getContext(), 2);
        rvCommodityClassification.setLayoutManager(layoutManagerActivity);
        rvCommodityClassification.addItemDecoration(new GridSpacingItemDecoration(2, 15, false));
        rvCommodityClassification.setHasFixedSize(true);
        rvCommodityClassification.setItemAnimator(new DefaultItemAnimator());

        mCommodityClassificationFragmentBean = new ArrayList<>();
        mCommodityClassificationAdapter = new CommodityClassificationAdapter(mCommodityClassificationFragmentBean);
        rvCommodityClassification.setAdapter(mCommodityClassificationAdapter);
    }

    @Override
    protected void initListener() {
        srlCommodityClassification.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 0;
                initData();
                srlCommodityClassification.setRefreshing(false);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
        } else {
            pageNum = 0;
        }
    }

    @Override
    protected void initData() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("category", mCmsId);
        stringMap.put("pageNum", pageNum + "");
        stringMap.put("pageSize", 10 + "");

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getCommodityClassificationBody(stringMap)
                .compose(this.<BasicResponse<CommodityClassificationFragmentBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<CommodityClassificationFragmentBean>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BasicResponse<CommodityClassificationFragmentBean> response) {

                        if (response.getData().getResultList().size() > 0) {
                            if (pageNum == 0) {
                                mCommodityClassificationFragmentBean.clear();
                                mCommodityClassificationFragmentBean.addAll(response.getData().getResultList());
                                if (response.getData().getTotalSize() > 0) {
                                    pageNum += 1;
                                    mCommodityClassificationAdapter = new CommodityClassificationAdapter(mCommodityClassificationFragmentBean);
                                    rvCommodityClassification.setAdapter(mCommodityClassificationAdapter);
                                    mCommodityClassificationAdapter.setOnLoadMoreListener(CommodityClassificationFragment.this, rvCommodityClassification); //加载更多
                                } else {
                                    mCommodityClassificationAdapter.loadMoreEnd();
                                }
                            } else {
                                if (response.getData().getResultList().size() > 0) {
                                    pageNum += 1;
                                    mCommodityClassificationFragmentBean.addAll(response.getData().getResultList());
                                    mCommodityClassificationAdapter.addData(response.getData().getResultList());
                                    mCommodityClassificationAdapter.loadMoreComplete(); //完成本次
                                } else {
                                    mCommodityClassificationAdapter.loadMoreEnd(); //完成所有加载
                                }
                            }
                            mCommodityClassificationAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                @Override
                                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                    startActivity(new Intent(App.getContext(), CommodityDetailsActivity.class).putExtra("CommodityDetails", (Serializable) mCommodityClassificationFragmentBean.get(position)));
                                }
                            });
                        } else {
                            mCommodityClassificationAdapter.loadMoreEnd(); //完成所有加载
                            mCommodityClassificationAdapter.setEmptyView(R.layout.null_commodity_classification_fragment, rvCommodityClassification);
                        }
                    }
                });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //        mCmsId = ((CommodityClassificationActivity) activity).getCmsId();
    }

    @Override
    public void onLoadMoreRequested() {
        initData();
    }
}
