package com.mengyang.kohler.common.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.adapter.SystemMsgAdapter;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.SystemMsgBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 系统消息
 */

public class SystemMsgActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.tv_system_msg_top)
    TopView tvSystemMsgTop;
    @BindView(R.id.iv_top_system_msg)
    ImageView ivTopSystemMsg;
    @BindView(R.id.rv_system_msg)
    RecyclerView rvSystemMsg;
    @BindView(R.id.srl_system_msg)
    SwipeRefreshLayout srlSystemMsg;
    private SystemMsgAdapter mSystemMsgAdapter;
    private List<SystemMsgBean.ResultListBean> mSystemMsgBean;
    private int pageNum = 0; //请求页数

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_message;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvSystemMsgTop);
        ivTopSystemMsg.setImageResource(R.mipmap.message);
        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        rvSystemMsg.setLayoutManager(layoutManager);
        rvSystemMsg.addItemDecoration(new SpacesItemDecoration(20));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvSystemMsg.setHasFixedSize(true);
        rvSystemMsg.setItemAnimator(new DefaultItemAnimator());

        mSystemMsgBean = new ArrayList<>();
        mSystemMsgAdapter = new SystemMsgAdapter(mSystemMsgBean);
        rvSystemMsg.setAdapter(mSystemMsgAdapter);
    }

    @Override
    protected void initListener() {
        srlSystemMsg.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 0;
                initData();
                srlSystemMsg.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("pageNum", pageNum + "");

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getSystemMsg(stringMap)
                .compose(SystemMsgActivity.this.<BasicResponse<SystemMsgBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<SystemMsgBean>>(SystemMsgActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<SystemMsgBean> response) {
                        if (response.getData().getResultList().size() != 0) {
                            if (pageNum == 0) {
                                mSystemMsgBean.clear();
                                mSystemMsgBean.addAll(response.getData().getResultList());
                                if (mSystemMsgBean.size() > 0) {
                                    pageNum += 1;
                                    mSystemMsgAdapter = new SystemMsgAdapter(mSystemMsgBean);
                                    rvSystemMsg.setAdapter(mSystemMsgAdapter);
                                    mSystemMsgAdapter.setOnLoadMoreListener(SystemMsgActivity.this, rvSystemMsg); //加载更多
                                } else {
                                    mSystemMsgAdapter.loadMoreEnd();
                                }
                            } else {
                                if (response.getData().getResultList().size() > 0) {
                                    pageNum += 1;
                                    mSystemMsgAdapter.addData(response.getData().getResultList());
                                    mSystemMsgAdapter.loadMoreComplete(); //完成本次
                                } else {
                                    mSystemMsgAdapter.loadMoreEnd(); //完成所有加载
                                }
                            }
                        } else {
                            mSystemMsgAdapter.setEmptyView(R.layout.null_layout, rvSystemMsg);
                        }
                    }
                });
    }

    @OnClick({R.id.iv_top_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        initData();
    }
}
