package com.chaqianma.jd.DBHelper;

import android.provider.BaseColumns;

/**
 * Created by zhangxd on 2015/7/29.
 */
public abstract class ImageTable implements BaseColumns {
    //表名
    public static final String TABLE_NAME = "uploadimg";
    //请求ID
    public static final String BORROW_REQUESTID="borrow_request_id";
    //图片类型
    public static final  String IMGTYPE = "imgType";
    //图片名称
    public static final String IMGNAME = "imgName";
    //小图路径
    public static final String SMALLIMGPATH = "smallImgPath";
    //大图路径
    public static final String BIGIMGPATH = "bigImgPath";
    //文件内容
    public static final String FILETYPE = "fileType";
    //对应的表单名
    public static final String PARENT_TABLE_NAME = "tableName";
    //属于哪个
    public static final String PARENT_ID="parent_id";
}
