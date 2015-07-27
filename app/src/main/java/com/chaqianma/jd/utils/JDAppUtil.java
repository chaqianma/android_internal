package com.chaqianma.jd.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.chaqianma.jd.common.Constants;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Created by zhangxd on 2015/7/20.
 * 工程辅助类
 */
public class JDAppUtil {
    /*
    * 获取UUID
    * */
    public static String getUniqueId(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        /*final String mDevice, mSerial, mPhone,mAndroidId;
        mDevice = "" + tm.getDeviceId(); //这个其实就是唯一设备码了
        mSerial = "" + tm.getSimSerialNumber();
        mAndroidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(mAndroidId.hashCode(), ((long)mDevice.hashCode() << 32) | mSerial.hashCode());
        String uniqueId = deviceUuid.toString();*/
        return tm.getDeviceId();
    }

    /*
    * 是否连接
    * */
    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());

    }

    /*
    *timestamp to string
     */
    public static String getStrDateTime(String timestamp) {
        String date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            date = sdf.format(timestamp);
        } catch (Exception e) {
        }
        return date;
    }

    /*
    * 拨打电话
    * */
    public static void onCallPhone(Context context,String mobile)
    {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
        context.startActivity(intent);
    }
}
