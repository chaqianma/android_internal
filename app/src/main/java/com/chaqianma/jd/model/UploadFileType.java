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
*   1身份证 2离婚证 3结婚证 4营业执照 5税务登记证 6企业代码证 7其他证件 8房产证/合同 9土地证：10收入证明 11 经营性单据 12车牌车型 13行驶证 14驾驶证 15护照 16备注文件
    17 COMMENT  尽职说明
 */
public enum UploadFileType {
    NONE(0), CARD(1), SINGLE(2), MARRY(3),
    YY(4), SW(5), QYDM(6), QT(7), FC(8), TD(9),
    SY(10), JY(11), CP(12), XS(13), REMARK(16), COMMENT(17), SOUND(80);
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
                fileType = UploadFileType.SINGLE;
                break;
            case 3:
                fileType = UploadFileType.MARRY;
                break;
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
            case 10:
                fileType = UploadFileType.SY;
                break;
            case 11:
                fileType = UploadFileType.JY;
                break;
            case 12:
                fileType = UploadFileType.CP;
                break;
            case 13:
                fileType = UploadFileType.XS;
                break;
            case 16:
                fileType = UploadFileType.REMARK;
                break;
            case 17:
                fileType = UploadFileType.COMMENT;
                break;
            case 80:
                fileType = UploadFileType.SOUND;
                break;
            default:
                break;
        }
        return fileType;
    }
}



