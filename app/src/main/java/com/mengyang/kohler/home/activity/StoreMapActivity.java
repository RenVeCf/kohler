package com.mengyang.kohler.home.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.view.PoiOverlay;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.baidu.mapapi.utils.DistanceUtil.getDistance;

/**
 * 门店列表
 */

public class StoreMapActivity extends BaseActivity {
    @BindView(R.id.tv_store_map_top)
    TopView tvStoreMapTop;
    @BindView(R.id.bt_store_map)
    Button btStoreMap;
    @BindView(R.id.map_view)
    MapView mapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient = null;
    //是否第一次定位，如果是第一次定位的话要将自己的位置显示在地图 中间
    private boolean isFirstLocation = true;
    private Marker mMarker; //坐标气球

    @Override
    protected int getLayoutId() {
        SDKInitializer.initialize(getApplicationContext());
        return R.layout.activity_store_map;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvStoreMapTop);
        //百度地图
        mBaiduMap = mapView.getMap();
        // 不显示缩放比例尺
        mapView.showZoomControls(false);
        // 不显示百度地图Logo
        mapView.removeViewAt(1);

        // 改变地图状态，使地图显示在恰当的缩放大小
        MapStatus mMapStatus = new MapStatus.Builder().zoom(15).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);

        mLocationClient = new LocationClient(getApplicationContext());//声明LocationClient类
    }

    @Override
    protected void initListener() {
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
                //以下只列举部分获取经纬度相关（常用）的结果信息
                //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

                double latitude = bdLocation.getLatitude();    //获取纬度信息
                double longitude = bdLocation.getLongitude();    //获取经度信息
                float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f

                String coorType = bdLocation.getCoorType();
                //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

                int errorCode = bdLocation.getLocType();
                //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

                String addr = bdLocation.getAddrStr();    //获取详细地址信息
                String country = bdLocation.getCountry();    //获取国家
                String province = bdLocation.getProvince();    //获取省份
                String city = bdLocation.getCity();    //获取城市
                String district = bdLocation.getDistrict();    //获取区县
                String street = bdLocation.getStreet();    //获取街道信息

                String locationDescribe = bdLocation.getLocationDescribe();    //获取位置描述信息

                List<Poi> poiList = bdLocation.getPoiList();
                //获取周边POI信息
                //POI信息包括POI ID、名称等，具体信息请参照类参考中POI类的相关说明

                //将获取的location信息给百度map
                MyLocationData data = new MyLocationData.Builder()
                        .accuracy(bdLocation.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100)
                        .latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude())
                        .build();
                mBaiduMap.setMyLocationData(data);
                if (isFirstLocation) {
                    //获取经纬度
                    LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                    MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(ll);
                    //mBaiduMap.setMapStatus(status);//直接到中间
                    mBaiduMap.animateMapStatus(status);//动画的方式到中间
                    //isFirstLocation = false; //如果要设置一个按钮（点一下就回到定位中心点的那种），就打开，再在按钮事件里写 isFirstLocation = true;
                    //                    showInfo("位置：" + bdLocation.getAddrStr());
                }
            }
        });
    }


    @Override
    protected void initData() {
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(1000);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        option.setIsNeedLocationPoiList(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true

        mLocationClient.setLocOption(option);

//        /**
//         * 绘制Marker，地图上常见的类似气球形状的图层
//         */
//        MarkerOptions markerOptions = new MarkerOptions();//参数设置类
//        markerOptions.position(经纬度);//marker坐标位置
//        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round);
//        markerOptions.icon(bitmap);//marker图标，可以自定义
//        markerOptions.draggable(false);//是否可拖拽，默认不可拖拽
//        markerOptions.anchor(0.5f, 1.0f);//设置 marker覆盖物与位置点的位置关系，默认（0.5f, 1.0f）水平居中，垂直下对齐
//        markerOptions.alpha(0.8f);//marker图标透明度，0~1.0，默认为1.0
//        markerOptions.animateType(MarkerOptions.MarkerAnimateType.drop);//marker出现的方式，从天上掉下
//        markerOptions.flat(false);//marker突变是否平贴地面
//        markerOptions.zIndex(1);//index
//        mMarker = (Marker) mBaiduMap.addOverlay(markerOptions);//在地图上增加mMarker图层
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted()) {//如果定位client没有开启，开启定位
            mLocationClient.start();
            //mLocationClient为第二步初始化过的LocationClient对象
            //调用LocationClient的start()方法，便可发起定位请求
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //关闭定位
        mBaiduMap.setMyLocationEnabled(false);
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        MapView.setMapCustomEnable(false);
        mapView = null;
    }


    @OnClick({R.id.bt_store_map})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_store_map:
                //测距
//                LatLng start = new LatLng(39.95676, 116.401394);
//                LatLng end = new LatLng(36.63014,114.499574);
//                getDistance(start, end);
                startActivity(new Intent(this, StoreListActivity.class));
                break;
        }
    }
}
