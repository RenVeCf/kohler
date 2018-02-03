package com.mengyang.kohler.main.activity;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.account.fragment.AccountFragment;
import com.mengyang.kohler.ar.ARFragment;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.view.ResideLayout;
import com.mengyang.kohler.home.fragment.HomeFragment;
import com.mengyang.kohler.whole_category.fragment.WholeCategoryFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener {

    @BindView(R.id.vp_main_viewpager)
    ViewPager vpMainViewpager;
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
    @BindView(R.id.ll_nearby_shops)
    LinearLayout llNearbyShops;
    @BindView(R.id.ll_account_manual)
    LinearLayout llAccountManual;
    @BindView(R.id.ll_toilet_seat)
    LinearLayout llToiletSeat;
    @BindView(R.id.ll_shower_room_parts)
    LinearLayout llShowerRoomParts;
    @BindView(R.id.ll_shower_room_nozzle)
    LinearLayout llShowerRoomNozzle;
    @BindView(R.id.ll_shower_nozzle)
    LinearLayout llShowerNozzle;
    @BindView(R.id.ll_makeup)
    LinearLayout llMakeup;
    @BindView(R.id.ll_massage_bathtub)
    LinearLayout llMassageBathtub;
    @BindView(R.id.ll_bathtub)
    LinearLayout llBathtub;

    private Fragment currentFragment = new Fragment();
    private HomeFragment mHomeFragment = new HomeFragment();
    private WholeCategoryFragment mWholeCategoryFragment = new WholeCategoryFragment();
    private ARFragment mARFragment = new ARFragment();
    private AccountFragment mAccountFragment = new AccountFragment();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initValues() {
        App.addDestoryActivity(this, "MainActivity");
        App.getManager().addActivity(this);
        Boolean isFirstOpen = (Boolean) SPUtil.get(this, IConstants.FIRST_APP, true);
        if (isFirstOpen) {
            rlMain.openPane();
            SPUtil.put(this, IConstants.FIRST_APP, false);
        }
        switchFragment(mHomeFragment).commit();
        //        cvpMainViewpager.setScanScroll(false);

        //        //加载adapter
        //        cvpMainViewpager.setAdapter(new MyAdapter(getSupportFragmentManager(), setfargment()));
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
        vpMainViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    @Override
    protected void initData() {

    }

    //Fragment优化
    private FragmentTransaction switchFragment(Fragment targetFragment) {

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.ll_main, targetFragment, targetFragment.getClass().getName());

        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment);
        }
        currentFragment = targetFragment;
        return transaction;
    }

    /**
     * fragment 样式判断
     */
    private void FragmentSelect(int flag) {
        if (flag == 0) {
            final Drawable homeDrawableTop = getResources().getDrawable(R.mipmap.shouyebai);
            final Drawable wholeCategoryDrawableTop = getResources().getDrawable(R.mipmap.pinleibai);
            final Drawable arDrawableTop = getResources().getDrawable(R.mipmap.arbai);
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
            final Drawable homeDrawableTop = getResources().getDrawable(R.mipmap.icon_home);
            final Drawable wholeCategoryDrawableTop = getResources().getDrawable(R.mipmap.category);
            final Drawable arDrawableTop = getResources().getDrawable(R.mipmap.ar);
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

    @OnClick({R.id.bt_home, R.id.bt_whole_category, R.id.bt_ar, R.id.bt_account, R.id.ll_nearby_shops, R.id.ll_account_manual, R.id.ll_toilet_seat, R.id.ll_shower_room_parts, R.id.ll_shower_room_nozzle, R.id.ll_shower_nozzle, R.id.ll_makeup, R.id.ll_massage_bathtub, R.id.ll_bathtub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_home:
                switchFragment(mHomeFragment).commit();
                //沉浸式状态栏初始化黑色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
                FragmentSelect(1);
                break;
            case R.id.bt_whole_category:
                switchFragment(mWholeCategoryFragment).commit();
                //沉浸式状态栏初始化白色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(false).init();
                FragmentSelect(0);
                break;
            case R.id.bt_ar:
                switchFragment(mARFragment).commit();
                //沉浸式状态栏初始化黑色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
                FragmentSelect(1);
                break;
            case R.id.bt_account:
                switchFragment(mAccountFragment).commit();
                //沉浸式状态栏初始化黑色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
                FragmentSelect(1);
                break;
            case R.id.ll_nearby_shops:
                break;
            case R.id.ll_account_manual:
                break;
            case R.id.ll_toilet_seat:
                break;
            case R.id.ll_shower_room_parts:
                break;
            case R.id.ll_shower_room_nozzle:
                break;
            case R.id.ll_shower_nozzle:
                break;
            case R.id.ll_makeup:
                break;
            case R.id.ll_massage_bathtub:
                break;
            case R.id.ll_bathtub:
                break;
        }
    }

    @Override
    public void onFragmentInteraction(String data) {
        rlMain.openPane();
    }
}
