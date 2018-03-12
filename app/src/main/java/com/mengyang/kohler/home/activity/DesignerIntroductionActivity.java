package com.mengyang.kohler.home.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.bean.DesignerIntroductionBean;

import java.util.List;

import butterknife.BindView;

public class DesignerIntroductionActivity extends BaseActivity {

    @BindView(R.id.tv_designer_introduction_top)
    TopView tvDesignerIntroductionTop;
    @BindView(R.id.rv_designer_introduction)
    RecyclerView rvDesignerIntroduction;
    //    private DesignerIntroductionAdapter mDesignerIntroductionAdapter;
    private List<DesignerIntroductionBean> mDesignerIntroductionBean;

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
        rvDesignerIntroduction.addItemDecoration(new GridSpacingItemDecoration(3, 20, false));
        rvDesignerIntroduction.setHasFixedSize(true);
        rvDesignerIntroduction.setItemAnimator(new DefaultItemAnimator());

        //        mDesignerIntroductionBean = new ArrayList<>();
        //        mDesignerIntroductionAdapter = new DesignerIntroductionAdapter(mDesignerIntroductionBean);
        //        rvDesignerIntroduction.setAdapter(mDesignerIntroductionAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
