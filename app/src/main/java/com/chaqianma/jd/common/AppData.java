package com.chaqianma.jd.common;

import com.chaqianma.jd.model.BorrowRequestInfo;
import com.chaqianma.jd.model.LocationInfo;
import com.chaqianma.jd.model.UserInfo;

/**
 * Created by zhangxd on 2015/7/17.
 */
public class AppData {
    private static AppData appData = null;
    private UserInfo userInfo = null;

    private LocationInfo locationInfo = null;

    private BorrowRequestInfo borrowRequestInfo=null;



    private String header=null;


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


    public BorrowRequestInfo getBorrowRequestInfo() {
        return borrowRequestInfo;
    }

    public void setBorrowRequestInfo(BorrowRequestInfo borrowRequestInfo) {
        this.borrowRequestInfo = borrowRequestInfo;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}
