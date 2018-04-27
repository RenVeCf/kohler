package com.mengyang.kohler.main.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Toast;

import com.allyes.analytics.AIOAnalytics;
import com.gyf.barlibrary.ImmersionBar;
import com.kohler.arscan.DownloadActivity;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.account.activity.LoginActivity;
import com.mengyang.kohler.account.fragment.AccountFragment;
import com.mengyang.kohler.ar.ARFragment;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.AppUtils;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.view.ResideLayout;
import com.mengyang.kohler.home.activity.MineManualActivity;
import com.mengyang.kohler.home.activity.StoreMapActivity;
import com.mengyang.kohler.home.fragment.HomeFragment;
import com.mengyang.kohler.main.view.CommonProgressDialog;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.NotSelectClassificationBean;
import com.mengyang.kohler.module.bean.UserMsgBean;
import com.mengyang.kohler.whole_category.activity.CommodityClassificationActivity;
import com.mengyang.kohler.whole_category.fragment.WholeCategoryFragment;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener, HomeFragment.HandleViewPager {
    private static final int PERMISSON_REQUESTCODE = 0;
    private String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.WAKE_LOCK,
    };
    @BindView(R.id.iv_whole_category)
    ImageView ivWholeCategory;
    @BindView(R.id.ll_whole_category)
    LinearLayout llWholeCategory;
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
    @BindView(R.id.ll_whole_cabinet)
    LinearLayout llWholeCabinet;
    @BindView(R.id.ll_kitchen_sink)
    LinearLayout llKitchenSink;
    @BindView(R.id.ll_kitchen_faucet)
    LinearLayout llKitchenFaucet;
    @BindView(R.id.ll_water_purification)
    LinearLayout llWaterPurification;
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
    private long exitTime;
    private boolean mIsUnableToDrag = true;
    private PopupWindow mNoJurisdictionPopupWindow;
    private View mPopLayout;
    private CommonProgressDialog pBar;
    private int mFlag = 0;

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
        //            Map<String, Object> stringMap = IdeaApi.getSign();
        //
        //            IdeaApi.getRequestLogin(stringMap);
        //            IdeaApi.getApiService()
        //                    .getUserMsg(stringMap)
        //                    .compose(this.<BasicResponse<UserMsgBean>>bindToLifecycle())
        //                    .subscribeOn(Schedulers.io())
        //                    .observeOn(AndroidSchedulers.mainThread())
        //                    .subscribe(new DefaultObserver<BasicResponse<UserMsgBean>>(this, false) {
        //                        @Override
        //                        public void onSuccess(BasicResponse<UserMsgBean> response) {
        // 获取本版本号，是否更新
        int vision = AppUtils.getVersionCode(MainActivity.this);
        getVersion(vision);
        //                        }
        //                    });
        //        }
        SPUtil.put(this, IConstants.FIRST_APP, false);
        switchFragment(mHomeFragment).commit();
        mIsUnableToDrag = true;
        mNoJurisdictionPopupWindow = new PopupWindow(this);
        mNoJurisdictionPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mNoJurisdictionPopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater = LayoutInflater.from(App.getContext());
        mPopLayout = inflater.inflate(R.layout.popup_window_no_jurisdictuon, null);
        mNoJurisdictionPopupWindow.setContentView(mPopLayout);
        mNoJurisdictionPopupWindow.setBackgroundDrawable(new ColorDrawable(0x4c000000));
        mNoJurisdictionPopupWindow.setOutsideTouchable(false);
        mNoJurisdictionPopupWindow.setFocusable(true);

        checkPermissions();//检查权限

        mHomeFragment.setHandleListenning(this);
        rlMain.setPanelSlideListener(new ResideLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelOpened(View panel) {
                if (mHomeFragment != null) {
                    mHomeFragment.stopViewPager();
                }
            }

            @Override
            public void onPanelClosed(View panel) {
                if (mHomeFragment != null) {
                    mHomeFragment.startViewPager();
                }
            }
        });

        AIOAnalytics.onInit(this);
        AIOAnalytics.onResume();
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
            Map<String, Object> stringMap = IdeaApi.getSign();

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

    @Override
    protected void onPause() {
        super.onPause();
        AIOAnalytics.onPause();
    }

    private void listWithoutSelection(final int position) {
        Map<String, Object> stringMap = IdeaApi.getSign();

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
                            startActivity(new Intent(MainActivity.this, CommodityClassificationActivity.class).putExtra("id", response.getData().get(position).getId() + "").putExtra("classification_title", response.getData().get(position).getNameCn()).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                    }
                });
    }

    @Override
    public void onFragmentInteraction(String data) {
        if (rlMain != null) {
            if ("topView".equals(data) && rlMain.isOpen()) {
                rlMain.closePane();
            } else {
                if (rlMain.isOpen()) {
                    rlMain.closePane();
                } else {
                    rlMain.openPane();
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //只有home界面可以侧滑
        Fragment fragment = getVisibleFragment();
        if (fragment instanceof HomeFragment) {
            rlMain.StartSlide();
        } else {
            rlMain.StopSlide();
        }
        return super.dispatchTouchEvent(ev);
    }


    @OnClick({R.id.ll_whole_category, R.id.ll_toilet_heater, R.id.ll_nearby_shops, R.id.ll_account_manual, R.id.ll_super_toilet_seat, R.id.ll_toilet_seat, R.id.ll_toilet_seat_cover, R.id.ll_qing_shu_bao_toilet_seat, R.id.ll_shower_room_parts, R.id.ll_ceramic_tile, R.id.ll_makeup, R.id.ll_shower_room_nozzle, R.id.ll_shower_nozzle, R.id.ll_shower_room, R.id.ll_steam_equipment, R.id.ll_bathtub, R.id.ll_massage_bathtub, R.id.ll_commercial_products, R.id.ll_whole_cabinet, R.id.ll_kitchen_sink, R.id.ll_kitchen_faucet, R.id.ll_water_purification, R.id.bt_home, R.id.bt_whole_category, R.id.bt_ar, R.id.bt_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_nearby_shops:
                AIOAnalytics.onEvent("fujindianpu");
                startActivity(new Intent(this, StoreMapActivity.class));
                break;
            case R.id.ll_account_manual:
                AIOAnalytics.onEvent("tuce");
                if (((boolean) SPUtil.get(this, IConstants.IS_LOGIN, false)) == true) {
                    if (SPUtil.get(this, IConstants.TYPE, "").equals("dealer") || SPUtil.get(this, IConstants.TYPE, "").equals("designer")) {
                        startActivity(new Intent(this, MineManualActivity.class));
                    } else {
                        if (Build.VERSION.SDK_INT == 24) {//android7.0需要单独做适配
                            mNoJurisdictionPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, getStatusBarHeight());
                        } else {
                            mNoJurisdictionPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
                        }
                    }
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
            case R.id.ll_whole_cabinet:
                //整体橱柜
                listWithoutSelection(16);
                break;
            case R.id.ll_kitchen_sink:
                //厨盆
                listWithoutSelection(17);
                break;
            case R.id.ll_kitchen_faucet:
                //厨房龙头
                listWithoutSelection(18);
                break;
            case R.id.ll_water_purification:
                //净水
                listWithoutSelection(19);
                break;
            case R.id.bt_home:
                AIOAnalytics.onEvent("index");
                switchFragment(mHomeFragment).commit();
                //沉浸式状态栏初始化黑色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
                FragmentSelect(1);
                mIsUnableToDrag = false;
                view_line.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_whole_category:
                AIOAnalytics.onEvent("category");
                switchFragment(mWholeCategoryFragment).commit();
                //沉浸式状态栏初始化白色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(false).init();
                FragmentSelect(0);
                mIsUnableToDrag = true;
                view_line.setVisibility(View.GONE);
                break;
            case R.id.bt_ar:
                AIOAnalytics.onEvent("arsaoyisao");
                MobclickAgent.onEvent(MainActivity.this, "arsaoyisao");
                switchFragment(mARFragment).commit();
                //沉浸式状态栏初始化黑色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
                FragmentSelect(1);
                mIsUnableToDrag = true;
                view_line.setVisibility(View.VISIBLE);
                Intent intent = new Intent(this, DownloadActivity.class);
                intent.putExtra("way", "arscan");
                startActivityForResult(intent, IConstants.DELETE_REQUESTCODE);
                break;
            case R.id.bt_account:
                AIOAnalytics.onEvent("zhanghu");
                switchFragment(mAccountFragment).commit();
                //沉浸式状态栏初始化黑色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
                FragmentSelect(1);
                mIsUnableToDrag = true;
                view_line.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_whole_category:
                if (mFlag == 0) {
                    ivWholeCategory.setImageDrawable(getResources().getDrawable(R.mipmap.arrow));
                    llSuperToiletSeat.setVisibility(View.GONE);
                    llToiletSeat.setVisibility(View.GONE);
                    llToiletSeatCover.setVisibility(View.GONE);
                    llQingShuBaoToiletSeat.setVisibility(View.GONE);
                    llShowerRoomParts.setVisibility(View.GONE);
                    llCeramicTile.setVisibility(View.GONE);
                    llMakeup.setVisibility(View.GONE);
                    llShowerRoomNozzle.setVisibility(View.GONE);
                    llShowerNozzle.setVisibility(View.GONE);
                    llSteamEquipment.setVisibility(View.GONE);
                    llBathtub.setVisibility(View.GONE);
                    llMassageBathtub.setVisibility(View.GONE);
                    llToiletHeater.setVisibility(View.GONE);
                    llCommercialProducts.setVisibility(View.GONE);
                    llShowerRoom.setVisibility(View.GONE);
                    llWholeCabinet.setVisibility(View.GONE);
                    llKitchenSink.setVisibility(View.GONE);
                    llKitchenFaucet.setVisibility(View.GONE);
                    llWaterPurification.setVisibility(View.GONE);
                    mFlag = 1;
                } else {
                    ivWholeCategory.setImageDrawable(getResources().getDrawable(R.mipmap.arrow_down));
                    llSuperToiletSeat.setVisibility(View.VISIBLE);
                    llToiletSeat.setVisibility(View.VISIBLE);
                    llToiletSeatCover.setVisibility(View.VISIBLE);
                    llQingShuBaoToiletSeat.setVisibility(View.VISIBLE);
                    llShowerRoomParts.setVisibility(View.VISIBLE);
                    llCeramicTile.setVisibility(View.VISIBLE);
                    llMakeup.setVisibility(View.VISIBLE);
                    llShowerRoomNozzle.setVisibility(View.VISIBLE);
                    llShowerNozzle.setVisibility(View.VISIBLE);
                    llSteamEquipment.setVisibility(View.VISIBLE);
                    llBathtub.setVisibility(View.VISIBLE);
                    llMassageBathtub.setVisibility(View.VISIBLE);
                    llToiletHeater.setVisibility(View.VISIBLE);
                    llCommercialProducts.setVisibility(View.VISIBLE);
                    llShowerRoom.setVisibility(View.VISIBLE);
                    llWholeCabinet.setVisibility(View.VISIBLE);
                    llKitchenSink.setVisibility(View.VISIBLE);
                    llKitchenFaucet.setVisibility(View.VISIBLE);
                    llWaterPurification.setVisibility(View.VISIBLE);
                    mFlag = 0;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            switch (requestCode) {
                case IConstants.DELETE_REQUESTCODE:
                    switchFragment(mHomeFragment).commit();
                    //沉浸式状态栏初始化黑色
                    ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
                    FragmentSelect(1);
                    view_line.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                //                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSON_REQUESTCODE) {
            boolean allGranted = true;
            for (int i = 0; i < permissions.length; ++i) {
                String perm = permissions[i];
                int req = grantResults[i];

                if (req == PackageManager.PERMISSION_GRANTED) {
                    Log.i("cohler", "onRequestPermissionsResult [" + perm + "]: PERMISSION_GRANTED");

                } else {
                    Log.i("cohler", "onRequestPermissionsResult [" + perm + "]: PERMISSION_DENIED");
                }

                if (req == PackageManager.PERMISSION_DENIED) {// 权限被用户拒绝
                    allGranted = false;
                    break;
                }
            }
        }
    }

    @OnClick()
    public void onViewClicked() {
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible()) {
                return fragment;
            }
        }
        return null;
    }

    @Override
    public boolean handleListenning() {

        if (rlMain != null && rlMain.isOpen()) {
            rlMain.closePane();
            return true;
        }
        return false;
    }

    /**
     * 数据统计需要的动态权限
     */
    private void checkPermissions() {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : needPermissions) {
            if (ContextCompat.checkSelfPermission(this, perm) == PackageManager.PERMISSION_GRANTED) {
                Log.i("cohler", "check permission [" + perm + "]: PERMISSION_GRANTED");//有权限了
            } else {
                Log.i("cohler", "check permission [" + perm + "]: PERMISSION_DENIED");//没有权限
                //判断是否需要显示申请原因
                if (true == ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                    //ConsoleLog.debug("shouldShowRequestPermissionRationale == true");
                } else {
                    //ConsoleLog.debug("shouldShowRequestPermissionRationale == false");
                }
                needRequestPermissonList.add(perm);
            }
        }

        if (needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AIOAnalytics.onPause();
    }

    // 获取更新版本号
    private void getVersion(final int vision) {
        String newversion = "2";//更新新的版本号
        String content = "\n" + "科勒应用有新的版本。\n";//更新内容
        String url = "http://openbox.mobilem.360.cn/index/d/sid/3976114";//安装包下载地址

        double newversioncode = Double
                .parseDouble(newversion);
        int cc = (int) (newversioncode);

        System.out.println(newversion + "v" + vision + ",,"
                + cc);
        if (cc != vision) {
            if (vision < cc) {
                System.out.println(newversion + "v"
                        + vision);
                // 版本号不同
                ShowDialog(vision, newversion, content, url);
            }
        }
    }

    /**
     * 升级系统
     *
     * @param content
     * @param url
     */
    private void ShowDialog(int vision, String newversion, String content,
                            final String url) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("版本更新")
                .setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        pBar = new CommonProgressDialog(MainActivity.this);
                        pBar.setIndeterminate(true);
                        pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        pBar.setCancelable(false);
                        // downFile(URLData.DOWNLOAD_URL);
                        final DownloadTask downloadTask = new DownloadTask(
                                MainActivity.this);
                        downloadTask.execute(url);
                        pBar.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                downloadTask.cancel(true);
                            }
                        });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 下载应用
     *
     * @author Administrator
     */
    class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            File file = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                // expect HTTP 200 OK, so we don't mistakenly save error
                // report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP "
                            + connection.getResponseCode() + " "
                            + connection.getResponseMessage();
                }
                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    file = new File(MainActivity.this.getExternalCacheDir().getPath() + File.separator + "app",
                            IConstants.DOWNLOAD_NAME);

                    if (!file.exists()) {
                        // 判断父文件夹是否存在
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "sd卡未挂载",
                            Toast.LENGTH_LONG).show();
                }
                input = connection.getInputStream();
                output = new FileOutputStream(file);
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
                return e.toString();

            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }
                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            pBar.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            pBar.setIndeterminate(false);
            pBar.setMax(100);
            pBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            pBar.dismiss();
            update();
        }
    }

    private void update() {
        //安装应用
        String apkPath = MainActivity.this.getExternalCacheDir().getPath() + File.separator + "app" + File.separator;
        if (TextUtils.isEmpty(apkPath)) {
            Toast.makeText(MainActivity.this, "更新失败！未找到安装包", Toast.LENGTH_SHORT).show();
            return;
        }
        File apkFile = new File(apkPath + IConstants.DOWNLOAD_NAME);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Android 7.0 系统共享文件需要通过 FileProvider 添加临时权限，否则系统会抛出 FileUriExposedException .
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(MainActivity.this, "com.mengyang.kohler.fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        MainActivity.this.startActivity(intent);
    }
}
