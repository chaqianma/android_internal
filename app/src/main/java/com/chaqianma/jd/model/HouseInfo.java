package com.chaqianma.jd.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxd on 2015/8/1.
 */
public class HouseInfo implements Serializable {
    private String id = null;
    private String personalAssetsId = null;
    private List<UploadFileInfo> fileList=null;
    private String address=null;
    private String owner_desc=null;
    private String area=null;
    private String deed_num=null;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwner_desc() {
        return owner_desc;
    }

    public void setOwner_desc(String owner_desc) {
        this.owner_desc = owner_desc;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDeed_num() {
        return deed_num;
    }

    public void setDeed_num(String deed_num) {
        this.deed_num = deed_num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
