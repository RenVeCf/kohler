package com.mengyang.kohler.home.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.LiveRealTimeAdapter;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.LiveRealTimeBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 现场实时投票
 */

public class LiveRealTimeActivity extends BaseActivity {//implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.tv_live_real_time_top)
    TopView tvLiveRealTimeTop;
    @BindView(R.id.tv_live_real_time_prize)
    TextView tvLiveRealTimePrize;
    @BindView(R.id.rv_live_real_time)
    RecyclerView rvLiveRealTime;
    @BindView(R.id.srl_live_real_time)
    SwipeRefreshLayout srlLiveRealTime;
    @BindView(R.id.rl_live_real_time)
    RelativeLayout rlLiveRealTime;
    private LiveRealTimeAdapter mLiveRealTimeAdapter;
    private List<LiveRealTimeBean.ResultListBean> mLiveRealTimeBean;
    private int pageNum = 0;
    private int mTotalSize = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_real_time;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvLiveRealTimeTop);
        GridLayoutManager layoutManagerActivity = new GridLayoutManager(App.getContext(), 2);
        rvLiveRealTime.setLayoutManager(layoutManagerActivity);
        rvLiveRealTime.addItemDecoration(new GridSpacingItemDecoration(2, 35, false));
        rvLiveRealTime.setNestedScrollingEnabled(false);
        rvLiveRealTime.setHasFixedSize(true);
        rvLiveRealTime.setItemAnimator(new DefaultItemAnimator());

        //解决SwipeRefreshLayout和RecyclerView的滑动冲突
        rvLiveRealTime.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //                super.onScrolled(recyclerView, dx, dy);
                //计算Recyclerview第一个item的位置是否可见
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                //当第一个item不可见时，设置SwipeRefreshLayout不可用
                srlLiveRealTime.setEnabled(topRowVerticalPosition >= 0);
            }
        });

        mLiveRealTimeBean = new ArrayList<>();
        mLiveRealTimeAdapter = new LiveRealTimeAdapter(mLiveRealTimeBean);
        rvLiveRealTime.setAdapter(mLiveRealTimeAdapter);
    }

    @Override
    protected void initListener() {
        srlLiveRealTime.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 0;
                initData();
                srlLiveRealTime.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData() {
        //看大图
        Map<String, Object> stringMap = IdeaApi.getSign();
        //        stringMap.put("pageNum", pageNum + "");

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getMeetingLiveRealTime(stringMap)
                .compose(LiveRealTimeActivity.this.<BasicResponse<LiveRealTimeBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<LiveRealTimeBean>>(LiveRealTimeActivity.this, false) {
                    @Override
                    public void onSuccess(BasicResponse<LiveRealTimeBean> response) {
                        //                        if (response != null) {
                        mTotalSize = response.getData().getTotalSize();
                        //                            if (pageNum == 0) {
                        mLiveRealTimeBean.clear();
                        mLiveRealTimeBean.addAll(response.getData().getResultList());
                        if (mLiveRealTimeBean.size() > 0) {
                            pageNum += 1;
                            mLiveRealTimeAdapter = new LiveRealTimeAdapter(mLiveRealTimeBean);
                            rvLiveRealTime.setAdapter(mLiveRealTimeAdapter);
                            //                                    mLiveRealTimeAdapter.setOnLoadMoreListener(LiveRealTimeActivity.this, rvLiveRealTime); //加载更多
                            mLiveRealTimeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    startActivity(new Intent(LiveRealTimeActivity.this, MeetingBigPhotoActivity.class).putExtra("totalSize", mTotalSize).putExtra("position", position).putExtra("num", mLiveRealTimeBean.get(position).getLikeCount()).putExtra("url", mLiveRealTimeBean.get(position).getPicUrl()).putExtra("id", mLiveRealTimeBean.get(position).getId()));
                                }
                            });
                            mLiveRealTimeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                @Override
                                public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                                    //投票
                                    Map<String, Object> stringMap = IdeaApi.getSign();
                                    stringMap.put("id", mLiveRealTimeBean.get(position).getId() + "");

                                    IdeaApi.getRequestLogin(stringMap);
                                    IdeaApi.getApiService()
                                            .getMeetingLikePicture(stringMap)
                                            .compose(LiveRealTimeActivity.this.<BasicResponse>bindToLifecycle())
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new DefaultObserver<BasicResponse>(LiveRealTimeActivity.this, false) {
                                                @Override
                                                public void onSuccess(BasicResponse response) {
                                                    pageNum = 0;
                                                    initData();
                                                }
                                            });
                                }
                            });
                        }
                        //                        } else {
                        //                            mLiveRealTimeAdapter.loadMoreEnd();
                        //                        }
                        //                            } else {
                        //                                if (response.getData().getResultList().size() > 0) {
                        //                                    pageNum += 1;
                        //                                    mLiveRealTimeAdapter.addData(response.getData().getResultList());
                        //                                    mLiveRealTimeAdapter.loadMoreComplete(); //完成本次
                        //                                } else {
                        //                                    mLiveRealTimeAdapter.loadMoreEnd(); //完成所有加载
                        //                                }
                        //                            }
                        //                        } else {
                        //                            mLiveRealTimeAdapter.loadMoreEnd();
                        //                        }
                    }
                });
    }

    //    @Override
    //    public void onLoadMoreRequested() {
    //        initData();
    //    }

    @OnClick(R.id.rl_live_real_time)
    public void onViewClicked() {
        startActivity(new Intent(this, BarrageActivity.class));
    }
}