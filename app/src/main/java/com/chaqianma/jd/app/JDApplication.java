package com.chaqianma.jd.app;

import android.app.Application;
import android.app.Notification;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.chaqianma.jd.R;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.utils.JPushUtil;
import com.chaqianma.jd.utils.SharedPreferencesUtil;

import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by zhangxd on 2015/7/15.
 * Application
 */
public class JDApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        JPushInterface.setDebugMode(Constants.DEBUG); // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this); // 初始化 JPush
        SDKInitializer.initialize(this);//初始化百度地图
        JPushUtil.setPushNotificationBuilder(getApplicationContext());
    }

    //设置别名
    public void setAlias(String userMobile) {
        //设置别名
        mHandler.sendMessage(mHandler.obtainMessage(Constants.MSG_SET_ALIAS, userMobile));
    }


    //Handler to setAlias
    private final android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.MSG_SET_ALIAS:
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;
            }
        }
    };

    // AliasCallback
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    break;
                case 6002:
                    break;
                default:
            }
        }
    };
}
