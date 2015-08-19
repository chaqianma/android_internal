package com.chaqianma.jd.activity;

import android.drm.DrmManagerClient;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.chaqianma.jd.R;
import com.chaqianma.jd.common.*;
import com.chaqianma.jd.model.LocationInfo;
import com.chaqianma.jd.utils.LocationUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxd on 2015/7/26.
 */
public class BaiduMapActivity extends BaseActivity {
    @InjectView(R.id.view_baidu_map)
    MapView mMapView;
    private BaiduMap mBaiduMap = null;
    boolean isFirstLoc = true;// 是否首次定位
    private BitmapDescriptor mIconMaker = null;
    private LocationUtil mLocationUtil = null;
    private double mLatitude = .0d;
    private double mLongitude = .0d;
    private String borrowName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_baidu_map);
        ButterKnife.inject(this);
        Bundle bundle = getIntent().getBundleExtra(Constants.TOVALUEKEY);
        if (bundle != null) {
            String location = bundle.getString(Constants.LOCATION);
            borrowName = bundle.getString(Constants.BORROWNAME);
            if (location != null && location.length() > 0 && location.indexOf(",") >= 0) {
                try {
                    String[] arrs = location.split(",");
                    //经度
                    mLongitude = Double.parseDouble(arrs[0]);
                    //纬度
                    mLatitude = Double.parseDouble(arrs[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        initMap();
        addOverlay();
        setTopBarState("待处理尽调", true);
    }

    private void initMap() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mIconMaker = BitmapDescriptorFactory.fromResource(R.mipmap.maker);
    }

    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.GETLOCATION:
                    LocationInfo locationInfo = AppData.getInstance().getLocationInfo();
                    if (locationInfo != null && isFirstLoc) {
                        isFirstLoc = false;
                        addOverlay(locationInfo);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public void addOverlay() {
        mBaiduMap.clear();
        // 位置
        LatLng latLng = new LatLng(mLatitude, mLongitude);
        //LatLng latLng = new LatLng(32.142425, 118.996925);
        // 图标
        OverlayOptions overlayOptions = new MarkerOptions().position(latLng)
                .icon(mIconMaker).zIndex(5);
        Marker marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", borrowName);
        marker.setExtraInfo(bundle);
        // 将地图移到到最后一个经纬度位置
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(u);
        mBaiduMap.setOnMarkerClickListener(new MyMarkerClickListener());
    }

    public void addOverlay(LocationInfo locationInfo) {
        mBaiduMap.clear();
        // 位置
        // latLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        LatLng latLng = new LatLng(32.142425, 118.996925);
        // 图标
        OverlayOptions overlayOptions = new MarkerOptions().position(latLng)
                .icon(mIconMaker).zIndex(5);
        Marker marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", borrowName);
        marker.setExtraInfo(bundle);
        // 将地图移到到最后一个经纬度位置
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(u);
        mBaiduMap.setOnMarkerClickListener(new MyMarkerClickListener());
    }

    private class MyMarkerClickListener implements BaiduMap.OnMarkerClickListener {
        @Override
        public boolean onMarkerClick(final Marker marker) {
            // 获得marker中的数据
            String info = (String) marker.getExtraInfo().get("info");
            InfoWindow mInfoWindow;
            // 生成一个TextView用户在地图中显示InfoWindow
            TextView tv = new TextView(BaiduMapActivity.this);
            tv.setBackgroundResource(R.mipmap.location_tips);
            tv.setPadding(30, 20, 30, 50);
            tv.setText(info);
            // 将marker所在的经纬度的信息转化成屏幕上的坐标
            final LatLng ll = marker.getPosition();
            /*Point p = mBaiduMap.getProjection().toScreenLocation(ll);
            p.y -= 47;
           LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);*/
            InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
                public void onInfoWindowClick() {
                    LatLng ll = marker.getPosition();
                    LatLng llNew = new LatLng(ll.latitude + 0.005,
                            ll.longitude + 0.005);
                    //marker.setPosition(llNew);
                    mBaiduMap.hideInfoWindow();
                }
            };
            mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(tv), ll, -47, listener);
            // 显示InfoWindow
            mBaiduMap.showInfoWindow(mInfoWindow);
            return true;
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        if (mLocationUtil != null)
            mLocationUtil.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}
