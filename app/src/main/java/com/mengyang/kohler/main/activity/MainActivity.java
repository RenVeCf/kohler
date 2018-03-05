package com.mengyang.kohler.main.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
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

import com.gyf.barlibrary.ImmersionBar;
import com.kohler.arscan.DownloadActivity;
import com.kohler.arscan.UnityPlayerActivity;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.account.activity.LoginActivity;
import com.mengyang.kohler.account.fragment.AccountFragment;
import com.mengyang.kohler.ar.ARFragment;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.LogUtils;
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
    private int mFlag = 0;

    private int[] arr = {1, 2, 3};

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
        mIsUnableToDrag = true;
        //        cvpMainViewpager.setScanScroll(false);

        //        //加载adapter
        //        cvpMainViewpager.setAdapter(new MyAdapter(getSupportFragmentManager(), setfargment()));
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
        }else {
            rlMain.StopSlide();
        }
        return super.dispatchTouchEvent(ev);
    }


    @OnClick({R.id.ll_whole_category, R.id.ll_toilet_heater, R.id.ll_nearby_shops, R.id.ll_account_manual, R.id.ll_super_toilet_seat, R.id.ll_toilet_seat, R.id.ll_toilet_seat_cover, R.id.ll_qing_shu_bao_toilet_seat, R.id.ll_shower_room_parts, R.id.ll_ceramic_tile, R.id.ll_makeup, R.id.ll_shower_room_nozzle, R.id.ll_shower_nozzle, R.id.ll_shower_room, R.id.ll_steam_equipment, R.id.ll_bathtub, R.id.ll_massage_bathtub, R.id.ll_commercial_products, R.id.bt_home, R.id.bt_whole_category, R.id.bt_ar, R.id.bt_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_nearby_shops:
                startActivity(new Intent(this, StoreMapActivity.class));
                break;
            case R.id.ll_account_manual:
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
            case R.id.bt_home:
                switchFragment(mHomeFragment).commit();
                //沉浸式状态栏初始化黑色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
                FragmentSelect(1);
                mIsUnableToDrag = false;
                view_line.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_whole_category:
                switchFragment(mWholeCategoryFragment).commit();
                //沉浸式状态栏初始化白色
                ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(false).init();
                FragmentSelect(0);
                mIsUnableToDrag = true;
                view_line.setVisibility(View.GONE);
                break;
            case R.id.bt_ar:
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
            if (false == allGranted) {
//                showMissingPermissionDialog();
            }
        }
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

    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-\"权限\"-打开所需权限。");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton("设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    @OnClick()
    public void onViewClicked() {
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != null && fragment.isVisible()) {
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
}
