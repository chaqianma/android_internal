package com.jwy.jd.common;

import com.jwy.jd.model.LocationInfo;
import com.jwy.jd.model.UserInfo;

/**
 * Created by zhangxd on 2015/7/17.
 */
public class AppData {
    private static AppData appData = null;
    private UserInfo userInfo = null;

    private LocationInfo locationInfo = null;

    private AppData() {
    }

    public static AppData getInstance() {
        if (appData == null) {
            synchronized (AppData.class) {
                if (appData == null)
                    appData = new AppData();
            }
        }
        return appData;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public LocationInfo getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(LocationInfo locationInfo) {
        this.locationInfo = locationInfo;
    }
}
