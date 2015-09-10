package com.chaqianma.jd.utils;

import android.util.SparseArray;

import com.chaqianma.jd.model.UploadFileInfo;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zhangxd on 2015/9/9.
 * 下载队列
 */
public class QueueUtil {
    private static QueueUtil mQueueUtil = null;
    private BlockingQueue<UploadFileInfo> mBlockingQueue = null;
    private volatile Thread mDownloadWorkerThread = null;
    private volatile boolean mStarted = false;
    //数据池
    private List<UploadFileInfo> mUploadFileInfoList=null;
    private QueueUtil() {
        mBlockingQueue = new LinkedBlockingQueue<UploadFileInfo>();
        mUploadFileInfoList=new ArrayList<UploadFileInfo>();
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
    public void addDownloadQueue(UploadFileInfo fileInfo) {
        try {
            if (!isExist(fileInfo)) {
                if (mBlockingQueue.size() < 10)
                    mBlockingQueue.put(fileInfo);
                else {
                    //每轮控制10个请求
                    mUploadFileInfoList.add(fileInfo);
                    //线程不为null 启动状态  当前线程没有中断
                    while (mDownloadWorkerThread!=null && mStarted && !Thread.currentThread().isInterrupted())
                    {

                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载任务
     */
    private final class DownloadTask implements Runnable {

        public DownloadTask() {

        }

        /**
         * 下载
         */
        private void download() throws InterruptedException {
            UploadFileInfo fileInfo = null;
            while (mStarted && !Thread.currentThread().isInterrupted()) {
                fileInfo = mBlockingQueue.take();
                synchronized (QueueUtil.class) {
                    //下载
                    new DownloadFileUtil(fileInfo);
                }
            }
        }

        @Override
        public void run() {
            try {
                download();
            } catch (InterruptedException e) {
                mStarted = false;
            } catch (RuntimeException e) {
                mStarted = false;
            } finally {
            }
        }
    }

    /**
     * 开始下载
     */
    public synchronized void start() {
        if (null == mDownloadWorkerThread) {
            mDownloadWorkerThread = new Thread(new DownloadTask(), System.currentTimeMillis() + "");
        }
        if (mStarted) {
            return;
        }
        mStarted = true;
        if (mDownloadWorkerThread.isInterrupted())
            mDownloadWorkerThread.start();
    }

    /**
     * 停止下载
     */
    public synchronized void stop() {
        mStarted = false;
        mBlockingQueue.clear();
        if (null != mDownloadWorkerThread) {
            mDownloadWorkerThread.interrupt();
            mDownloadWorkerThread = null;
        }
    }

    /**
     * 是否存在需要的下载文件
     */
    private boolean isExist(UploadFileInfo uploadFileInfo) {
        return mBlockingQueue.contains(uploadFileInfo);
    }
}
