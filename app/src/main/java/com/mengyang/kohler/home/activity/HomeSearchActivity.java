package com.mengyang.kohler.home.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.HomeSearchAdapter;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.AllSearchBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 搜索页
 */

public class HomeSearchActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.tv_home_search_top)
    TopView tvHomeSearchTop;
    @BindView(R.id.iv_home_search_remove_search)
    ImageView ivHomeSearchRemoveSearch;
    @BindView(R.id.et_home_search_activity)
    EditText etHomeSearchActivity;
    @BindView(R.id.iv_home_search)
    ImageView ivHomeSearch;
    @BindView(R.id.rv_home_search)
    RecyclerView rvHomeSearch;
    private HomeSearchAdapter mHomeSearchAdapter;
    private List<AllSearchBean> mAllSearchBean;
    private int pageNum = 0; //请求页数

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvHomeSearchTop);
        GridLayoutManager layoutManagerActivity = new GridLayoutManager(App.getContext(), 2);
        rvHomeSearch.setLayoutManager(layoutManagerActivity);
        rvHomeSearch.addItemDecoration(new GridSpacingItemDecoration(2, 15, false));
        rvHomeSearch.setHasFixedSize(true);
        rvHomeSearch.setItemAnimator(new DefaultItemAnimator());
        etHomeSearchActivity.setText(getIntent().getStringExtra("etHomeSearch"));
        mAllSearchBean = new ArrayList<>();
        mHomeSearchAdapter = new HomeSearchAdapter(mAllSearchBean);
        rvHomeSearch.setAdapter(mHomeSearchAdapter);
    }

    @Override
    protected void initListener() {
        mHomeSearchAdapter.setOnLoadMoreListener(HomeSearchActivity.this); //加载更多
    }

    @Override
    protected void initData() {
        Map<String, String> stringMap = IdeaApi.getSign();
//        stringMap.put("pageNum", pageNum + "");
//        stringMap.put("pageSize", 10 + "");
        stringMap.put("queryStr", etHomeSearchActivity.getText().toString().trim());


        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getAllSearch(stringMap)
                .compose(HomeSearchActivity.this.<BasicResponse<List<AllSearchBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<AllSearchBean>>>(HomeSearchActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<List<AllSearchBean>> response) {
                        if (response != null) {
                            if (pageNum == 0) {
                                mAllSearchBean.clear();
                                mAllSearchBean.addAll(response.getData());
                                if (mAllSearchBean.size() > 0) {
                                    pageNum += 1;
                                    mHomeSearchAdapter = new HomeSearchAdapter(mAllSearchBean);
                                    mHomeSearchAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM); //动画
                                    mHomeSearchAdapter.isFirstOnly(false); //第一次
                                    rvHomeSearch.setAdapter(mHomeSearchAdapter);
                                } else {
                                    mHomeSearchAdapter.loadMoreEnd();
                                }
                            } else {
                                if (response.getData().size() > 0) {
                                    pageNum += 1;
                                    mAllSearchBean.addAll(response.getData());
                                    mHomeSearchAdapter.addData(response.getData());
                                    mHomeSearchAdapter.loadMoreComplete(); //完成本次
                                } else {
                                    mHomeSearchAdapter.loadMoreEnd(); //完成所有加载
                                }
                            }
                        } else {
                            mHomeSearchAdapter.loadMoreEnd();
                        }
                    }
                });
    }

    @Override
    public void onLoadMoreRequested() {
        initData();
    }

    @OnClick({R.id.iv_home_search_remove_search, R.id.iv_home_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_home_search_remove_search:
                etHomeSearchActivity.setText("");
                break;
            case R.id.iv_home_search:
                pageNum = 0;
                initData();
                break;
        }
    }
}
