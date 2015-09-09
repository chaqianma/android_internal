package com.chaqianma.jd.utils;

import com.chaqianma.jd.model.UploadFileInfo;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zhangxd on 2015/9/9.
 * 下载队列
 */
public class QueueUtil {
    private static QueueUtil mQueueUtil = null;
    private BlockingQueue<UploadFileInfo> mBlockingQueue = null;
    private Thread mDownloadWorkerThread = null;

    private QueueUtil() {
        mBlockingQueue = new LinkedBlockingQueue<UploadFileInfo>();
        mDownloadWorkerThread = new Thread();
    }

    public static QueueUtil getInstance() {
        if (mQueueUtil == null) {
            synchronized (DownloadFileUtil.class) {
                if (mQueueUtil == null)
                    mQueueUtil = new QueueUtil();
            }
        }
        return mQueueUtil;
    }

    /**
     * 添加下载文件到队列中
     */
    public void addDownloadQueue(UploadFileInfo uploadFileInfo,AsyncHttpResponseHandler responseHandler) {
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
