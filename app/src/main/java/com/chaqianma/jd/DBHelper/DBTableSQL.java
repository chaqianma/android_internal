package com.chaqianma.jd.DBHelper;

import android.provider.BaseColumns;

/**
 * Created by zhangxd on 2015/7/29.
 */
public class DBTableSQL {
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    public static String SQL_CREATE_UPLOADIMG = "create table if not exists "
            + ImageTable.TABLE_NAME
            + " (" + ImageTable._ID + " INTEGER PRIMARY KEY,"
            + ImageTable.BORROW_REQUESTID + TEXT_TYPE + COMMA_SEP
            + ImageTable.PARENT_ID + TEXT_TYPE + COMMA_SEP
            + ImageTable.IMGTYPE + TEXT_TYPE + COMMA_SEP
            + ImageTable.IMGNAME + TEXT_TYPE + COMMA_SEP
            + ImageTable.SMALLIMGPATH + TEXT_TYPE + COMMA_SEP
            + ImageTable.BIGIMGPATH + TEXT_TYPE + COMMA_SEP
            + ImageTable.FILETYPE + TEXT_TYPE + COMMA_SEP
            + ImageTable.PARENT_TABLE_NAME + TEXT_TYPE + ")";



}