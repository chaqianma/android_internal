package com.chaqianma.jd.utils;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.chaqianma.jd.model.ErrorInfo;
import com.google.gson.Gson;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.widget.JDProgress;
import com.chaqianma.jd.widget.JDToast;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by zhangxd on 2015/7/15.
 */
public class JDHttpResponseHandler extends AsyncHttpResponseHandler {

    private Context context;

    private ResponseHandler handler;

    private Class<?> dataType = null;

    private boolean isLogin = false;

    public JDHttpResponseHandler(Context context, ResponseHandler handler) {
        this(context, handler, null);
    }

    public JDHttpResponseHandler(Context context, ResponseHandler handler, Class<?> dataType) {
        this.context = context;
        this.handler = handler;
        this.dataType = dataType;
    }

    public JDHttpResponseHandler(Context context, ResponseHandler handler, Class<?> dataType, boolean isLogin) {
        this(context, handler, dataType);
        this.isLogin = isLogin;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (context != null)
            JDProgress.show(context);
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

    @Override
    public void onSuccess(int i, Header[] headers, byte[] bytes) {
        //Header
        if (isLogin) {
            int length = headers.length;
            Header header;
            for (int idx = length - 1; idx >= 0; idx--) {
                header = headers[idx];
                if (Constants.HEADERTAG.equalsIgnoreCase(header.getName())) {
                    HttpClientUtil.setAuthToken(header.getValue());
                    break;
                }
            }
        }
        if (bytes != null && bytes.length > 0) {
            Log.i("zxd:info", new String(bytes));
            String sss = new String(bytes);
            if (dataType == null)
                handler.onSuccess(new String(bytes));
            else
                handler.onSuccess(JSON.parseObject(bytes, dataType));
        } else {
            handler.onSuccess(null);
        }
        JDProgress.dismiss();
    }

    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
        try {
            if (bytes == null || new String(bytes).indexOf("refused") > -1) {
                showToast("服务器连接不上，请稍候再试");
            } else {
                if (dataType == null)
                    dataType = Class.forName(ErrorInfo.class.getName());
                ErrorInfo errorInfo = (ErrorInfo) JSON.parseObject(new String(bytes), dataType);
                if (errorInfo != null) {
                    String msg = errorInfo.getMessage().toLowerCase();
                    if(errorInfo.getPath().toLowerCase().indexOf("login") > -1 && msg.indexOf("uuid") > -1)
                        showToast("UUID不匹配");
                    if (errorInfo.getPath().toLowerCase().indexOf("login") > -1 && msg.indexOf("failed") > -1)
                        showToast("用户名或密码错误");
                    else if (msg.indexOf("available") > -1 || msg.indexOf("mybatis") > -1 || msg.indexOf("ibatis") > -1)
                        showToast("服务器请求错误，请稍候再试");
                    else
                        showToast(errorInfo.getMessage());
                }
            }
        } catch (Exception e) {
            showToast("服务器请求错误，请稍候再试");
        }
        if (context != null)
            JDProgress.dismiss();
        if (bytes != null && bytes.length > 0)
            handler.onFailure(new String(bytes));
    }

    private void showToast(String msg) {
        if (context != null) {
            JDToast.showShortText(context, msg);
        }
    }
}
