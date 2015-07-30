package com.chaqianma.jd.model;

import java.io.Serializable;

/**
 * Created by zhangxd on 2015/7/29.
 */
public class DownImgInfo extends ErrorInfo  implements Serializable{
    private String fileId;          //文件 ID
    private String fileExt;         //文件扩展名
    private String userId;         //上传人员ID
    private String fileType;       //文件类型 1身份证 2离婚证/结婚证 3备注文件 4营业执照 5税务登记证
    // 6企业代码证 7其他证件 8房产证/合同 9土地证 10收入流水
    // 11 费用/成本单据 12其他单据 13车牌车型 14行驶证 15驾驶证 16护照
    private String parentTableName; //关联上级表明
    private String parentId;       //关联上级ID
    private Long dateline;          //上传日期

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getParentTableName() {
        return parentTableName;
    }

    public void setParentTableName(String parentTableName) {
        this.parentTableName = parentTableName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Long getDateline() {
        return dateline;
    }

    public void setDateline(Long dateline) {
        this.dateline = dateline;
    }
}
