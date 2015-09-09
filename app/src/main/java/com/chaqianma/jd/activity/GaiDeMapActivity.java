package com.chaqianma.jd.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.BaseAdapter;

import com.amap.api.location.core.GeoPoint;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.chaqianma.jd.R;
import com.chaqianma.jd.common.Constants;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxd on 2015/9/8.
 */
public class GaiDeMapActivity extends BaseActivity implements AMap.OnMapClickListener {
    @InjectView(R.id.view_gaide_map)
    MapView mMapView;
    private AMap mAMap;
    private double mLatitude = .0d;
    private double mLongitude = .0d;
    //借款人
    private String mBorrowName = null;
    //定位位置显示
    private Marker mGMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_baidu_map);
        ButterKnife.inject(this);
        initData();
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
        mAMap.setOnMapClickListener(this);
        MarkerOptions markOptions = new MarkerOptions();
        markOptions.icon(
                BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),
                                R.mipmap.maker)))
                .anchor(0.5f, 0.5f);
        markOptions.visible(true);
        //markOptions.position(new LatLng(mLatitude, mLongitude));
        //mAMap.addMarker(markOptions);
        mGMarker = mAMap.addMarker(markOptions);
        mGMarker.setPosition(new LatLng(mLatitude, mLongitude));
        mGMarker.setTitle(mBorrowName);
        //mGPSMarker.showInfoWindow();
        CameraUpdate cameraUpdate = CameraUpdateFactory.changeLatLng(new LatLng(mLatitude, mLongitude));
        mAMap.moveCamera(cameraUpdate);
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(15));


    }

    /**
     * 初始化传值数据
     */
    private void initData() {
        Bundle bundle = getIntent().getBundleExtra(Constants.TOVALUEKEY);
        if (bundle != null) {
            String location = bundle.getString(Constants.LOCATION);
            mBorrowName = bundle.getString(Constants.BORROWNAME);
            if (location != null && location.length() > 0 && location.indexOf(",") >= 0) {
                try {
                    String[] arrs = location.split(",");
                    //经度
                    mLatitude = Double.parseDouble(arrs[1]);
                    //纬度
                    mLongitude = Double.parseDouble(arrs[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
       // mAMap.setLocationSource(this);// 设置定位监听
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    /**
     * 地图点击事件
     */
    @Override
    public void onMapClick(LatLng latLng) {
       /* MarkerOptions markerOptions = new MarkerOptions();
        // 设置Marker的图标样式
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker));
        // 设置Marker点击之后显示的标题
        markerOptions.title("第" + (markerCounts + 1) + "个Marker");
        // 设置Marker的坐标，为我们点击地图的经纬度坐标
        markerOptions.position(latLng);
        // 设置Marker的可见性
        markerOptions.visible(true);
        // 设置Marker是否可以被拖拽，这里先设置为false，之后会演示Marker的拖拽功能
        markerOptions.draggable(false);
        // 将Marker添加到地图上去
        mAMap.addMarker(markerOptions);
        // Marker的计数器自增
        markerCounts++;*/

        if(mGMarker.isInfoWindowShown())
            mGMarker.hideInfoWindow();
        else
            mGMarker.showInfoWindow();
    }
}
