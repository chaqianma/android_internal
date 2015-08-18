package com.chaqianma.jd.app;

/**
 * Created by zhangxd on 2015/7/15.
 * 全局异常处理
 */

import android.content.Context;

import java.lang.Thread;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static CrashHandler mInstance = new CrashHandler();
    private Context mContext;
    private CrashHandler()
    {

    }

    /** 获取CrashHandler实例 ,单例模式 */
    public static CrashHandler getInstance() {
        if (mInstance == null) {
            synchronized (CrashHandler.class) {
                if (mInstance == null)
                    mInstance = new CrashHandler();
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        String sss="111";
        sss="111";
        sss="111";
        sss="111";
        sss="111";
        System.out.print("zxdLog:"+":"+ex.getMessage());
    }
}
