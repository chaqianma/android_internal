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
    private String sum_price=null;
    private String plate_num=null;
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

    public String getSum_price() {
        return sum_price;
    }

    public void setSum_price(String sum_price) {
        this.sum_price = sum_price;
    }

    public String getPlate_num() {
        return plate_num;
    }

    public void setPlate_num(String plate_num) {
        this.plate_num = plate_num;
    }
}
