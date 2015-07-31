package com.chaqianma.jd.utils;

import com.chaqianma.jd.model.UploadFileInfo;
import com.loopj.android.http.BinaryHttpResponseHandler;
import org.apache.http.Header;

/**
 * Created by zhangxd on 2015/7/31.
 */
public class JDBinaryResponseHandler extends BinaryHttpResponseHandler {

    private ResponseHandler handler = null;

    private UploadFileInfo fileInfo = null;

    public JDBinaryResponseHandler(UploadFileInfo fileInfo, ResponseHandler handler) {
        this.handler = handler;
        this.fileInfo=fileInfo;
    }

    @Override
    public void onSuccess(int i, Header[] headers, byte[] bytes) {

    }

    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

    }
}
