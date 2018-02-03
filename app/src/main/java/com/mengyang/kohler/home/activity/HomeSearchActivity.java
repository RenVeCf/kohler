package com.mengyang.kohler.home.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.HomeSearchAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索页
 */

public class HomeSearchActivity extends BaseActivity {
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
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_home_search_remove_search, R.id.iv_home_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_home_search_remove_search:
                etHomeSearchActivity.setText("");
                break;
            case R.id.iv_home_search:
                break;
        }
    }
}
