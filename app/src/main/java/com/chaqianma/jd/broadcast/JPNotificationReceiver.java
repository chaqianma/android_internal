package com.chaqianma.jd.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;

import com.chaqianma.jd.activity.LoginActivity;
import com.chaqianma.jd.activity.MainActivity;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.utils.SharedPreferencesUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zhangxd on 2015/7/20.
 */
public class JPNotificationReceiver extends BroadcastReceiver {
    private final String NOTIFICATIONRECEIVER = "NOTIFICATIONRECEIVER";
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        Bundle bundle = intent.getExtras();
        if (NOTIFICATIONRECEIVER.equals(intent.getAction())) {

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            //1 新任务  5 还款日  6 逾期
            if (intent.hasExtra("messageType")) {
                String msgType = intent.getStringExtra("messageType");
                if (msgType.equals("1")) {

                } else if (msgType.equals("5") || (msgType.equals("6"))) {
                    SharedPreferencesUtil.addRepaymentId(mContext, intent.getStringExtra("repaymentId"));
                } else {

                }
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            if (intent.hasExtra("messageType")) {
                String msgType = intent.getStringExtra("messageType");
                if (msgType.equals("1")) {
                    if (AppData.getInstance().getUserInfo() == null) {
                        Intent startIntent = new Intent();
                        startIntent.setClass(context, LoginActivity.class);
                        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(startIntent);
                    } else {
                        Intent startIntent = new Intent();
                        startIntent.setClass(context, MainActivity.class);
                        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(startIntent);
                    }
                }
            }
        } else {
        }
    }
}
