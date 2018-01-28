package com.mengyang.kohler.main.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.account.fragment.AccountFragment;
import com.mengyang.kohler.ar.ARFragment;
import com.mengyang.kohler.common.utils.IConstants;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.view.ResideLayout;
import com.mengyang.kohler.home.fragment.HomeFragment;
import com.mengyang.kohler.main.adapter.MyAdapter;
import com.mengyang.kohler.main.view.CustomViewPager;
import com.mengyang.kohler.whole_category.fragment.WholeCategoryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener {

    @BindView(R.id.cvp_main_viewpager)
    CustomViewPager cvpMainViewpager;
    @BindView(R.id.bt_home)
    Button btHome;
    @BindView(R.id.bt_whole_category)
    Button btWholeCategory;
    @BindView(R.id.bt_ar)
    Button btAr;
    @BindView(R.id.bt_account)
    Button btAccount;
    @BindView(R.id.rl_main)
    ResideLayout rlMain;
    @BindView(R.id.ll_main_fragment_select)
    LinearLayout llMainFragmentSelect;

    private List<Fragment> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initValues() {
        Boolean isFirst = (Boolean) SPUtil.get(this, IConstants.FIRST_APP, true);
        if (isFirst) {
            rlMain.openPane();
            SPUtil.put(this, IConstants.FIRST_APP, false);
        }
        cvpMainViewpager.setScanScroll(false);

        //加载adapter
        cvpMainViewpager.setAdapter(new MyAdapter(getSupportFragmentManager(), setfargment()));
    }

    @Override
    public void onBackPressed() {
        if (rlMain.isOpen()) {
            rlMain.closePane();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void initListener() {
        cvpMainViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 添加Fragment
     */
    private List<Fragment> setfargment() {
        list = new ArrayList<Fragment>();
        list.add(new HomeFragment());
        list.add(new WholeCategoryFragment());
        list.add(new ARFragment());
        list.add(new AccountFragment());
        return list;
    }

    @Override
    protected void initData() {

    }

    public class MyHandler extends Handler {

        public String msgContent;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    /**
     * fragment 样式判断
     */
    private void FragmentSelect(int flag) {
        if (flag == 0) {
            final Drawable homeDrawableTop = getResources().getDrawable(R.mipmap.arbai);
            final Drawable wholeCategoryDrawableTop = getResources().getDrawable(R.mipmap.pinleibai);
            final Drawable arDrawableTop = getResources().getDrawable(R.mipmap.shouyebai);
            final Drawable accountDrawableTop = getResources().getDrawable(R.mipmap.zhanghubai);
            llMainFragmentSelect.setBackgroundResource(R.mipmap.bg2);
            btHome.setTextColor(getResources().getColor(R.color.background_color));
            btHome.setCompoundDrawablesWithIntrinsicBounds(null, homeDrawableTop, null, null);
            btWholeCategory.setTextColor(getResources().getColor(R.color.background_color));
            btWholeCategory.setCompoundDrawablesWithIntrinsicBounds(null, wholeCategoryDrawableTop, null, null);
            btAr.setTextColor(getResources().getColor(R.color.background_color));
            btAr.setCompoundDrawablesWithIntrinsicBounds(null, arDrawableTop, null, null);
            btAccount.setTextColor(getResources().getColor(R.color.background_color));
            btAccount.setCompoundDrawablesWithIntrinsicBounds(null, accountDrawableTop, null, null);
        } else {
            final Drawable homeDrawableTop = getResources().getDrawable(R.mipmap.ar);
            final Drawable wholeCategoryDrawableTop = getResources().getDrawable(R.mipmap.category);
            final Drawable arDrawableTop = getResources().getDrawable(R.mipmap.icon_home);
            final Drawable accountDrawableTop = getResources().getDrawable(R.mipmap.personal);
            llMainFragmentSelect.setBackgroundResource(R.color.white);
            btHome.setTextColor(getResources().getColor(R.color.background_color));
            btHome.setCompoundDrawablesWithIntrinsicBounds(null, homeDrawableTop, null, null);
            btWholeCategory.setTextColor(getResources().getColor(R.color.background_color));
            btWholeCategory.setCompoundDrawablesWithIntrinsicBounds(null, wholeCategoryDrawableTop, null, null);
            btAr.setTextColor(getResources().getColor(R.color.background_color));
            btAr.setCompoundDrawablesWithIntrinsicBounds(null, arDrawableTop, null, null);
            btAccount.setTextColor(getResources().getColor(R.color.background_color));
            btAccount.setCompoundDrawablesWithIntrinsicBounds(null, accountDrawableTop, null, null);
        }
    }

    @OnClick({R.id.bt_home, R.id.bt_whole_category, R.id.bt_ar, R.id.bt_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_home:
                cvpMainViewpager.setCurrentItem(0);
                //沉浸式状态栏初始化黑色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
                FragmentSelect(1);
                break;
            case R.id.bt_whole_category:
                cvpMainViewpager.setCurrentItem(1);
                //沉浸式状态栏初始化白色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(false).init();
                FragmentSelect(0);
                break;
            case R.id.bt_ar:
                cvpMainViewpager.setCurrentItem(2);
                //沉浸式状态栏初始化黑色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
                FragmentSelect(1);
                break;
            case R.id.bt_account:
                cvpMainViewpager.setCurrentItem(3);
                //沉浸式状态栏初始化黑色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
                FragmentSelect(1);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(String data) {
        rlMain.openPane();
    }
}
