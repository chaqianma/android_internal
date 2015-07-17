package com.jwy.jd.common;

import com.jwy.jd.model.UserInfo;

/**
 * Created by zhangxd on 2015/7/17.
 */
public class AppData {
    private static  AppData appData=null;
    private UserInfo userInfo=null;
    private AppData()
    {
    }

    public synchronized static AppData newInstance()
    {
        if(appData==null)
            appData=new AppData();
        return appData;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
