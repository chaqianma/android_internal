package com.chaqianma.jd.utils;

import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.UploadFileInfo;
import com.loopj.android.http.SyncHttpClient;

/**
 * Created by zhangxd on 2015/9/9.
 * 下载
 */
public class DownloadFileUtil {

    public DownloadFileUtil(UploadFileInfo fileInfo) {

    }

    public DownloadFileUtil(UploadFileInfo fileInfo, ResponseHandler responseHandler) {
        //线程中下载。用同步
        SyncHttpClient syncHttpClient = new SyncHttpClient();
        syncHttpClient.get(HttpRequestURL.downLoadFileUrl + "/" + fileInfo.getFileId(), new JDFileResponseHandler(fileInfo, responseHandler));
    }
}
