package com.chaqianma.jd.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxd on 2015/8/1
 *
 * 资产
 */
public class AssetInfo extends ErrorInfo{
    private String borrowRequestId=null;
    private String id=null;
    private List<CarInfo> personalAssetsCarInfoList=null;
    private List<HouseInfo>personalAssetsHouseInfoList=null;
    private String remark=null;
    public String getBorrowRequestId() {
        return borrowRequestId;
    }

    public void setBorrowRequestId(String borrowRequestId) {
        this.borrowRequestId = borrowRequestId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CarInfo> getPersonalAssetsCarInfoList() {
        return personalAssetsCarInfoList;
    }

    public void setPersonalAssetsCarInfoList(List<CarInfo> personalAssetsCarInfoList) {
        this.personalAssetsCarInfoList = personalAssetsCarInfoList;
    }

    public List<HouseInfo> getPersonalAssetsHouseInfoList() {
        return personalAssetsHouseInfoList;
    }

    public void setPersonalAssetsHouseInfoList(List<HouseInfo> personalAssetsHouseInfoList) {
        this.personalAssetsHouseInfoList = personalAssetsHouseInfoList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
