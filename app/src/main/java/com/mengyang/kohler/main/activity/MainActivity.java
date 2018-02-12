package com.mengyang.kohler.main.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.account.activity.LoginActivity;
import com.mengyang.kohler.account.fragment.AccountFragment;
import com.mengyang.kohler.ar.ARFragment;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.view.ResideLayout;
import com.mengyang.kohler.home.activity.MineManualActivity;
import com.mengyang.kohler.home.activity.StoreMapActivity;
import com.mengyang.kohler.home.fragment.HomeFragment;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.NotSelectClassificationBean;
import com.mengyang.kohler.module.bean.UserMsgBean;
import com.mengyang.kohler.whole_category.activity.CommodityClassificationActivity;
import com.mengyang.kohler.whole_category.fragment.WholeCategoryFragment;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener {

    @BindView(R.id.ll_nearby_shops)
    LinearLayout llNearbyShops;
    @BindView(R.id.ll_account_manual)
    LinearLayout llAccountManual;
    @BindView(R.id.ll_super_toilet_seat)
    LinearLayout llSuperToiletSeat;
    @BindView(R.id.ll_toilet_seat)
    LinearLayout llToiletSeat;
    @BindView(R.id.ll_toilet_seat_cover)
    LinearLayout llToiletSeatCover;
    @BindView(R.id.ll_qing_shu_bao_toilet_seat)
    LinearLayout llQingShuBaoToiletSeat;
    @BindView(R.id.ll_shower_room_parts)
    LinearLayout llShowerRoomParts;
    @BindView(R.id.ll_ceramic_tile)
    LinearLayout llCeramicTile;
    @BindView(R.id.ll_makeup)
    LinearLayout llMakeup;
    @BindView(R.id.ll_shower_room_nozzle)
    LinearLayout llShowerRoomNozzle;
    @BindView(R.id.ll_shower_nozzle)
    LinearLayout llShowerNozzle;
    @BindView(R.id.ll_shower_room)
    LinearLayout llShowerRoom;
    @BindView(R.id.ll_steam_equipment)
    LinearLayout llSteamEquipment;
    @BindView(R.id.ll_bathtub)
    LinearLayout llBathtub;
    @BindView(R.id.ll_massage_bathtub)
    LinearLayout llMassageBathtub;
    @BindView(R.id.ll_commercial_products)
    LinearLayout llCommercialProducts;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
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
    @BindView(R.id.ll_main_fragment_select)
    LinearLayout llMainFragmentSelect;
    @BindView(R.id.rl_main)
    ResideLayout rlMain;
    @BindView(R.id.view)
    View view_line;
    @BindView(R.id.scroll_view_mian)
    ScrollView scroll_view_mian;
    @BindView(R.id.ll_toilet_heater)
    LinearLayout llToiletHeater;
    private UserMsgBean mUserMsgBean;
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
        //        Boolean isFirstOpen = (Boolean) SPUtil.get(this, IConstants.FIRST_APP, true);
        //        if (isFirstOpen) {
        //            rlMain.openPane();
        SPUtil.put(this, IConstants.FIRST_APP, false);
        //        }
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
        if (((boolean) SPUtil.get(App.getContext(), IConstants.IS_LOGIN, false)) == true) {
            Map<String, String> stringMap = IdeaApi.getSign();

            IdeaApi.getRequestLogin(stringMap);
            IdeaApi.getApiService()
                    .getUserMsg(stringMap)
                    .compose(this.<BasicResponse<UserMsgBean>>bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver<BasicResponse<UserMsgBean>>(this, false) {
                        @Override
                        public void onSuccess(BasicResponse<UserMsgBean> response) {
                            mUserMsgBean = response.getData();
                            SPUtil.put(App.getContext(), IConstants.USER_NIKE_NAME, mUserMsgBean.getNickName());
                            SPUtil.put(App.getContext(), IConstants.USER_HEAD_PORTRAIT, mUserMsgBean.getPortraitUrl());
                            SPUtil.put(App.getContext(), IConstants.MEETING_PUSH_MSG, (mUserMsgBean.isPushMsg() + ""));
                        }
                    });
        }
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
            btHome.setTextColor(getResources().getColor(R.color.white));
            btHome.setCompoundDrawablesWithIntrinsicBounds(null, homeDrawableTop, null, null);
            btWholeCategory.setTextColor(getResources().getColor(R.color.white));
            btWholeCategory.setCompoundDrawablesWithIntrinsicBounds(null, wholeCategoryDrawableTop, null, null);
            btAr.setTextColor(getResources().getColor(R.color.white));
            btAr.setCompoundDrawablesWithIntrinsicBounds(null, arDrawableTop, null, null);
            btAccount.setTextColor(getResources().getColor(R.color.white));
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

    private void listWithoutSelection(final int position) {
        Map<String, String> stringMap = IdeaApi.getSign();

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getNotSelectClassification(stringMap)
                .compose(this.<BasicResponse<List<NotSelectClassificationBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<NotSelectClassificationBean>>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<List<NotSelectClassificationBean>> response) {
                        if (response != null) {
                            startActivity(new Intent(MainActivity.this, CommodityClassificationActivity.class).putExtra("id", response.getData().get(position).getId() + "").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                    }
                });
    }

    @Override
    public void onFragmentInteraction(String data) {
        rlMain.openPane();
    }

    @OnClick({R.id.ll_toilet_heater, R.id.ll_nearby_shops, R.id.ll_account_manual, R.id.ll_super_toilet_seat, R.id.ll_toilet_seat, R.id.ll_toilet_seat_cover, R.id.ll_qing_shu_bao_toilet_seat, R.id.ll_shower_room_parts, R.id.ll_ceramic_tile, R.id.ll_makeup, R.id.ll_shower_room_nozzle, R.id.ll_shower_nozzle, R.id.ll_shower_room, R.id.ll_steam_equipment, R.id.ll_bathtub, R.id.ll_massage_bathtub, R.id.ll_commercial_products, R.id.bt_home, R.id.bt_whole_category, R.id.bt_ar, R.id.bt_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_nearby_shops:
                startActivity(new Intent(this, StoreMapActivity.class));
                break;
            case R.id.ll_account_manual:
                if (((boolean) SPUtil.get(this, IConstants.IS_LOGIN, false)) == true) {
                    if (SPUtil.get(this, IConstants.TYPE, "").equals("dealer") || SPUtil.get(this, IConstants.TYPE, "").equals("designer"))
                        startActivity(new Intent(this, MineManualActivity.class));
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.ll_super_toilet_seat:
                //一体超感座便器
                listWithoutSelection(1);
                break;
            case R.id.ll_toilet_seat:
                //座便器
                listWithoutSelection(2);
                break;
            case R.id.ll_toilet_seat_cover:
                //座便器盖板
                listWithoutSelection(4);
                break;
            case R.id.ll_qing_shu_bao_toilet_seat:
                //C³清舒宝智能座便盖
                listWithoutSelection(3);
                break;
            case R.id.ll_shower_room_parts:
                //浴室配件
                listWithoutSelection(13);
                break;
            case R.id.ll_ceramic_tile:
                //瓷砖石材
                listWithoutSelection(14);
                break;
            case R.id.ll_makeup:
                //梳妆
                listWithoutSelection(7);
                break;
            case R.id.ll_shower_room_nozzle:
                //浴室龙头
                listWithoutSelection(5);
                break;
            case R.id.ll_shower_nozzle:
                //淋浴龙头
                listWithoutSelection(6);
                break;
            case R.id.ll_shower_room:
                //淋浴房
                listWithoutSelection(10);
                break;
            case R.id.ll_steam_equipment:
                //蒸汽设备
                listWithoutSelection(11);
                break;
            case R.id.ll_bathtub:
                //浴缸
                listWithoutSelection(9);
                break;
            case R.id.ll_massage_bathtub:
                //按摩浴缸
                listWithoutSelection(8);
                break;
            case R.id.ll_toilet_heater:
                //浴室净•暖机（浴霸）
                listWithoutSelection(15);
                break;
            case R.id.ll_commercial_products:
                //商用产品
                listWithoutSelection(12);
                break;
            case R.id.bt_home:
                switchFragment(mHomeFragment).commit();
                //沉浸式状态栏初始化黑色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
                FragmentSelect(1);
                view_line.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_whole_category:
                switchFragment(mWholeCategoryFragment).commit();
                //沉浸式状态栏初始化白色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(false).init();
                FragmentSelect(0);
                view_line.setVisibility(View.GONE);
                break;
            case R.id.bt_ar:
                switchFragment(mARFragment).commit();
                //沉浸式状态栏初始化黑色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
                FragmentSelect(1);
                view_line.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_account:
                switchFragment(mAccountFragment).commit();
                //沉浸式状态栏初始化黑色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
                FragmentSelect(1);
                view_line.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
