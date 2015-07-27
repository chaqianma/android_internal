package com.chaqianma.jd.utils;

import com.chaqianma.jd.common.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by zhangxd on 2015/7/14
 * HTTP网络请求
 */
public class HttpClientUtil {
    private static AsyncHttpClient mClient = new AsyncHttpClient();
    //设置auth_token
    public static void setAuthToken(String auth_token) {
        mClient.addHeader(Constants.HEADERTAG, auth_token);
    }

    static {
        mClient.setTimeout(6 * 1000);
    }

    public static void get(String url, HashMap<String, Object> argMaps, AsyncHttpResponseHandler responseHandler) {
        if (argMaps != null && argMaps.size() > 0)
            mClient.get(getAbsoluteUrl(url), getRequestParams(argMaps), responseHandler);
        else
            mClient.get(getAbsoluteUrl(url), responseHandler);
    }

    public static void post(String url, HashMap<String, Object> argMaps, AsyncHttpResponseHandler responseHandler) {
        if (argMaps != null && argMaps.size() > 0)
            mClient.post(getAbsoluteUrl(url), getRequestParams(argMaps), responseHandler);
        else
            mClient.post(getAbsoluteUrl(url), responseHandler);
    }

    private static RequestParams getRequestParams(HashMap<String, Object> argMaps) {
        RequestParams params = new RequestParams();
        Iterator iterator = argMaps.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            params.put(entry.getKey().toString(), entry.getValue());
        }
        return params;
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        String url=Constants.BASEURL.concat(relativeUrl);
        return Constants.BASEURL.concat(relativeUrl);
    }
}
