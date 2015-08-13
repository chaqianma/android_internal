package com.chaqianma.jd.model;

import java.util.List;

/**
 * Created by zhangxd on 2015/8/2.
 *
 * 联系人信息
 */
public class ContactInfo extends ErrorInfo {
    private String borrowRequestId=null;
    private List<UploadFileInfo> fileList=null;
    private String id=null;
    private int relation=-1;
    private String name=null;
    private String remark=null;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
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
}
