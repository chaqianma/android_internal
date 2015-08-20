package com.chaqianma.jd.utils;

import android.content.Context;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.chaqianma.jd.model.RepaymentInfo;


/**
 * Created by zhangxd on 2015/8/12.
 * <p/>
 * 通过经纬度获取具体地址
 */
public class GeoCoderUtil {

    private TextView tvAddress;
    private String mLocation = null;
    private RepaymentInfo mRepaymentInfo = null;
    private Context mContext = null;

    public GeoCoderUtil(TextView tvAddress, String location) {
        this.tvAddress = tvAddress;
        this.mLocation = location;
        getAddress();
    }

    public GeoCoderUtil(TextView tvAddress, RepaymentInfo repaymentInfo) {
        this.tvAddress = tvAddress;
        this.mLocation = repaymentInfo.getUserWorkLocation();
        this.mRepaymentInfo = repaymentInfo;
        getAddress();
    }

    public void getAddress() {
        if (mLocation != null && mLocation.length() > 0 && mLocation.indexOf(",") >= 0) {
            try {
                String[] arrs = mLocation.split(",");
                //纬度  经度
                LatLng latLng = new LatLng(Double.parseDouble(arrs[1]), Double.parseDouble(arrs[0]));
                GeoCoder geoCoder = GeoCoder.newInstance();
                OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
                    // 反地理编码查询结果回调函数
                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                        if (result == null
                                || result.error != SearchResult.ERRORNO.NO_ERROR) {
                            // 没有检测到结果
                        }
                        if (mRepaymentInfo != null)
                            mRepaymentInfo.setStrWorkLocation(result.getAddress());
                        tvAddress.setText(result.getAddress());
                    }

                    // 地理编码查询结果回调函数
                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult result) {
                        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                            // 没有检测到结果
                        }
                    }
                };
                // 设置地理编码检索监听者
                geoCoder.setOnGetGeoCodeResultListener(listener);
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
