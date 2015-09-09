package com.chaqianma.jd.utils;

import android.content.Context;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.chaqianma.jd.model.RepaymentInfo;

/**
 * Created by zhangxd on 2015/8/12
 * 高德
 */
public class GDGeoCodeUtil {

    private TextView tvAddress;
    private String mLocation = null;
    private RepaymentInfo mRepaymentInfo = null;
    private Context mContext = null;

    public GDGeoCodeUtil(Context context, TextView tvAddress, String location) {
        this.tvAddress = tvAddress;
        this.mLocation = location;
        this.mContext = context;
        getAddress();
    }

    public GDGeoCodeUtil(Context context, TextView tvAddress, RepaymentInfo repaymentInfo) {
        this.tvAddress = tvAddress;
        this.mLocation = repaymentInfo.getUserWorkLocation();
        this.mRepaymentInfo = repaymentInfo;
        this.mContext = context;
        getAddress();
    }

    private void getAddress() {
        if (mLocation != null && mLocation.length() > 0 && mLocation.indexOf(",") >= 0) {
            try {
                String[] arrs = mLocation.split(",");
                LatLonPoint latLonPoint = new LatLonPoint(Double.parseDouble(arrs[1]), Double.parseDouble(arrs[0]));
                GeocodeSearch geocoderSearch = new GeocodeSearch(mContext);
                geocoderSearch.setOnGeocodeSearchListener(geocodeSearchListener);
                RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 100, GeocodeSearch.AMAP);
                geocoderSearch.getFromLocationAsyn(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private GeocodeSearch.OnGeocodeSearchListener geocodeSearchListener = new GeocodeSearch.OnGeocodeSearchListener() {
        @Override
        public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
            if (rCode == 0) {
                if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                        && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                    String address = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                    if (mRepaymentInfo != null)
                        mRepaymentInfo.setStrWorkLocation(address);
                    if (tvAddress != null)
                        tvAddress.setText(address);
                    /*List<PoiItem> poiItems = result.getRegeocodeAddress().getPois();
                    for (PoiItem poiItem : poiItems) {
                        Log.v("zhangzida", poiItem.getTitle());//杈撳嚭鍛ㄨ竟poi鐨勪俊鎭�
                    }*/
                }
            }
        }

        @Override
        public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

        }
    };
}
