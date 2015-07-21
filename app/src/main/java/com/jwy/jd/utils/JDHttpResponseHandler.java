package com.jwy.jd.utils;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.jwy.jd.widget.JDProgress;
import com.jwy.jd.widget.JDToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.Header;

/**
 * Created by zhangxd on 2015/7/15.
 */
public  class JDHttpResponseHandler extends AsyncHttpResponseHandler {

    private Context context;

    private ResponseHandler handler;

    private Class<?> dataType=null;

    private boolean isLogin=false;
    public JDHttpResponseHandler(Context context,ResponseHandler handler)
    {
        this(context,handler,null);
    }

    public JDHttpResponseHandler(Context context,ResponseHandler handler,Class<?> dataType)
    {
        this.context=context;
        this.handler=handler;
        this.dataType=dataType;
    }

    public JDHttpResponseHandler(Context context,ResponseHandler handler,Class<?>dataType,boolean isLogin)
    {
        this(context,handler,dataType);
        this.isLogin=isLogin;
    }

    @Override
    public void onStart() {
        super.onStart();
        JDProgress.show(context);
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

    @Override
    public void onSuccess(int i, Header[] headers, byte[] bytes) {
        //Header
        if(isLogin)
        {
            int length=headers.length;
            Header header;
            for (int idx =length - 1; idx >= 0; idx--) {
                header=headers[idx];
                if("X-AUTH-TOKEN".equalsIgnoreCase(header.getName()))
                {
                    HttpClientUtil.setAuthToken(header.getValue());
                    break;
                }
            }
        }
        if(bytes!=null && bytes.length>0)
        {
            if(dataType==null)
                handler.onSuccess(new String(bytes));
            else
                handler.onSuccess(JSON.parseObject(bytes,dataType));
        }
        JDProgress.dismiss();
    }
    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
        JDToast.showShortText(context, "请求出错");
        JDProgress.dismiss();
    }
}
