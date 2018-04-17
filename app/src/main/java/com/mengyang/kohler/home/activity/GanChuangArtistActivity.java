package com.mengyang.kohler.home.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.DesignerIntroductionAdapter;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.ArtKohlerBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Description : 敢创•科勒亚太艺术展 （展品介绍）
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/3/29
 */

public class GanChuangArtistActivity extends BaseActivity {
    @BindView(R.id.tv_designer_introduction_top)
    TopView tvDesignerIntroductionTop;
    @BindView(R.id.rv_designer_introduction)
    RecyclerView rvDesignerIntroduction;
    @BindView(R.id.srl_designer_introduction)
    SwipeRefreshLayout srlDesignerIntroduction;
    private DesignerIntroductionAdapter mDesignerIntroductionAdapter;
    private List<ArtKohlerBean.DesignersBean> mDesignerIntroductionBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_designer_introduction;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvDesignerIntroductionTop);

        GridLayoutManager layoutManagerActivity = new GridLayoutManager(App.getContext(), 3);
        rvDesignerIntroduction.setLayoutManager(layoutManagerActivity);
        rvDesignerIntroduction.addItemDecoration(new GridSpacingItemDecoration(3, 15, false));
        rvDesignerIntroduction.setHasFixedSize(true);
        rvDesignerIntroduction.setItemAnimator(new DefaultItemAnimator());

        mDesignerIntroductionBean = new ArrayList<>();
        mDesignerIntroductionAdapter = new DesignerIntroductionAdapter(mDesignerIntroductionBean);
        rvDesignerIntroduction.setAdapter(mDesignerIntroductionAdapter);
    }

    @Override
    protected void initListener() {
        srlDesignerIntroduction.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                srlDesignerIntroduction.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData() {
        Map<String, Object> stringMap = IdeaApi.getSign();

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getGanChuang(stringMap)
                .compose(GanChuangArtistActivity.this.<BasicResponse<ArtKohlerBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<ArtKohlerBean>>(GanChuangArtistActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<ArtKohlerBean> response) {
                        mDesignerIntroductionBean.clear();
                        mDesignerIntroductionBean.addAll(response.getData().getDesigners());
                        mDesignerIntroductionAdapter = new DesignerIntroductionAdapter(mDesignerIntroductionBean);
                        rvDesignerIntroduction.setAdapter(mDesignerIntroductionAdapter);
                    }
                });
    }
}
