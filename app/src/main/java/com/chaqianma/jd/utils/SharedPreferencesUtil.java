package com.chaqianma.jd.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.chaqianma.jd.common.Constants;

/**
 * Created by zhangxd on 2015/7/16.
 * SharedPreferences操作
 */
public class SharedPreferencesUtil {

    public static String SHAREPREFERENCE_NAME = "SHAREPREFERENCE_NAME";
    public static String SHAREPREFERENCE_SPLASH = "SHAREPREFERENCE_SPLASH";
    public static String SHAREPREFERENCE_PHONE = "SHAREPREFERENCE_PHONE";
    public static String SHAREPREFERENCE_PWD = "SHAREPREFERENCE_PWD";
    public static String SHAREPREFERENCE_VERSION = "SHAREPREFERENCE_VERSION";
    public static String SHAREPREFERENCE_USERSTATUS = "SHAREPREFERENCE_USERSTATUS";
    public static String SHAREPREFERENCE_JIZHUMIMA = "SHAREPREFERENCE_JIZHUMIMA";
    public static String SHAREPREFERENCE_TUISONGIDLIST = "SHAREPREFERENCE_TUISONGIDLIST";
    public static String SHAREPREFERENCE_WEBVIEWZITI = "SHAREPREFERENCE_WEBVIEWZITI";

    public static boolean getShareBoolean(Context context, String strKey) {
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        return settings.getBoolean(strKey, false);
    }

    public static boolean getShareBoolean(Context context, String strKey, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        return settings.getBoolean(strKey, defaultValue);
    }


    public static void setShareBoolean(Context context, String strKey, boolean bVaule) {
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(strKey, bVaule);
        editor.commit();
    }

    public static String getShareString(Context context, String strKey) {
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        return settings.getString(strKey, "");
    }

    public static void setShareString(Context context, String strKey, String strValue) {
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(strKey, strValue);
        editor.commit();
    }

    public static void setShareInt(Context context, String strKey, int iValue) {
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(strKey, iValue);
        editor.commit();
    }

    public static int getShareInt(Context context, String strKey) {
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        return settings.getInt(strKey, 0);
    }

    /*
    * 删除对应的Key值
    * */
    public static void removeValue(Context context, String strKey) {
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(strKey);
        editor.commit();
    }

    /*
    * 获取催收ID值
    * */
    public static String getRepaymentId(Context context) {
        String key = "REPAYMENT";
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        return settings.getString(key, "");
    }

    /*
    * 添加催收ID值
    * */
    public static void addRepaymentId(Context context, String bVal) {
        String key = "REPAYMENT";
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        String pId = settings.getString(key, "");
        if (!JDAppUtil.isEmpty(pId)) {
            pId += "," + bVal;
        } else {
            pId += bVal;
        }
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, pId);
        editor.commit();
    }

    /*
    * 删除催收ID值
    * */
    public static void removePaymentId(Context context) {
        String key = "REPAYMENT";
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();
    }

    //保存用记名与密码
    public static void saveUserAndPassword(Context context, String username, String password) {
        if (SharedPreferencesUtil.getShareBoolean(context, Constants.REMEMBERPASSWORD)) {
            SharedPreferencesUtil.setShareString(context, Constants.USERNAME, username);
            SharedPreferencesUtil.setShareString(context, Constants.PASSWORD, password);
        }
    }
}
