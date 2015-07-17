package com.jwy.jd.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhangxd on 2015/7/16.
 * SQLlITE
 */
public class DBSQLBaseUtil extends SQLiteOpenHelper {
    private static final int VERSION=1;
    public DBSQLBaseUtil(Context context,String name,SQLiteDatabase.CursorFactory factory,int version)
    {
        super(context,name,factory,version);
    }

    public DBSQLBaseUtil(Context context,String name,int version)
    {
        this(context, name, null, version);
    }

    public DBSQLBaseUtil(Context context, String name){
        this(context,name,VERSION);
    }

    //该函数是在第一次创建的时候执行，实际上是第一次得到SQLiteDatabase对象的时候才会调用这个方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        System.out.println("create a database");
        //execSQL用于执行SQL语句
        db.execSQL("create table user(id int,name varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        System.out.println("upgrade a database");
    }
}
