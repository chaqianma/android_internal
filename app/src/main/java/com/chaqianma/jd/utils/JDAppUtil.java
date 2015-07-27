package com.chaqianma.jd.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.chaqianma.jd.common.Constants;

import java.util.UUID;

/**
 * Created by zhangxd on 2015/7/20.
 * 工程辅助类
 */
public class JDAppUtil{
    /*
    * 获取UUID
    * */
    public static String getUniqueId(Context context)
    {
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
        ConnectivityManager conn = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static void saveUserAndPassword(Context context,String username,String password)
    {
        if(SharedPreferencesUtil.getShareBoolean(context,Constants.REMEMBERPASSWORD))
        {
            SharedPreferencesUtil.setShareString(context,Constants.USERNAME,username);
            SharedPreferencesUtil.setShareString(context,Constants.PASSWORD,password);
        }
    }
}
