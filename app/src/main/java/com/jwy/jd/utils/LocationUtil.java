package com.jwy.jd.utils;

import android.content.Context;
import android.os.Handler;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jwy.jd.common.AppData;
import com.jwy.jd.common.Constants;
import com.jwy.jd.model.LocationInfo;

/**
 * Created by zhangxd on 2015/7/20.
 */
public class LocationUtil implements Runnable {
    private LocationClient mLocationClient = null;
    private Context mContext = null;
    private Handler mHandler=null;
    private MyBDLocationListener myLocationListener = null;

    public LocationUtil(Context context,Handler handler) {
        this.mContext = context;
        this.mHandler=handler;
    }

    @Override
    public void run() {
        mLocationClient = new LocationClient(mContext);
        myLocationListener = new MyBDLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("gcj02");//返回的定位结果是百度经纬度，默认值gcj02
        option.setScanSpan(5 * 1000);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public void stop() {
        if (mLocationClient != null && mLocationClient.isStarted())
            mLocationClient.stop();
    }

    public void unRegisterLocationListener() {
        if (mLocationClient != null && mLocationClient.isStarted())
            mLocationClient.unRegisterLocationListener(myLocationListener);
    }

    private class MyBDLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            LocationInfo locationInfo = new LocationInfo();
            locationInfo.setLatitude(location.getLatitude() + "");
            locationInfo.setLontitude(location.getLongitude() + "");
            locationInfo.setRadius(location.getRadius() + "");
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                locationInfo.setSpeed(location.getSpeed() + "");
                locationInfo.setSatellite(location.getSatelliteNumber() + "");
                locationInfo.setDirection(location.getDirection() + "");
                locationInfo.setAddress(location.getAddrStr() + "");
                locationInfo.setDirection(location.getDirection() + "");
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                locationInfo.setAddress(location.getAddrStr());
                locationInfo.setOperationers(location.getOperators() + "'");
                locationInfo.setCity(location.getCity());
                locationInfo.setProvince(location.getProvince());
            }
            AppData.getInstance().setLocationInfo(locationInfo);
            mHandler.sendMessage(mHandler.obtainMessage(Constants.GETLOCATION,locationInfo));
            unRegisterLocationListener();
            mLocationClient.stop();
        }
    }
}
