package com.mengyang.kohler.home.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.webkit.PermissionRequest;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.view.TopView;

import java.security.Permission;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class ShopsListActivity extends BaseActivity {
    @BindView(R.id.tv_shops_list_top)
    TopView tvShopsListTop;
    @BindView(R.id.mv_shops_list)
    MapView mvShopsList;
    @BindView(R.id.bt_shops_list)
    Button btShopsList;
    AMap aMap = null;

    AMapLocationClient mAMapLocationClient = null;
    AMapLocationClientOption mAMapLocationClientOption = null;

    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    private boolean isNeedCheck = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shops_list;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvShopsListTop);
        mvShopsList.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mvShopsList.getMap();
        }
        //        MyLocationStyle myLocationStyle;
        //        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        //        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        //        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        //        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        //        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
        //            @Override
        //            public void onMyLocationChange(Location location) {
        //                //从location对象中获取经纬度信息，地址描述信息，建议拿到位置之后调用逆地理编码接口获取（获取地址描述数据章节有介绍）
        //                LogUtils.i("location = " + location);
        //            }
        //        });
        mAMapLocationClient = new AMapLocationClient(App.getContext());
        mAMapLocationClientOption = new AMapLocationClientOption();
        mAMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mAMapLocationClientOption.setGpsFirst(false);
        mAMapLocationClientOption.setHttpTimeOut(10000);
        mAMapLocationClientOption.setInterval(2000);
        mAMapLocationClientOption.setNeedAddress(true);
        mAMapLocationClientOption.setOnceLocation(true);
        mAMapLocationClientOption.setOnceLocationLatest(true);
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        mAMapLocationClient.setLocationOption(mAMapLocationClientOption);
        mAMapLocationClient.startLocation();
    }

    @Override
    protected void initListener() {
        mAMapLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        aMapLocation.getLocationType();
                        aMapLocation.getLatitude();
                        aMapLocation.getLongitude();
                        aMapLocation.getAccuracy();
                        aMapLocation.getAccuracy();
                        aMapLocation.getCountry();
                        aMapLocation.getProvince();
                        String city = aMapLocation.getCity();
                        aMapLocation.getDistrict();
                        aMapLocation.getStreet();
                        aMapLocation.getStreetNum();
                        aMapLocation.getCityCode();
                        aMapLocation.getAdCode();
                        aMapLocation.getAoiName();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(aMapLocation.getTime());
                        df.format(date);
                        ToastUtil.showToast(city);
                    } else {
                        LogUtils.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                    mAMapLocationClient.stopLocation();
                }
            }
        });
    }


    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mvShopsList.onResume();
//        if (isNeedCheck) {
//            checkPermissions(needPermissions);
//        }
    }

//    private void checkPermissions(String... permissions) {
//        List<String> needRequestPermissionsList = findDeniedPermissions(permissions);
//        if (null != needRequestPermissionsList && needRequestPermissionsList.size() > 0) {
//            ActivityCompat.requestPermissions(this, needRequestPermissionsList.toArray(new String[needRequestPermissionsList.size()]), PERMISSION_REQUESTCODE);
//        }
//    }
//
//    private List<String> findDeniedPermissions(String[] permissions) {
//        List<String> needRequestPermissionsList = new ArrayList<String>();
//        for (String perm : permissions) {
//            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED || ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
//                needRequestPermissionsList.add(perm);
//            }
//        }
//        return needRequestPermissionsList;
//    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mvShopsList.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mvShopsList.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mvShopsList.onDestroy();
        mAMapLocationClient.onDestroy();
    }
}
