package com.jwy.jd.widget;

import android.content.Context;

import com.jwy.jd.widget.CustomProgressDialog;

/**
 * Created by zhangxd on 2015/7/15.
 * Õ¯¬Á«Î«ÛÃ· æøÚ
 */

public class JDProgress {
    private static CustomProgressDialog dialog = null;

    public static void show(Context context)
    {
        show(context, null);
    }

    public static void show(Context context, String strMsg) {
        if (context == null) return;
        if (dialog != null)
            dialog.dismiss();
        dialog = new CustomProgressDialog(context, strMsg);
        dialog.show();
    }

    public static void dismiss() {
        try {
            if (dialog != null)
                dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
