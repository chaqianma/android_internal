package com.chaqianma.jd.utils;

import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.UploadFileInfo;
import com.chaqianma.jd.model.UploadFileType;
import com.chaqianma.jd.model.UploadStatus;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zhangxd on 2015/9/9.
 * 队列上传文件
 */
public class DownloadFileUtil {
    private static DownloadFileUtil mDownloadFileUtil = null;
    private BlockingQueue<UploadFileInfo> mBlockingQueue = null;
    private Thread mDownloadWorkerThread = null;

    private DownloadFileUtil() {
        mBlockingQueue = new LinkedBlockingQueue<UploadFileInfo>();
        mDownloadWorkerThread = new Thread();
    }

    public static DownloadFileUtil getInstance() {
        if (mDownloadFileUtil == null) {
            synchronized (DownloadFileUtil.class) {
                if (mDownloadFileUtil == null)
                    mDownloadFileUtil = new DownloadFileUtil();
            }
        }
        return mDownloadFileUtil;
    }

    /**
     * 添加下载文件到队列中
     */
    public void addDownloadQueue(UploadFileInfo uploadFileInfo,ResponseHandler responseHandler) {
        try {
            if (!isExist(uploadFileInfo)) {
                if (mBlockingQueue.size() < 10)
                    mBlockingQueue.put(uploadFileInfo);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否存在需要的下载文件
     */
    private boolean isExist(UploadFileInfo uploadFileInfo) {
        return mBlockingQueue.contains(uploadFileInfo);
    }
}
