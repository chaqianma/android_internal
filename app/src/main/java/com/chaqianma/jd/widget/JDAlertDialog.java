package com.chaqianma.jd.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by zhangxd on 2015/7/17.
 */
public class JDAlertDialog
{

    private static final String mPrompt="提示";
    private static final String mSure="确定";
    private static final String mCancel="取消";

    public static void showAlertDialog(Context context,String msg, DialogInterface.OnClickListener onclient)
    {
        showAlertDialog(context, mPrompt, msg, onclient);
    }

    public static void showAlertDialog(Context context,String title,String msg, DialogInterface.OnClickListener onclient)
    {
        new AlertDialog.Builder(context).setTitle(title).setMessage(msg).setPositiveButton(mSure, onclient).create().show();
    }

    public static void showAlertDialog(Context context,String title,View view, DialogInterface.OnClickListener onclient)
    {
        new AlertDialog.Builder(context).setTitle(title).setView(view).
                setPositiveButton(mSure, onclient)
                .setCancelable(true)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                    }
                }).
                create().show();
    }
}
