package com.chaqianma.jd.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.widget.JDToast;

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
    public static void onCallPhone(Context context, String mobile) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
        context.startActivity(intent);
    }

    /*
    * 判断是否存在SDK卡
    * */
    public static boolean isHasSDKCard(Context context) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            JDToast.showShortText(context, "内存卡不存在");
            return false;
        }
        return true;
    }
    /*
    *  转换时间
    * */
    public static String getTimeToStr(String timestamp) {
        String date = "";
        try {
            if (timestamp != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
                Timestamp now = new Timestamp(Long.parseLong(timestamp));
                date = df.format(now);
            }
        } catch (Exception e) {
        }
        return date;
    }


    /*
    * 添加显示动画
    * */
    public static void addShowAction(View view) {
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);
        view.startAnimation(mShowAction);
        view.setVisibility(View.VISIBLE);
    }

    /*
   * 添加隐藏动画
   * */
    public static void addHiddenAction(View view) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        mHiddenAction.setDuration(500);
        mHiddenAction.setAnimationListener(new MyAnimationListener(view, false));
        view.startAnimation(mHiddenAction);
    }

    private static class MyAnimationListener implements Animation.AnimationListener {
        private View mView = null;
        private boolean mIsShow = false;

        public MyAnimationListener(View view, boolean isShow) {
            this.mView = view;
            this.mIsShow = isShow;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mView.setVisibility(mIsShow ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
