package com.mengyang.kohler.whole_category.activity;

import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;

public class SelectClassificationActivity extends BaseActivity {

    @BindView(R.id.tv_whole_category_select_classification_top)
    TopView tvWholeCategorySelectClassificationTop;
    @BindView(R.id.iv_top_back)
    ImageView ivTopBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_classification;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //沉浸式状态栏初始化白色
        ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(false).init();
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvWholeCategorySelectClassificationTop);
        ivTopBack.setImageResource(R.mipmap.fanhuibai);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
