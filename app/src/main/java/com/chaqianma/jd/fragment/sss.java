package com.chaqianma.jd.fragment;

import com.chaqianma.jd.model.CompanyInfo;
import com.chaqianma.jd.model.ErrorInfo;

import java.util.List;

/**
 * Created by zhangxd on 2015/8/2.
 */
public class sss extends ErrorInfo {
    private List<CompanyInfo> businessInfoList=null;

    public List<CompanyInfo> getBusinessInfoList() {
        return businessInfoList;
    }

    public void setBusinessInfoList(List<CompanyInfo> businessInfoList) {
        this.businessInfoList = businessInfoList;
    }
}
