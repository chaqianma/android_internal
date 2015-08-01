package com.chaqianma.jd.model;

/**
 * Created by zhangxd on 2015/7/29.
 */

/*
    fileType 文件类型 1身份证 2离婚证/结婚证 3备注文件 4营业执照 5税务登记证
// 6企业代码证 7其他证件 8房产证/合同 9土地证 10收入流水
// 11 费用/成本单据 12其他单据 13车牌车型 14行驶证 15驾驶证 16护照
    parentTableName //关联记录表名 个人信息 user_base_info 企业信息 business_info
    parentId 关联记录ID
*
 */
public enum UploadFileType {
    NONE(0), CARD(1), MARRY(2), SOUND(3), REMARK(4),
    YY(4), SW(5), QYDM(6), QT(7), FC(8), TD(9);
    private int index;

    private UploadFileType(int index) {
        this.index = index;
    }

    public int getValue() {
        return index;
    }

    public static UploadFileType valueOf(int value) {
        UploadFileType fileType = UploadFileType.NONE;
        switch (value) {
            case 0:
                break;
            case 1:
                fileType = UploadFileType.CARD;
                break;
            case 2:
                fileType = UploadFileType.MARRY;
                break;
            case 3:
                fileType = UploadFileType.SOUND;
                break;
            case 4:
                fileType = UploadFileType.REMARK;
                break;
            default:
                break;
        }
        return fileType;
    }

    // YY(4), SW(5), QYDM(6), QT(7), FC(8), TD(9);
    public static UploadFileType valueOfName(int value) {
        UploadFileType fileType = UploadFileType.NONE;
        switch (value) {
            case 4:
                fileType = UploadFileType.YY;
                break;
            case 5:
                fileType = UploadFileType.SW;
                break;
            case 6:
                fileType = UploadFileType.QYDM;
                break;
            case 7:
                fileType = UploadFileType.QT;
                break;
            case 8:
                fileType = UploadFileType.FC;
                break;
            case 9:
                fileType = UploadFileType.TD;
                break;
            default:
                break;
        }
        return fileType;
    }
}



