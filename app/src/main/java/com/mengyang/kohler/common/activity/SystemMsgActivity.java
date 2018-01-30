package com.mengyang.kohler.common.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.adapter.SystemMsgAdapter;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;
import butterknife.OnClick;

public class SystemMsgActivity extends BaseActivity {

    @BindView(R.id.tv_system_msg_top)
    TopView tvSystemMsgTop;
    @BindView(R.id.rv_system_msg)
    RecyclerView rvSystemMsg;
    private SystemMsgAdapter mSystemMsgAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_message;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvSystemMsgTop);
        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        rvSystemMsg.setLayoutManager(layoutManager);
        rvSystemMsg.addItemDecoration(new SpacesItemDecoration(20));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvSystemMsg.setHasFixedSize(true);
        rvSystemMsg.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_top_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
        }
    }
}
