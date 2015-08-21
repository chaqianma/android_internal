package com.chaqianma.jd.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.chaqianma.jd.model.RepaymentInfo;
import com.chaqianma.jd.utils.JDAppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxd on 2015/8/21.
 */
public class RepaymentDBHelper {
    /**
     * 数据库操作对象
     */
    private DBBaseHelper db;

    /**
     * 同步锁
     */
    private static Object Lockobj = new Object();


    /**
     * 数据库初始化
     */
    public void iniLoginDBhelper(final Context context) {
        if (null == db) {
            if (null == context) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            db = new DBBaseHelper(context);
        }
    }

    /**
     * 关闭数据库
     */
    public void closeDB() {
        if (db != null) {
            db.closeDatabase();
        }
    }

    /**
     * 插入数据
     */
    public void insert(Context context, RepaymentInfo repaymentInfo) {
        iniLoginDBhelper(context);
        synchronized (Lockobj) {
            ContentValues values = new ContentValues();
            values.put(RepaymentTable.REPAYMENT_ID, repaymentInfo.getId());
            values.put(RepaymentTable.DATELINE, repaymentInfo.getDateLine());
            db.insert(RepaymentTable.TABLE_NAME, values);
            closeDB();
        }
    }

    /*
    * 获取催收IDS
    * */
    public String getRepaymentIds(Context context) {
        String ids = "";
        try {
            iniLoginDBhelper(context);
            synchronized (Lockobj) {
                Cursor cursor = db.query(RepaymentTable.TABLE_NAME, null, null,
                        null, null);
                if (cursor == null) {
                    return ids;
                }
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    do {
                        String bVal = cursor.getString(cursor.getColumnIndex(RepaymentTable.REPAYMENT_ID));
                        if (!JDAppUtil.isEmpty(ids)) {
                            ids += "," + bVal;
                        } else {
                            ids += bVal;
                        }
                    } while (cursor.moveToNext());
                }
                cursor.close();
                closeDB();
                return ids;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    /**
     * 获取所有账号信息
     */
    public List<RepaymentInfo> getRepaymentList(final Context context) {
        List<RepaymentInfo> repaymentInfoList = new ArrayList<RepaymentInfo>();
        iniLoginDBhelper(context);
        synchronized (Lockobj) {
            Cursor cursor = db.query(RepaymentTable.TABLE_NAME, null, null,
                    null, null);
            if (cursor == null) {
                return repaymentInfoList;
            }
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    RepaymentInfo repaymentInfo = new RepaymentInfo();
                    repaymentInfo.setColumnId(cursor.getString(cursor.getColumnIndex(RepaymentTable._ID)));
                    repaymentInfo.setId(cursor.getString(cursor.getColumnIndex(RepaymentTable.REPAYMENT_ID)));
                    repaymentInfo.setDateLine(cursor.getString(cursor.getColumnIndex(RepaymentTable.DATELINE)));
                    repaymentInfoList.add(repaymentInfo);
                } while (cursor.moveToNext());
            }
            cursor.close();
            closeDB();
            return repaymentInfoList;
        }
    }
}
