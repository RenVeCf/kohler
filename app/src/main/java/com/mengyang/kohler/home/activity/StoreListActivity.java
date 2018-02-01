package com.mengyang.kohler.home.activity;

import android.support.v7.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.StoreListAdapter;

import butterknife.BindView;

public class StoreListActivity extends BaseActivity {
    @BindView(R.id.tv_store_list_top)
    TopView tvStoreListTop;
    @BindView(R.id.rv_store_list)
    RecyclerView rvStoreList;
    private StoreListAdapter mStoreListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_list;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvStoreListTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
