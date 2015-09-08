package com.chaqianma.jd.DBHelper;

import android.provider.BaseColumns;

/**
 * Created by zhangxd on 2015/7/29.
 */
public abstract class ImageTable implements BaseColumns {
    //表名
    public static final String TABLE_NAME = "uploadimg";
    //图片文件ID
    public static final String FILEID = "fileId";
    //请求ID
    public static final String BORROW_REQUESTID = "borrowRequestId";
    //图片名称
    public static final String IMGNAME = "imgName";
    //小图路径
    public static final String SMALLIMGPATH = "smallImgPath";
    //大图路径
    public static final String BIGIMGPATH = "bigImgPath";
    //文件类型
    public static final String FILETYPE = "fileType";
    //对应的表单名
    public static final String PARENT_TABLE_NAME = "parentTableName";
    //属于哪个
    public static final String PARENT_ID = "parentId";
    // -1 本地  0 上传中  1 成功  2 失败
    public static final String STATUS = "status";
    //上传时间
    public static final String DATELINE = "dateline";
    //位于哪一个
    public static final String IDXTAG = "idxTag";
}
