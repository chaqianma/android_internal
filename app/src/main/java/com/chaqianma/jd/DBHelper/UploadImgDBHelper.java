package com.chaqianma.jd.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.chaqianma.jd.model.UploadFileInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * Created by zhangxd on 2015/7/29.
 */
public class UploadImgDBHelper {
    private static UploadImgDBHelper uploadImgDBHelper = null;
    /**
     * 数据库操作对象
     */
    private DBBaseHelper db;

    /**
     * 同步锁
     */
    private static Object Lockobj = new Object();

    private UploadImgDBHelper(Context context) {
        if (null == db) {
            if (null == context) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            db = new DBBaseHelper(context);
        }
    }

    public static UploadImgDBHelper getInstance(Context context) {
        if (uploadImgDBHelper == null) {
            synchronized (UploadImgDBHelper.class) {
                if (uploadImgDBHelper == null)
                    uploadImgDBHelper = new UploadImgDBHelper(context);
            }
        }
        return uploadImgDBHelper;
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
    public boolean insert(ContentValues values) {
        if (values == null) {
            return false;
        }
        synchronized (Lockobj) {
            long id = db.insert(ImageTable.TABLE_NAME, values);
            if (id != -1) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 获取所有数据
     */
    public List<UploadFileInfo> getAllUploadItem(String sortKey) {
        List<UploadFileInfo> listItems = new ArrayList<UploadFileInfo>();
        synchronized (Lockobj) {
            Cursor cursor = null;
            try {
                cursor = db.query(ImageTable.TABLE_NAME, null, null, null, sortKey + " asc");
                if (cursor == null) {
                    return listItems;
                }
                listItems = fillUploadItem(cursor);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
            }
            return listItems;
        }
    }


    public List<UploadFileInfo> query(String[] queryKeys, String[] queryValues, String sortKey) {
        List<UploadFileInfo> listItems = new ArrayList<UploadFileInfo>();
        synchronized (Lockobj) {
            Cursor cursor = null;
            try {
                cursor = db.query(ImageTable.TABLE_NAME, queryKeys, queryValues, null, sortKey + " asc");
                if (cursor == null || cursor.getCount() <= 0) {
                    return listItems;
                }
                listItems = fillUploadItem(cursor);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
            }
            return listItems;
        }
    }

    /**
     * 解析本地文件
     */
    private List<UploadFileInfo> fillUploadItem(Cursor cursor) {
        List<UploadFileInfo> listItems = new ArrayList<UploadFileInfo>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                UploadFileInfo uploadImgItem = new UploadFileInfo();
                uploadImgItem.setId(cursor.getInt(cursor.getColumnIndex(ImageTable._ID)));
                uploadImgItem.setBorrowRequestId(cursor.getString(cursor.getColumnIndex(ImageTable.BORROW_REQUESTID)));
                uploadImgItem.setImgName(cursor.getString(cursor.getColumnIndex(ImageTable.IMGNAME)));
                uploadImgItem.setSmallImgPath(cursor.getString(cursor.getColumnIndex(ImageTable.SMALLIMGPATH)));
                uploadImgItem.setBigImgPath(cursor.getString(cursor.getColumnIndex(ImageTable.BIGIMGPATH)));
                uploadImgItem.setFileType(cursor.getInt(cursor.getColumnIndex(ImageTable.FILETYPE)));
                uploadImgItem.setParentTableName(cursor.getString(cursor.getColumnIndex(ImageTable.PARENT_TABLE_NAME)));
                uploadImgItem.setParentId(cursor.getString(cursor.getColumnIndex(ImageTable.PARENT_ID)));
                uploadImgItem.setFileId(cursor.getString(cursor.getColumnIndex(ImageTable.FILEID)));
                uploadImgItem.setStatus(cursor.getInt(cursor.getColumnIndex(ImageTable.STATUS)));
                uploadImgItem.setDateline(cursor.getString(cursor.getColumnIndex(ImageTable.DATELINE)));
                uploadImgItem.setIdxTag(cursor.getInt(cursor.getColumnIndex(ImageTable.IDXTAG)));
                listItems.add(uploadImgItem);
            } while (cursor.moveToNext());
        }
        return listItems;
    }

    /**
     * 插入数据
     */
    public boolean insert(UploadFileInfo imgInfo) {
        if (imgInfo == null) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(ImageTable.BORROW_REQUESTID, imgInfo.getBorrowRequestId());
        contentValues.put(ImageTable.IMGNAME, imgInfo.getImgName());
        contentValues.put(ImageTable.FILEID, imgInfo.getFileId());
        contentValues.put(ImageTable.SMALLIMGPATH, imgInfo.getSmallImgPath());
        contentValues.put(ImageTable.BIGIMGPATH, imgInfo.getBigImgPath());
        contentValues.put(ImageTable.FILETYPE, imgInfo.getFileType());
        contentValues.put(ImageTable.PARENT_TABLE_NAME, imgInfo.getParentTableName());
        contentValues.put(ImageTable.PARENT_ID, imgInfo.getParentId());
        contentValues.put(ImageTable.STATUS, imgInfo.getStatus());
        contentValues.put(ImageTable.DATELINE, imgInfo.getDateline());
        contentValues.put(ImageTable.IDXTAG, imgInfo.getIdxTag());
        return insert(contentValues);
    }

    /**
     * 删除数据
     */
    public void delete(int id) {
        if (id < 0)
            return;
        db.delete(ImageTable.TABLE_NAME, new String[]{ImageTable._ID}, new String[]{id + ""});
    }

    /**
     * 更新表数据
     */
    public void update(int id, ContentValues values) {
        if (values == null) {
            return;
        }
        synchronized (Lockobj) {
            db.update(ImageTable.TABLE_NAME, values, new String[]{ImageTable._ID}, new String[]{id + ""});
        }
    }
}
