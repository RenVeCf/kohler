package com.mengyang.kohler.home.activity;

import android.support.v7.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;

/**
 * 我的手册
 */

public class MineManualActivity extends BaseActivity {

    @BindView(R.id.tv_mine_manual_top)
    TopView tvMineManualTop;
    @BindView(R.id.rv_mine_manual_brochure_list)
    RecyclerView rvMineManualBrochureList;
    @BindView(R.id.rv_mine_manual_my_brochure)
    RecyclerView rvMineManualMyBrochure;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_manual;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvMineManualTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
