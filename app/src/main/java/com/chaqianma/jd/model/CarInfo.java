package com.chaqianma.jd.model;

import java.io.Serializable;

/**
 * Created by zhangxd on 2015/8/1.
 */
public class CarInfo implements Serializable {
    private String id = null;
    private String personalAssetsId = null;

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
}
