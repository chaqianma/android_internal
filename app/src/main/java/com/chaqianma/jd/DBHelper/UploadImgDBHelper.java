package com.chaqianma.jd.DBHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.chaqianma.jd.model.UploadImgInfo;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by zhangxd on 2015/7/29.
 */
public class UploadImgDBHelper {
    private static final String TAG = UploadImgDBHelper.class.getSimpleName();
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
    public void iniUloadDBhelper(final Context context) {
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
    public boolean insert(final Context context, ContentValues values) {
        if (values == null) {
            return false;
        }
        iniUloadDBhelper(context);
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
     * 插入数据
     */
    public boolean insert(final Context context, UploadImgInfo imgInfo) {
        if (imgInfo == null) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(ImageTable.BORROW_REQUESTID, imgInfo.getBorrowRequestId());
        contentValues.put(ImageTable.IMGTYPE, imgInfo.getImgType());
        contentValues.put(ImageTable.IMGNAME, imgInfo.getImgName());
        contentValues.put(ImageTable.SMALLIMGPATH, imgInfo.getSmallImgPath());
        contentValues.put(ImageTable.BIGIMGPATH, imgInfo.getBigImgPath());
        contentValues.put(ImageTable.FILETYPE, imgInfo.getFileType());
        contentValues.put(ImageTable.PARENT_TABLE_NAME, imgInfo.getParentTableName());
        contentValues.put(ImageTable.PARENT_ID, imgInfo.getParentId());
        return insert(context, contentValues);
    }

    /**
     * 获取所有数据
     */
    public List<UploadImgInfo> getAllUploadItem(final Context context, String sortKey) {
        List<UploadImgInfo> listItems = new ArrayList<UploadImgInfo>();
        iniUloadDBhelper(context);
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


    public List<UploadImgInfo> query(final Context context, String[] queryKeys, String[] queryValues, String sortKey) {
        List<UploadImgInfo> listItems = new ArrayList<UploadImgInfo>();
        iniUloadDBhelper(context);
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
     * */
    private List<UploadImgInfo> fillUploadItem(Cursor cursor) {
        List<UploadImgInfo> listItems = new ArrayList<UploadImgInfo>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                UploadImgInfo uploadImgItem = new UploadImgInfo();
                uploadImgItem.setId(cursor.getInt(cursor.getColumnIndex(ImageTable._ID)));
                uploadImgItem.setBorrowRequestId(cursor.getString(cursor.getColumnIndex(ImageTable.BORROW_REQUESTID)));
                uploadImgItem.setImgType(cursor.getString(cursor.getColumnIndex(ImageTable.IMGTYPE)));
                uploadImgItem.setImgName(cursor.getString(cursor.getColumnIndex(ImageTable.IMGNAME)));
                uploadImgItem.setSmallImgPath(cursor.getString(cursor.getColumnIndex(ImageTable.SMALLIMGPATH)));
                uploadImgItem.setBigImgPath(cursor.getString(cursor.getColumnIndex(ImageTable.BIGIMGPATH)));
                uploadImgItem.setFileType(cursor.getInt(cursor.getColumnIndex(ImageTable.FILETYPE)));
                uploadImgItem.setParentTableName(cursor.getString(cursor.getColumnIndex(ImageTable.PARENT_TABLE_NAME)));
                uploadImgItem.setParentId(cursor.getString(cursor.getColumnIndex(ImageTable.PARENT_ID)));
                listItems.add(uploadImgItem);
            } while (cursor.moveToNext());
        }
        return listItems;
    }

    /**
     * 更新表数据
     */
    public void update(final Context context, int id, ContentValues values) {
        if (values == null) {
            return;
        }
        iniUloadDBhelper(context);
        synchronized (Lockobj) {
            db.update(ImageTable.TABLE_NAME, values, new String[]{ImageTable._ID}, new String[]{id + ""});
        }
    }
}
