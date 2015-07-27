package com.chaqianma.jd.widget;

import android.content.Context;

/**
 * Created by zhangxd on 2015/7/15.
 * 网络请求提示框
 */

public class JDProgress {
    private static JDProgressDialog dialog = null;

    public static void show(Context context)
    {
        show(context, null);
    }

    public static void show(Context context, String strMsg) {
        if (context == null) return;
        if (dialog != null)
            dialog.dismiss();
        dialog = new JDProgressDialog(context, strMsg);
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
