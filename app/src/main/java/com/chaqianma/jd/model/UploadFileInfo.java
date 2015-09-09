package com.chaqianma.jd.model;

/**
 * Created by zhangxd on 2015/7/29.
 * <p/>
 * ileType 文件类型 1身份证 2离婚证/结婚证 3备注文件 4营业执照 5税务登记证
 * // 6企业代码证 7其他证件 8房产证/合同 9土地证 10收入流水
 * // 11 费用/成本单据 12其他单据 13车牌车型 14行驶证 15驾驶证 16护照
 * parentTableName //关联记录表名 个人信息 user_base_info 企业信息 business_info
 * parentId 关联记录ID
 */
public class UploadFileInfo extends ErrorInfo {

    private int id;
    //图片id
    private String fileId = null;
    //图片类型
    private String imgType = null;
    //图片名称
    private String imgName = null;
    //小图路径
    private String smallImgPath = null;
    //大图路径
    private String bigImgPath = null;
    //文件类型
    private int fileType;
    //图片是否来自服务器
    private boolean iServer = true;
    //是否是默认图片
    private boolean isDefault = false;
    // -1 本地  0 上传中  1 成功  2 失败
    private int status = -1;
    //请求id
    private String borrowRequestId = null;
    //对应的表单名
    private String parentTableName = null;

    private String dateline = null;
    private String fileExt = null;
    private String userId = null;
    private String parentId = null;

    private int idxTag;

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getSmallImgPath() {
        return smallImgPath;
    }

    public void setSmallImgPath(String smallImgPath) {
        this.smallImgPath = smallImgPath;
    }

    public String getBigImgPath() {
        return bigImgPath;
    }

    public void setBigImgPath(String bigImgPath) {
        this.bigImgPath = bigImgPath;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public boolean iServer() {
        return iServer;
    }

    public void setiServer(boolean imgIsServer) {
        this.iServer = imgIsServer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBorrowRequestId() {
        return borrowRequestId;
    }

    public void setBorrowRequestId(String borrowRequestId) {
        this.borrowRequestId = borrowRequestId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
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

    public int getIdxTag() {
        return idxTag;
    }

    public void setIdxTag(int idxTag) {
        this.idxTag = idxTag;
    }

    public int downloadCnt=0;

    public int getDownloadCnt() {
        return downloadCnt;
    }
    /**
     * 只允许下载次数
     * */
    public void setDownloadCnt(int downloadCnt) {
        this.downloadCnt = downloadCnt;
    }
}
