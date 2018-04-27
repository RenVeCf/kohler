package com.mengyang.kohler.home.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.WorksOfArtAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 展品介绍
 */

public class WorksOfArtActivity extends BaseActivity {

    @BindView(R.id.tv_works_of_art_top)
    TopView tvWorksOfArtTop;
    @BindView(R.id.rv_works_of_art)
    RecyclerView rvWorksOfArt;
    private WorksOfArtAdapter mWorksOfArtAdapter;
    private List<Object> mWorksOfArt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_works_of_art;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvWorksOfArtTop);

        // 展品介绍
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        rvWorksOfArt.setLayoutManager(layoutManager);
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvWorksOfArt.setHasFixedSize(true);
        rvWorksOfArt.setItemAnimator(new DefaultItemAnimator());

        mWorksOfArt = new ArrayList<>();
        mWorksOfArt.add(R.mipmap.introduction_of_exhibits_1);
        mWorksOfArt.add(R.mipmap.introduction_of_exhibits_2);
        mWorksOfArt.add(R.mipmap.introduction_of_exhibits_3);
        mWorksOfArt.add(R.mipmap.introduction_of_exhibits_4);
        mWorksOfArt.add(R.mipmap.introduction_of_exhibits_5);
        mWorksOfArt.add(R.mipmap.introduction_of_exhibits_6);
        mWorksOfArt.add(R.mipmap.introduction_of_exhibits_7);
        mWorksOfArt.add(R.mipmap.introduction_of_exhibits_8);
        mWorksOfArt.add(R.mipmap.introduction_of_exhibits_9);
        mWorksOfArt.add(R.mipmap.introduction_of_exhibits_10);
        mWorksOfArt.add(R.mipmap.introduction_of_exhibits_11);
        mWorksOfArt.add(R.mipmap.introduction_of_exhibits_12);
        mWorksOfArt.add(R.mipmap.introduction_of_exhibits_0);

        mWorksOfArtAdapter = new WorksOfArtAdapter(mWorksOfArt);
        rvWorksOfArt.setAdapter(mWorksOfArtAdapter);
        //置顶某item
        ((LinearLayoutManager) rvWorksOfArt.getLayoutManager()).scrollToPositionWithOffset(getIntent().getIntExtra("WorksOfArtPosition", 0), 0);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
