package com.mengyang.kohler.home.activity;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;

/**
 * 展品介绍
 */

public class WorksOfArtActivity extends BaseActivity {

    @BindView(R.id.tv_works_of_art_top)
    TopView tvWorksOfArtTop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_works_of_art;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvWorksOfArtTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
