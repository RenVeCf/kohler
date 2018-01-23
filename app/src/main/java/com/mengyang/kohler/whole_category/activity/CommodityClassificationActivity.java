package com.mengyang.kohler.whole_category.activity;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.whole_category.adapter.CommodityClassificationAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class CommodityClassificationActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.viewpager_tv_one)
    TextView viewpagerTvOne;
    @BindView(R.id.viewpager_tv_two)
    TextView viewpagerTvTwo;
    @BindView(R.id.viewpager_tv_three)
    TextView viewpagerTvThree;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.cursor_img)
    ImageView mCursorImg;
    @BindView(R.id.tv_commodity_classification_top)
    TopView tvCommodityClassificationTop;

    private CommodityClassificationAdapter mAdapter = null;
    private ArrayList<View> mPageList = null;

    private int mOffset = 0;// 移动条图片的偏移量
    private int mCurrIndex = 0; // 当前页面的编号
    private int mOneDis = 0; // 移动条滑动一页的距离
    private int mTwoDis = 0; // 滑动条移动两页的距离
    private ArrayList<String> mTitleLists = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commodity_classification;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvCommodityClassificationTop);
        //        // 初始化指示器位置
        //        initCursorPosition();

        mPageList = new ArrayList<>();
        LayoutInflater inflater = getLayoutInflater();
        mPageList.add(inflater.inflate(R.layout.commodity_classification_viewpager, null, false));
        mPageList.add(inflater.inflate(R.layout.commodity_classification_viewpager, null, false));

        // 装入每个页面的Title
        mTitleLists = new ArrayList<>();
        mTitleLists.add("第一页");
        mTitleLists.add("第二页");
        mTitleLists.add("第三页");
        mTitleLists.add("第四页");
        // 设置适配器
        mAdapter = new CommodityClassificationAdapter(mPageList, mTitleLists);
        viewPager.setAdapter(mAdapter);

        // 页面改变监听器
        viewPager.addOnPageChangeListener(this);
        // 初始默认第一页
        viewPager.setCurrentItem(0);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    private void initCursorPosition() {
        //        // 获取指示器图片宽度
        //        int cursorWidth = BitmapFactory.decodeResource(getResources(), R.drawable.line).getWidth();
        //
        //        // 获取分辨率宽度
        //        DisplayMetrics dm = new DisplayMetrics();
        //        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //        int screenWidth = dm.widthPixels;
        //
        //        // 计算偏移量
        //        mOffset = (screenWidth / 3 - cursorWidth) / 2;
        //
        //        // 设置动画初始位置
        //        Matrix matrix = new Matrix();
        //        matrix.postTranslate(mOffset, 0);
        //        mCursorImg.setImageMatrix(matrix);
        //
        //        // 计算指示器图片的移动距离
        //        mOneDis = mOffset * 2 + cursorWidth;// 页卡1 -> 页卡2 偏移量
        //        mTwoDis = mOneDis * 2;// 页卡1 -> 页卡3 偏移量
    }

    @OnClick({R.id.viewpager_tv_one, R.id.viewpager_tv_two, R.id.viewpager_tv_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.viewpager_tv_one:
                break;
            case R.id.viewpager_tv_two:
                break;
            case R.id.viewpager_tv_three:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 指示器图片动画设置
        Animation animation = null;
        switch (position) {
            case 0:
                if (1 == mCurrIndex) {
                    animation = new TranslateAnimation(mOneDis, 0, 0, 0);
                } else if (2 == mCurrIndex) {
                    animation = new TranslateAnimation(mTwoDis, 0, 0, 0);
                }
                break;
            case 1:
                if (0 == mCurrIndex) {
                    animation = new TranslateAnimation(mOffset, mOneDis, 0, 0);
                } else if (2 == mCurrIndex) {
                    animation = new TranslateAnimation(mTwoDis, mOneDis, 0, 0);
                }
                break;
            case 2:
                if (0 == mCurrIndex) {
                    animation = new TranslateAnimation(mOffset, mTwoDis, 0, 0);
                } else if (1 == mCurrIndex) {
                    animation = new TranslateAnimation(mOneDis, mTwoDis, 0, 0);
                }
                break;
            default:
                break;
        }
        mCurrIndex = position;
        animation.setFillAfter(true); // True:图片停在动画结束位置
        animation.setDuration(300);
        mCursorImg.startAnimation(animation);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
