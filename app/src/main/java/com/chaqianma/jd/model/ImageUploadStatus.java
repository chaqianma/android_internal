package com.chaqianma.jd.model;

/**
 * Created by zhangxd on 2015/7/30.
 * <p/>
 * ͼƬ�ϴ�״̬
 */
public enum ImageUploadStatus {
    NONE(-1),ING(0) ,SUCCESS(1), FAILURE(2);
    private int index;

    private ImageUploadStatus(int index) {
        this.index = index;
    }

    public int getValue() {
        return index;
    }
}
