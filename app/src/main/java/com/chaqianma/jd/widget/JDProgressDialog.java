package com.chaqianma.jd.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.chaqianma.jd.R;

/**
 * Created by zhangxd on 2015/7/15.
 */
public class JDProgressDialog extends Dialog {
    public JDProgressDialog(Context context, String strMsg)
    {
        this(context,R.style.CustomProgressDialog,strMsg);
    }

    public JDProgressDialog(Context context, int theme, String strMsg)
    {
        super(context,theme);
        this.setContentView(R.layout.progress_dialog);
        this.getWindow().getAttributes().gravity= Gravity.CENTER;

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
       if(!hasFocus) {
           dismiss();
       }
    }
}
