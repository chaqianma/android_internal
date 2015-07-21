package com.jwy.jd.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.jwy.jd.R;

/**
 * Created by zhangxd on 2015/7/20.
 */
public class JPNotificationReceiver extends BroadcastReceiver {
    private  final String  NOTIFICATIONRECEIVER = "NOTIFICATIONRECEIVER";
    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        if(NOTIFICATIONRECEIVER.equals(intent.getAction()))
        {

        }
    }

}
