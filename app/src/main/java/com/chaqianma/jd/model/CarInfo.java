package com.chaqianma.jd.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxd on 2015/8/1.
 */
public class CarInfo implements Serializable {
    private String id = null;
    private String personalAssetsId = null;
    private List<UploadFileInfo> fileList=null;
    private String sumPrice=null;
    private String plateNum=null;
    private String purchaseCondition=null;
    private String purchaseStatus=null;
    private String purchaseTime=null;
    private String code=null;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonalAssetsId() {
        return personalAssetsId;
    }

    public void setPersonalAssetsId(String personalAssetsId) {
        this.personalAssetsId = personalAssetsId;
    }

    public List<UploadFileInfo> getFileList() {
        return fileList;
    }

    public void setFileList(List<UploadFileInfo> fileList) {
        this.fileList = fileList;
    }

    public String getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getPurchaseCondition() {
        return purchaseCondition;
    }

    public void setPurchaseCondition(String purchaseCondition) {
        this.purchaseCondition = purchaseCondition;
    }

    public String getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(String purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
