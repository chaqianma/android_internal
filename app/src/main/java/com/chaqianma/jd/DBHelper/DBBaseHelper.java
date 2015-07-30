package com.chaqianma.jd.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zhangxd on 2015/7/29.
 */
public class DBBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";

    /**
     * 数据库的名称
     */
    private static final String DATABASE_NAME = "OSSMobile.db";

    /**
     * 数据库版本号,初始版本为1
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * 存储业务详细信息表
     */
    public static final String TABLE_OSS = "table_oss";

    /**
     * 登陆账号信息表
     */
    public static final String TABLE_LOGIN = "logininfo";


    /**
     * 用来操作数据库的实例
     */
    SQLiteDatabase db = null;

    /**
     * 构造函数
     */
    public DBBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.beginTransaction();
        try {
            db.execSQL(DBTableSQL.SQL_CREATE_UPLOADIMG);
            Log.e(TAG, "db create success");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, "db create fail" + e.getMessage());
        } finally {
            db.endTransaction();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * 插入数据
     *
     * @param tableName 要插入的表格的名称
     * @param values    要插入的数据由数据名称和数据值组成的键值对
     */
    public long insert(String tableName, ContentValues values) {
        long id = -1;
        db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            id = db.insert(tableName, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, ":insert fail" + e.getMessage());
        } finally {
            db.endTransaction();
        }
        return id;
    }


    /**
     * 对数据库中数据的删除操作
     *
     * @param tableName       删除操作中要操作的数据表的名称
     * @param whereArgs       要删除的数据中提供的条件参数的名称
     * @param whereArgsValues 要删除的数据中提供的条件参数的值
     */
    public void delete(String tableName, String[] whereArgs,
                       String[] whereArgsValues) {
        db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            if (whereArgs == null) {
                db.delete(tableName, null, null);
                Log.e(TAG, "delete " + tableName + "  success");
            } else {
                // 当传入的参数为一个时
                if (whereArgs.length == 1) {
                    // 当传入的参数和参数值分别为一个时
                    if (whereArgsValues.length == 1) {
                        db.delete(tableName, whereArgs[0] + " = ?",
                                whereArgsValues);
                        Log.e(TAG, "::delete: " + tableName + "Delete Success!");
                    }
                    // 当传入的参数为一个，参数值为多个时
                    else {
                        db.execSQL(del_SQL(
                                tableName,
                                createSQL(whereArgs, whereArgsValues,
                                        whereArgsValues.length)));
                        Log.e(TAG, "::delete: " + tableName + "Delete Success!");
                    }
                }
                // 当传入的参数和参数值分别为多个时，并且参数和参数值是一一对应的
                else {
                    db.execSQL(del_SQL(
                            tableName,
                            createSQL(whereArgs, whereArgsValues,
                                    whereArgs.length)));
                    Log.e(TAG, "::delete: " + tableName + "Delete Success!");
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, "::delete: " + tableName + "Delete Error!");
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 将用户提供的参数拼接成一条完整的删除数据库的SQL语句
     *
     * @param whereArgs       删除数据的条件参数
     * @param whereArgsValues 删除数据的条件参数的值
     * @return 拼接完成后的删除SQL语句
     */
    private String createSQL(String[] whereArgs, String[] whereArgsValues,
                             int length) {
        StringBuffer sql = new StringBuffer(" ");
        // 当传入的参数为一个时
        if (whereArgs.length == 1) {
            for (int i = 0; i < length; i++) {
                sql.append(whereArgs[0] + " = '" + whereArgsValues[i] + "'");
                if (i < length - 1) {
                    sql.append(" or ");
                }
            }
        }
        // 当传入的参数和参数值分别为多个时，并且参数和参数值是一一对应的
        else {
            for (int i = 0; i < length; i++) {
                sql.append(whereArgs[i] + " = '" + whereArgsValues[i] + "'");
                if (i < length - 1) {
                    sql.append(" and ");
                }
            }
        }
        return sql.toString();
    }

    private String createSQL(String[] whereArgs, long[] whereArgsValues,
                             int length) {
        StringBuffer sql = new StringBuffer(" ");
        // 锟斤拷锟斤拷锟斤拷牟锟斤拷锟轿伙拷锟绞?
        if (whereArgs.length == 1) {
            for (int i = 0; i < length; i++) {
                sql.append(whereArgs[0] + " = " + whereArgsValues[i]);
                if (i < length - 1) {
                    sql.append(" or ");
                }
            }
        }
        // 锟斤拷锟斤拷锟斤拷牟锟斤拷锟酵诧拷锟斤拷值锟街憋拷为锟斤拷锟绞憋拷锟斤拷锟斤拷也锟斤拷锟酵诧拷锟斤拷值锟斤拷一一锟斤拷应锟斤拷
        else {
            for (int i = 0; i < length; i++) {
                sql.append(whereArgs[i] + " = " + whereArgsValues[i]);
                if (i < length - 1) {
                    sql.append(" and ");
                }
            }
        }
        return sql.toString();
    }

    /**
     * 生成删除数据的sql语句
     *
     * @param tableName 要操作的数据表的名称
     * @param str_sql   where语句部分
     * @return
     */
    private String del_SQL(String tableName, String str_sql) {
        return new StringBuffer("delete from " + tableName + " where " + str_sql).toString();
    }

    /**
     * 对数据进行的查询操作
     *
     * @param tableName       需要操作的表名
     * @param whereArgs       要查询的数据中提供的条件参数的名称
     * @param whereArgsValues 要查询的数据中提供的条件参数的值
     * @param column          控制哪些字段返回结果（传null是返回所有字段的结果集）
     * @param orderBy         是否对某一字段进行排序（传null不进行排序）
     * @return 查找的数据集的指针
     */
    public Cursor query(String tableName, String[] whereArgs, String[] whereArgsValues, String[] column, String orderBy) {
        Cursor cursor = null;
        db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            // 当传入的参数为空时，表示操作所有的记录
            if (whereArgs == null) {
                cursor = db.query(tableName, column, null, null, null, null, orderBy);
            } else {
                if (whereArgs.length == 1) {
                    // 当传入的参数和参数值分别为一个时
                    if (whereArgsValues.length == 1) {
                        cursor = db.query(tableName, column, whereArgs[0] + "= ?", whereArgsValues, null, null, orderBy);
                    }
                    // 当传入的参数为一个，参数值为多个时
                    else {
                        cursor = db.query(tableName, column, createSQL(whereArgs, whereArgsValues, whereArgsValues.length), null, null, null, orderBy);
                    }
                }
                // 当传入的参数和参数值分别为多个时，并且参数和参数值是一一对应的
                else {
                    cursor = db.query(tableName, column, createSQL(whereArgs, whereArgsValues, whereArgs.length), null, null, null, orderBy);
                }
            }
            Log.e(TAG, "::query: " + tableName + "  success");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, "::query: " + tableName + "query error --->>>" + e.getMessage());
        } finally {
            db.endTransaction();
        }
        return cursor;
    }

    /**
     * 对数据的更新操作
     *
     * @param tableName       是所对应的操作表
     * @param values          需要更新的数据名称和值组成的键值对
     * @param whereArgs       要更新的数据集的条件参数
     * @param whereArgsValues 要更新的数据集的条件参数的值
     */
    public void update(String tableName, ContentValues values, String[] whereArgs, String[] whereArgsValues) {
        db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            if (whereArgs == null) {
                db.update(tableName, values, null, null);
            } else {
                // 当传入的参数为一个时
                if (whereArgs.length == 1) {
                    // 当传入的参数和参数值分别为一个时
                    if (whereArgsValues.length == 1) {
                        db.update(tableName, values, whereArgs[0] + "='" + whereArgsValues[0] + "'", null);
                    }
                    // 当传入的参数为一个，参数值为多个时
                    else {
                        db.update(tableName, values, createSQL(whereArgs, whereArgsValues, whereArgsValues.length), null);
                    }
                }
                // 当传入的参数和参数值分别为多个时，并且参数和参数值是一一对应的
                else {
                    db.update(tableName, values, createSQL(whereArgs, whereArgsValues, whereArgs.length), null);
                }

            }
            Log.e(TAG, "::update: " + tableName + "Update Success!");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, "::update: " + tableName + "update error-->>" + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public void update(String tableName, ContentValues values, String[] whereArgs, long[] whereArgsValues) {
        db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            if (whereArgs == null) {
                db.update(tableName, values, null, null);
            } else {
                // 锟斤拷锟斤拷锟斤拷牟锟斤拷锟轿伙拷锟绞?
                if (whereArgs.length == 1) {
                    // 锟斤拷锟斤拷锟斤拷牟锟斤拷锟酵诧拷锟斤拷值锟街憋拷为一锟斤拷时
                    if (whereArgsValues.length == 1) {
                        db.update(tableName, values, whereArgs[0] + "=" + whereArgsValues[0], null);
                    }
                    // 锟斤拷锟斤拷锟斤拷牟锟斤拷锟轿伙拷锟斤拷锟斤拷锟斤拷锟街滴拷锟斤拷时
                    else {
                        db.update(tableName, values, createSQL(whereArgs, whereArgsValues, whereArgsValues.length), null);
                    }
                }
                // 锟斤拷锟斤拷锟斤拷牟锟斤拷锟酵诧拷锟斤拷值锟街憋拷为锟斤拷锟绞憋拷锟斤拷锟斤拷也锟斤拷锟酵诧拷锟斤拷值锟斤拷一一锟斤拷应锟斤拷
                else {
                    db.update(tableName, values, createSQL(whereArgs, whereArgsValues, whereArgs.length), null);
                }

            }
            Log.e(TAG, "::update: " + tableName + "Update Success!");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, "::update: " + tableName + "update error-->>" + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        Cursor cursor = null;
        db = this.getReadableDatabase();
        db.beginTransaction();

        try {
            cursor = db.rawQuery(sql, selectionArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, "query error --->>>" + e.getMessage());
        } finally {
            db.endTransaction();
        }

        return cursor;
    }

    public void execSQL(String sql, Object[] bindArgs) {
        db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            db.execSQL(sql, bindArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, "exec error --->>>" + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 通过id获取对象
     *
     * @param tableName
     * @param id
     * @return
     * @author lucg 20120808
     */
    public Cursor selectById(String tableName, String id) {
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            cursor = db.query(tableName, null, "_id=?", new String[]{id}, null, null, null, null);
            Log.e(TAG, "::selectById: " + tableName + "query Success!");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Log.e(TAG, "selectById error --->>>" + e.getMessage());
        } finally {
            db.endTransaction();
        }
        return cursor;
    }

    /**
     * 关闭所有数据库
     */
    public void closeDatabase() {
        try {
            close();
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, "closeDatabase close fail-->>" + e.getMessage());
        }
    }

    /**
     * 关闭当前正在使用的数据库 正处于打开状态的数据库
     */
    public void close() {
        try {
            if (db != null) {
                if (db.isOpen()) {
                    db.close();
                    db = null;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "close fail--->>" + e.getMessage());
        }
    }
}
