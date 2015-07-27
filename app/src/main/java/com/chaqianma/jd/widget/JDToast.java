package com.chaqianma.jd.widget;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zhangxd on 2015/7/15
 * 系统提示
 */
public class JDToast {
    public static Toast toast=null;

    public static void makeToast(Context context, String content) {
        if(context==null)return;
        if(toast != null)
            toast.cancel();
        toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showShortText(Context context, int resId) {
        try {
            if(context==null)return;
            if(toast != null)
                toast.cancel();
            toast = Toast.makeText(context, context.getString(resId),Toast.LENGTH_SHORT);
            toast.show();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public static void showShortText(Context context, CharSequence text) {
        if(context==null)return;
        if(toast != null)
            toast.cancel();
        toast = Toast.makeText(context, text,Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showLongText(Context context, int resId) {
        try {
            if(context==null)return;
            if(toast != null)
                toast.cancel();
            toast = Toast.makeText(context,context.getString(resId),Toast.LENGTH_LONG);
            toast.show();

        } catch (Exception e) {
          e.printStackTrace();
        }
    }

    public static void showLongText(Context context, CharSequence text) {
        if(context==null)return;
        if(toast != null)
            toast.cancel();
        toast = Toast.makeText(context, text,Toast.LENGTH_LONG);
        toast.show();
    }
}
