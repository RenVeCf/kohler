package com.mengyang.kohler.home.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.allyes.analytics.AIOAnalytics;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.activity.WebViewActivity;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.WeeklyRadioConcertAdapter;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.WeeklyRadioConcertBean;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 星广会
 */
public class WeeklyRadioConcertActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.tv_home_weekly_radio_concert)
    TopView tvHomeWeeklyRadioConcert;
    @BindView(R.id.rv_weekly_radio_concert)
    RecyclerView rvWeeklyRadioConcert;
    @BindView(R.id.srl_weekly_radio_concert)
    SwipeRefreshLayout srlWeeklyRadioConcert;
    private List<WeeklyRadioConcertBean.ResultListBean> mWeeklyRadioConcertBean;
    private WeeklyRadioConcertAdapter mWeeklyRadioConcertAdapter;
    private int pageNum = 0; //请求页数

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weekly_radio_concert;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvHomeWeeklyRadioConcert);
        MobclickAgent.onEvent(WeeklyRadioConcertActivity.this, "yinyuehuiliebiao");
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        rvWeeklyRadioConcert.setLayoutManager(layoutManager);
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvWeeklyRadioConcert.setHasFixedSize(true);
        rvWeeklyRadioConcert.setItemAnimator(new DefaultItemAnimator());

        mWeeklyRadioConcertBean = new ArrayList<>();
        mWeeklyRadioConcertAdapter = new WeeklyRadioConcertAdapter(mWeeklyRadioConcertBean);
        rvWeeklyRadioConcert.setAdapter(mWeeklyRadioConcertAdapter);
    }

    @Override
    protected void initListener() {
        srlWeeklyRadioConcert.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 0;
                initData();
                srlWeeklyRadioConcert.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData() {
        Map<String, Object> stringMap = IdeaApi.getSign();
        stringMap.put("pageNum", pageNum + "");

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getWeeklyRadioConcert(stringMap)
                .compose(WeeklyRadioConcertActivity.this.<BasicResponse<WeeklyRadioConcertBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<WeeklyRadioConcertBean>>(WeeklyRadioConcertActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<WeeklyRadioConcertBean> response) {
                        if (response.getData().getResultList().size() != 0) {
                            if (pageNum == 0) {
                                mWeeklyRadioConcertBean.clear();
                                mWeeklyRadioConcertBean.addAll(response.getData().getResultList());
                                if (mWeeklyRadioConcertBean.size() > 0) {
                                    pageNum += 1;
                                    mWeeklyRadioConcertAdapter = new WeeklyRadioConcertAdapter(mWeeklyRadioConcertBean);
                                    rvWeeklyRadioConcert.setAdapter(mWeeklyRadioConcertAdapter);
                                    mWeeklyRadioConcertAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                            startActivity(new Intent(WeeklyRadioConcertActivity.this, WebViewActivity.class).putExtra("h5url", mWeeklyRadioConcertBean.get(position).getRedirectUrl()));
                                        }
                                    });
                                    mWeeklyRadioConcertAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                        @Override
                                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                            startActivity(new Intent(WeeklyRadioConcertActivity.this, WebViewActivity.class).putExtra("h5url", mWeeklyRadioConcertBean.get(position).getRedirectUrl()));
                                        }
                                    });
                                    mWeeklyRadioConcertAdapter.setOnLoadMoreListener(WeeklyRadioConcertActivity.this, rvWeeklyRadioConcert); //加载更多
                                } else {
                                    mWeeklyRadioConcertAdapter.loadMoreEnd();
                                }
                            } else {
                                if (response.getData().getResultList().size() > 0) {
                                    pageNum += 1;
                                    mWeeklyRadioConcertAdapter.addData(response.getData().getResultList());
                                    mWeeklyRadioConcertAdapter.loadMoreComplete(); //完成本次
                                } else {
                                    mWeeklyRadioConcertAdapter.loadMoreEnd(); //完成所有加载
                                }
                            }
                        } else {
                            mWeeklyRadioConcertAdapter.loadMoreEnd();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        AIOAnalytics.onPageBegin("yinyuehuiliebiao");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        AIOAnalytics.onPageEnd("yinyuehuiliebiao");
    }

    @Override
    public void onLoadMoreRequested() {
        initData();
    }
}
