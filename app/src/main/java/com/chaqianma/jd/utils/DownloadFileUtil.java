package com.chaqianma.jd.utils;

import com.chaqianma.jd.model.UploadFileInfo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zhangxd on 2015/9/9.
 * 队列上传文件
 */
public class DownloadFileUtil {
    private static DownloadFileUtil mUploadFileUtil = null;
    private BlockingQueue<UploadFileInfo> mBlockingQueue = null;
    private Thread mUploadWorkerThread = null;

    private DownloadFileUtil() {
        mBlockingQueue = new LinkedBlockingQueue<UploadFileInfo>();
        mUploadWorkerThread = new Thread();
    }

    public static DownloadFileUtil getInstance() {
        if (mUploadFileUtil == null) {
            synchronized (DownloadFileUtil.class) {
                if (mUploadFileUtil == null)
                    mUploadFileUtil = new DownloadFileUtil();
            }
        }
        return mUploadFileUtil;
    }

    public void ()

}
