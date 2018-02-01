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
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        App.getManager().addActivity(this);
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
        //有问题（没假数据没Bean）
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("pageNum", 0 + "");
        stringMap.put("pageSize", 10 + "");

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getSystemMsg(stringMap)
                .compose(SystemMsgActivity.this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(SystemMsgActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {

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
}
