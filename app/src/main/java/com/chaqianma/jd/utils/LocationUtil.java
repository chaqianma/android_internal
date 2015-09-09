package com.chaqianma.jd.utils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.LocationSource;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.LocationInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxd on 2015/7/20.
 * 高德地图
 */
public class LocationUtil {
    private Context mContext = null;
    private LocationManagerProxy mLocationManagerProxy = null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LocationInfo locationInfo = AppData.getInstance().getLocationInfo();
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("location", locationInfo.getLongitude() + "," + locationInfo.getLatitude()));
            HttpClientUtil.put(mContext, HttpRequestURL.uploadLocationUrl, formparams, new JDHttpResponseHandler(null, new ResponseHandler() {
                @Override
                public void onSuccess(Object o) {
                    String sss = "1";
                }
            }));
        }
    };

    public LocationUtil(Context context) {
        this.mContext = context;
        getLocation();
    }

    private void getLocation() {
        mLocationManagerProxy = LocationManagerProxy.getInstance(mContext);
        //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
        //在定位结束后，在合适的生命周期调用destroy()方法
        //其中如果间隔时间为-1，则定位只定一次   30 * 60 * 1000
        mLocationManagerProxy.setGpsEnable(true);
        // 2 位置变化的通知时间，单位为毫秒   3 位置变化通知距离，单位为米
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, 30 * 60 * 1000, 5000, mapLocationListener);
    }

    /**
     * 地图监听
     */
    private AMapLocationListener mapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0) {
                //获取位置信息
                LocationInfo locationInfo = new LocationInfo();
                locationInfo.setLatitude(aMapLocation.getLatitude());
                locationInfo.setLongitude(aMapLocation.getLongitude());
                AppData.getInstance().setLocationInfo(locationInfo);
                mHandler.sendEmptyMessage(0);
            }
        }

        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    /**
     * 停止定位
     */
    public void stopLocation() {
        if (mLocationManagerProxy != null) {
            // 移除定位请求
            mLocationManagerProxy.removeUpdates(mapLocationListener);
            // 销毁定位
            mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy = null;
    }
}
