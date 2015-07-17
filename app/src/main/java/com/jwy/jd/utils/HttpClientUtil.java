package com.jwy.jd.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by zhangxd on 2015/7/14.
 *
 * HTTPÕ¯¬Á«Î«Û
 */
public class HttpClientUtil{
    private static final String BASE_URL="http://192.168.1.116:8080/";
    private static AsyncHttpClient mClient = new AsyncHttpClient();

    //ÃÌº”auth_token
    public static void setAuthToken(String auth_token)
    {
        mClient.addHeader("",auth_token);
    }

    static {
        mClient.setTimeout(2*1000);
    }

    public static void get(String url,HashMap<String,Object> argMaps,AsyncHttpResponseHandler responseHandler) {

       if(argMaps!=null && argMaps.size()>0)
        {
            RequestParams params=new RequestParams();
            Iterator iter= argMaps.entrySet().iterator();
            while (iter.hasNext())
            {
                Map.Entry entry=(Map.Entry)iter.next();
                params.put(entry.getKey().toString(),entry.getValue());
            }
            mClient.get(getAbsoluteUrl(url), params, responseHandler);
        }
        else
        {
            mClient.get(getAbsoluteUrl(url),responseHandler);
        }
    }
    public static void post(String url,HashMap<String,Object> argMaps, AsyncHttpResponseHandler responseHandler) {
        if(argMaps!=null && argMaps.size()>0)
        {
            RequestParams params=new RequestParams();
            Iterator iter= argMaps.entrySet().iterator();
            while (iter.hasNext())
            {
                Map.Entry entry=(Map.Entry)iter.next();
                params.put(entry.getKey().toString(),entry.getValue());
            }
            mClient.post(getAbsoluteUrl(url), params, responseHandler);
        }
        else
        {
            mClient.post(getAbsoluteUrl(url),responseHandler);
        }
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL.concat(relativeUrl);
    }
}
