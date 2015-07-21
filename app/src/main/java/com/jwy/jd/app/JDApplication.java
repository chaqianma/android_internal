package com.jwy.jd.app;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import cn.jpush.android.api.JPushInterface;

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
        JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this); // 初始化 JPush
        getLocation();
    }

    //查找地理位置
    private void getLocation()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
}
