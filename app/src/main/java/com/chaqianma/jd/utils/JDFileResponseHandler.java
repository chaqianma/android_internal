package com.chaqianma.jd.utils;

import android.content.Context;

import com.chaqianma.jd.model.UploadFileInfo;
import com.chaqianma.jd.model.UploadStatus;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;

/**
 * Created by zhangxd on 2015/7/31.
 * 图片下载
 */
public class JDFileResponseHandler extends FileAsyncHttpResponseHandler {

    private UploadFileInfo mUploadFileInfo = null;
    private Context mContext = null;
    private ResponseHandler handler;


    public JDFileResponseHandler(UploadFileInfo uploadFileInfo, ResponseHandler handler) {
        super(new File(uploadFileInfo.getBigImgPath()));
        this.mUploadFileInfo = uploadFileInfo;
        this.handler = handler;
    }

    @Override
    public void onSuccess(int i, Header[] headers, File file) {
        if (file != null) {
            handler.onSuccess(mUploadFileInfo);
        }
    }

    @Override
    public void onFailure(int i, Header[] headers, Throwable throwable, File file) {
        throwable.printStackTrace();
        handler.onFailure(mUploadFileInfo);
    }
}
