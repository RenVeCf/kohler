package com.mengyang.kohler.whole_category.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.CommodityClassificationTitleBean;
import com.mengyang.kohler.whole_category.adapter.ViewPagerAdapter;
import com.mengyang.kohler.whole_category.fragment.CommodityClassificationFragment;
import com.mengyang.kohler.whole_category.view.NavitationFollowScrollLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 商品分类
 */

public class CommodityClassificationActivity extends BaseActivity {

    @BindView(R.id.tv_commodity_classification_top)
    TopView tvCommodityClassificationTop;
    @BindView(R.id.nfsl_commodity_classification)
    NavitationFollowScrollLayout nfslCommodityClassification;
    @BindView(R.id.vp_commodity_classification)
    ViewPager vpCommodityClassification;
    private List<CommodityClassificationTitleBean> mCommodityClassificationTitleBean;
    private String[] titles;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragments;
    private String mCmsId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commodity_classification;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvCommodityClassificationTop);
        fragments = new ArrayList<>();

        mCommodityClassificationTitleBean = new ArrayList<>();
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        vpCommodityClassification.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("parentId", getIntent().getStringExtra("id"));

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getCommodityClassificationTitle(stringMap)
                .compose(CommodityClassificationActivity.this.<BasicResponse<List<CommodityClassificationTitleBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<CommodityClassificationTitleBean>>>(CommodityClassificationActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<List<CommodityClassificationTitleBean>> response) {
                        mCommodityClassificationTitleBean.clear();
                        mCommodityClassificationTitleBean = response.getData();

                        titles = new String[mCommodityClassificationTitleBean.size()];
                        for (int i = 0; i < mCommodityClassificationTitleBean.size(); i++) {
                            titles[i] = new String(mCommodityClassificationTitleBean.get(i).getNameCn());
                        }

                        nfslCommodityClassification.setViewPager(CommodityClassificationActivity.this, titles, vpCommodityClassification, R.color.black, R.color.black, 12, 12, 24, true, R.color.splilinecolor, 1f, 4f, 4f, 80);
                        nfslCommodityClassification.setBgLine(CommodityClassificationActivity.this, 1, R.color.white);
                        nfslCommodityClassification.setNavLine(CommodityClassificationActivity.this, 3, R.color.white);

                        for (int i = 0; i < mCommodityClassificationTitleBean.size(); i++) {
                            mCmsId = mCommodityClassificationTitleBean.get(i).getCmsId() + "";
                            fragments.add(new CommodityClassificationFragment());
                        }

                        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
                        vpCommodityClassification.setAdapter(viewPagerAdapter);
                    }
                });
    }

    public String getCmsId() {
        return mCmsId;
    }
}
