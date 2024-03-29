package com.chaqianma.jd.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;

import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.widget.JDToast;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Created by zhangxd on 2015/7/20.
 * 工程辅助类
 */
public class JDAppUtil {
    private static boolean isAuthSuccess = false;

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
    * 金额转成 000,000 ,
    * */
    public static String money2Format(String money) {
        try {
            if (!JDAppUtil.isEmpty(money)) {
                NumberFormat nf1 = NumberFormat.getInstance();
                return nf1.format(Integer.parseInt(money));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 转换时间 yyyy-MM-dd
    * */
    public static String getTimeYMD(String timestamp) {
        String date = "";
        try {
            if (timestamp != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
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

    /*
    * 设置是否实名成功过
    * */
    public static void setIsAuthSuccess(boolean isSuccess) {
        isAuthSuccess = isSuccess;
    }

    /*
    * 获取实名验证标签
    * */
    public static boolean getIsAuthSuccess() {
        return isAuthSuccess;
    }

    /**
     * 隐藏软键盘
     */
    public static void hidekeyboard(Activity act) {
        InputMethodManager imm = ((InputMethodManager) act
                .getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (imm.isActive()) {
            View focusView = act.getCurrentFocus();
            if (focusView != null) {
                imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            }
        }
    }

    /*
    * 是否有SD卡
    * */
    public static boolean existSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    /*
    * SD卡节余MB
    * */
    public static long getSDFreeSize() {
        try {
            //取得SD卡文件路径
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            //获取单个数据块的大小(Byte)
            long blockSize = sf.getBlockSize();
            //空闲的数据块的数量
            long freeBlocks = sf.getAvailableBlocks();
            //返回SD卡空闲大小
            //return freeBlocks * blockSize;  //单位Byte
            //return (freeBlocks * blockSize)/1024;   //单位KB
            return (freeBlocks * blockSize) / 1024 / 1024; //单位MB
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }


    //判断值是否为空
    public static boolean isEmpty(String value) {
        return value == null || value.length() == 0;
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
