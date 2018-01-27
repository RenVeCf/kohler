package com.mengyang.kohler.home.activity;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;

public class HomeSearchActivity extends BaseActivity {
    @BindView(R.id.tv_home_search_top)
    TopView tvHomeSearchTop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvHomeSearchTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
