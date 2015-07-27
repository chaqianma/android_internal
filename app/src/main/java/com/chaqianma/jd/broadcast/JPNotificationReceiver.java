package com.chaqianma.jd.broadcast;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zhangxd on 2015/7/20.
 */
public class JPNotificationReceiver extends BroadcastReceiver {
    private  final String  NOTIFICATIONRECEIVER = "NOTIFICATIONRECEIVER";
    private Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext=context;
        Bundle bundle = intent.getExtras();
        if(NOTIFICATIONRECEIVER.equals(intent.getAction()))
        {

        }else if(JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction()))
        {

        }else{
            /*Intent i = new Intent(context, MainActivity.class);
            i.putExtras(bundle);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            context.startActivity(i);*/
        }
    }
}
