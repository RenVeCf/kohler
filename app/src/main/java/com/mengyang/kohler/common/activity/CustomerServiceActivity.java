package com.mengyang.kohler.common.activity;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;

public class CustomerServiceActivity extends BaseActivity {

    @BindView(R.id.tv_customer_service_top)
    TopView tvCustomerServiceTop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_service;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvCustomerServiceTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
