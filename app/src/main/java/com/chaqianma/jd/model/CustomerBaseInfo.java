package com.chaqianma.jd.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxd on 2015/7/31.
 * 个人基本信息
 */
public class CustomerBaseInfo extends ErrorInfo {
    private String id = null;
    private String birthday = null;
    private String borrorRequestId = null;
    private String certificateNum = null;
    private String certificateType = null;
    private String comeLocalTime=null;
    private String countChildren = null;
    private List<UploadFileInfo> fileList=null;
    private String gender = null;
    private String householdType = null;
    private String isAgriculturalHousehold = null;
    private String maritalStatus = null;
    private String mobile = null;
    private String name = null;
    private String remark = null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBorrorRequestId() {
        return borrorRequestId;
    }

    public void setBorrorRequestId(String borrorRequestId) {
        this.borrorRequestId = borrorRequestId;
    }

    public String getCertificateNum() {
        return certificateNum;
    }

    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCountChildren() {
        return countChildren;
    }

    public void setCountChildren(String countChildren) {
        this.countChildren = countChildren;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHouseholdType() {
        return householdType;
    }

    public void setHouseholdType(String householdType) {
        this.householdType = householdType;
    }

    public String getIsAgriculturalHousehold() {
        return isAgriculturalHousehold;
    }

    public void setIsAgriculturalHousehold(String isAgriculturalHousehold) {
        this.isAgriculturalHousehold = isAgriculturalHousehold;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getComeLocalTime() {
        return comeLocalTime;
    }

    public void setComeLocalTime(String comeLocalTime) {
        this.comeLocalTime = comeLocalTime;
    }

    public List<UploadFileInfo> getFileList() {
        return fileList;
    }

    public void setFileList(List<UploadFileInfo> fileList) {
        this.fileList = fileList;
    }
}
