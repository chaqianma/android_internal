package com.jwy.jd.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewTreeObserver;

import com.jwy.jd.R;

/**
 * Created by zhangxd on 2015/7/15.
 */
public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(Context context,String strMsg)
    {
        this(context,R.style.CustomProgressDialog,strMsg);
    }

    public CustomProgressDialog(Context context,int theme,String strMsg)
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
