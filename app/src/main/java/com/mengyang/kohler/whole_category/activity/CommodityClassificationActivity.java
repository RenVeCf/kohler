package com.mengyang.kohler.whole_category.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.LazyViewPager;
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
import io.reactivex.disposables.Disposable;
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
    @BindView(R.id.table_layout)
    TabLayout mTableLayout;
    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;
    private List<CommodityClassificationTitleBean> mCommodityClassificationTitleBean;
    private String[] titles;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragments;
    private boolean mIsPageSelected = true;
    private long mListTime;
    private PageSelectedListenning mPageSelectedListenning;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_commodity_classification;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvCommodityClassificationTop);
        tvTopTitle.setText(getIntent().getStringExtra("classification_title"));
        fragments = new ArrayList<>();
        mCommodityClassificationTitleBean = new ArrayList<>();
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
//        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mCommodityClassificationTitleBean);
        vpCommodityClassification.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
//        if (mIsPageSelected) {
//            mIsPageSelected = false;
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
    //                        nfslCommodityClassification.setNavLine(CommodityClassificationActivity.this, 2, R.color.white);
                            nfslCommodityClassification.setNavLine(CommodityClassificationActivity.this, 2, R.color.black);

                            for (int i = 0; i < mCommodityClassificationTitleBean.size(); i++) {
                                final CommodityClassificationFragment commodityClassificationFragment = CommodityClassificationFragment.newInstance(mCommodityClassificationTitleBean.get(i).getCmsId() + "");
                                fragments.add(commodityClassificationFragment);

                                vpCommodityClassification.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                                        if (commodityClassificationFragment != null) {
//                                            commodityClassificationFragment.setIsSelected(false);
//                                        }
                                    }

                                    @Override
                                    public void onPageSelected(int position) {
                                        long currentTime = System.currentTimeMillis();
                                        long l = currentTime - mListTime;
                                        mListTime = currentTime;

//                                        viewPagerAdapter.notifyDataSetChanged();
                                            Log.i("kohler666", "Activity,onPageSelected,position = "+ position);
                                            mIsPageSelected = true;


                                            if (commodityClassificationFragment != null) {
                                                commodityClassificationFragment.setIsSelected(true);
                                            }
                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int state) {

                                    }
                                });

                            }

                            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
//                            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mCommodityClassificationTitleBean);
                            vpCommodityClassification.setAdapter(viewPagerAdapter);

                        }
                    });
//        }
    }

    public interface PageSelectedListenning {
        void onSelected();
    }

    public void setPageSelectedListenning(PageSelectedListenning pageSelectedListenning) {
        mPageSelectedListenning = pageSelectedListenning;
    }
}
