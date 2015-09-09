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
    //�����
    private String mBorrowName = null;
    //��λλ����ʾ
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
     * ��ʼ����ֵ����
     */
    private void initData() {
        Bundle bundle = getIntent().getBundleExtra(Constants.TOVALUEKEY);
        if (bundle != null) {
            String location = bundle.getString(Constants.LOCATION);
            mBorrowName = bundle.getString(Constants.BORROWNAME);
            if (location != null && location.length() > 0 && location.indexOf(",") >= 0) {
                try {
                    String[] arrs = location.split(",");
                    //����
                    mLatitude = Double.parseDouble(arrs[1]);
                    //γ��
                    mLongitude = Double.parseDouble(arrs[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * ����һЩamap������
     */
    private void setUpMap() {
       // mAMap.setLocationSource(this);// ���ö�λ����
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);// ����Ĭ�϶�λ��ť�Ƿ���ʾ
        mAMap.setMyLocationEnabled(true);// ����Ϊtrue��ʾ��ʾ��λ�㲢�ɴ�����λ��false��ʾ���ض�λ�㲢���ɴ�����λ��Ĭ����false
        // ���ö�λ������Ϊ��λģʽ �������ɶ�λ��������ͼ������������ת����
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
     * ��ͼ����¼�
     */
    @Override
    public void onMapClick(LatLng latLng) {
       /* MarkerOptions markerOptions = new MarkerOptions();
        // ����Marker��ͼ����ʽ
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker));
        // ����Marker���֮����ʾ�ı���
        markerOptions.title("��" + (markerCounts + 1) + "��Marker");
        // ����Marker�����꣬Ϊ���ǵ����ͼ�ľ�γ������
        markerOptions.position(latLng);
        // ����Marker�Ŀɼ���
        markerOptions.visible(true);
        // ����Marker�Ƿ���Ա���ק������������Ϊfalse��֮�����ʾMarker����ק����
        markerOptions.draggable(false);
        // ��Marker��ӵ���ͼ��ȥ
        mAMap.addMarker(markerOptions);
        // Marker�ļ���������
        markerCounts++;*/

        if(mGMarker.isInfoWindowShown())
            mGMarker.hideInfoWindow();
        else
            mGMarker.showInfoWindow();
    }
}
