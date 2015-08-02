package com.chaqianma.jd.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxd on 2015/8/1.
 */
public class CompanyInfo  implements Serializable {
    private String id=null;
    private String borrowRequestId=null;
    private List<UploadFileInfo> fileList=null;
    private String companyName=null;
    private String remark=null;
    private String businessLicenceHasNot=null;
    private String businessStartTime=null;
    private String businessType=null;
    private String companySize=null;
    private String organizationType=null;
    private String phone=null;
    private boolean isValid=false;  //是否有效

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBorrowRequestId() {
        return borrowRequestId;
    }

    public void setBorrowRequestId(String borrowRequestId) {
        this.borrowRequestId = borrowRequestId;
    }

    public List<UploadFileInfo> getFileList() {
        return fileList;
    }

    public void setFileList(List<UploadFileInfo> fileList) {
        this.fileList = fileList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBusinessLicenceHasNot() {
        return businessLicenceHasNot;
    }

    public void setBusinessLicenceHasNot(String businessLicenceHasNot) {
        this.businessLicenceHasNot = businessLicenceHasNot;
    }

    public String getBusinessStartTime() {
        return businessStartTime;
    }

    public void setBusinessStartTime(String businessStartTime) {
        this.businessStartTime = businessStartTime;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
