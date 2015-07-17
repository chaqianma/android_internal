package com.jwy.jd.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhangxd on 2015/7/16.
 * SharedPreferences π”√
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

    public static boolean getShareBoolean(Context context, String strKey)
    {
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        return settings.getBoolean(strKey, false);
    }

    public static void setShareBoolean(Context context, String strKey, boolean bVaule)
    {
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(strKey, bVaule);
        editor.commit();
    }

    public static String getShareString(Context context, String strKey)
    {
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        return settings.getString(strKey, "");
    }

    public static void setShareString(Context context, String strKey, String strValue)
    {
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(strKey, strValue);
        editor.commit();
    }

    public static void setShareInt(Context context, String strKey, int iValue)
    {
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(strKey, iValue);
        editor.commit();
    }

    public static int getShareInt(Context context, String strKey)
    {
        SharedPreferences settings = context.getSharedPreferences(SHAREPREFERENCE_NAME, context.MODE_PRIVATE);
        return settings.getInt(strKey, 0);
    }
}
