package com.mengyang.kohler.whole_category.activity;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.SelectClassificationBean;
import com.mengyang.kohler.whole_category.adapter.SelectClassificationAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 精选分类
 */

public class SelectClassificationActivity extends BaseActivity {

    @BindView(R.id.tv_whole_category_select_classification_top)
    TopView tvWholeCategorySelectClassificationTop;
    @BindView(R.id.iv_top_back)
    ImageView ivTopBack;
    @BindView(R.id.rv_whole_category_select_classification)
    RecyclerView rvWholeCategorySelectClassification;
    private List<SelectClassificationBean> mSelectClassificationBean;
    private SelectClassificationAdapter mSelectClassificationAdapter;

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
        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        rvWholeCategorySelectClassification.setLayoutManager(layoutManager);
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvWholeCategorySelectClassification.setHasFixedSize(true);
        rvWholeCategorySelectClassification.setItemAnimator(new DefaultItemAnimator());
        rvWholeCategorySelectClassification.setNestedScrollingEnabled(true);
        mSelectClassificationBean = new ArrayList<>();
        mSelectClassificationAdapter = new SelectClassificationAdapter(mSelectClassificationBean);
        rvWholeCategorySelectClassification.setAdapter(mSelectClassificationAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Map<String, String> stringMap = IdeaApi.getSign();

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getSelectClassification(stringMap)
                .compose(SelectClassificationActivity.this.<BasicResponse<List<SelectClassificationBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<SelectClassificationBean>>>(SelectClassificationActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<List<SelectClassificationBean>> response) {
                        mSelectClassificationBean.clear();
                        mSelectClassificationBean = response.getData();
                        mSelectClassificationAdapter = new SelectClassificationAdapter(mSelectClassificationBean);
                        rvWholeCategorySelectClassification.setAdapter(mSelectClassificationAdapter);
                        mSelectClassificationAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                if (mSelectClassificationBean != null) {
                                    startActivity(new Intent(SelectClassificationActivity.this, CommodityClassificationActivity.class).putExtra("parentId", mSelectClassificationBean.get(position).getParentsId() + ""));
                                }
                            }
                        });
                    }
                });
    }
}
