package com.mengyang.kohler.whole_category.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;

public class WholeCategoryFragment extends BaseFragment {

    @BindView(R.id.tv_whole_category_top)
    TopView tvWholeCategoryTop;
    @BindView(R.id.rv_whole_category)
    RecyclerView rvWholeCategory;
    @BindView(R.id.iv_top_customer_service)
    ImageView ivTopCustomerService;
    @BindView(R.id.iv_top_system_msg)
    ImageView ivTopSystemMsg;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_whole_category;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvWholeCategoryTop);
        ivTopCustomerService.setVisibility(View.VISIBLE);
        ivTopSystemMsg.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
