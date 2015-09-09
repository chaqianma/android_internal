package com.chaqianma.jd.utils;

import android.content.Context;
import android.os.Looper;

import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.UploadFileInfo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
        AppData.getInstance().setHeader(auth_token);
    }

    static {
        mClient.setTimeout(6 * 1000);
        mClient.setMaxConnections(10);
        BasicHttpParams localBasicHttpParams = new BasicHttpParams();
        ConnManagerParams.setMaxTotalConnections(localBasicHttpParams, 5);
       /* AsyncHttpClient client = new AsyncHttpClient();
        int limit = 20;
        BlockingQueue<Runnable> q = new ArrayBlockingQueue<Runnable>(limit);
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(limit, limit, 20, TimeUnit.SECONDS, q);
        client.setThreadPool(executor);
        parseSilo(url, context); // this fires client.get() ... as it encounters urls in JSON feed
        executor.shutdown();
        while (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
            Log.e(TAG, executor.getTaskCount() + " tasks left");
        }*/
    /*    AsyncHttpClientConfigBea config = new AsyncHttpClientConfig.Builder()
                .setMaximumConnectionsPerHost(10)
                .setMaximumConnectionsTotal(100)
                .build();
        AsyncHttpClient c = new AsyncHttpClient(config);*/
    }

    public static void get(String url, HashMap<String, Object> argMaps, AsyncHttpResponseHandler responseHandler) {
        if (argMaps != null && argMaps.size() > 0)
            mClient.get(getAbsoluteUrl(url), getRequestParams(argMaps), responseHandler);
        else
            mClient.get(getAbsoluteUrl(url), responseHandler);
    }

    public static void download(String url, UploadFileInfo uploadFileInfo, AsyncHttpResponseHandler responseHandler) {
        //QueueUtil.getInstance().addDownloadQueue(uploadFileInfo, responseHandler);
    }

    public static void post(String url, HashMap<String, Object> argMaps, AsyncHttpResponseHandler responseHandler) {
        if (argMaps != null && argMaps.size() > 0)
            mClient.post(getAbsoluteUrl(url), getRequestParams(argMaps), responseHandler);
        else
            mClient.post(getAbsoluteUrl(url), responseHandler);
    }

    public static void post(Context context, String url, HttpEntity entity, AsyncHttpResponseHandler responseHandler) {
        if (entity != null)
            mClient.post(context, getAbsoluteUrl(url), entity, null, responseHandler);
    }


    public static void put(String url, HashMap<String, Object> argMaps, AsyncHttpResponseHandler responseHandler) {
        //Content-Type: "application/x-www-form-urlencoded; charset=UTF-8"
        if (argMaps != null && argMaps.size() > 0)
            mClient.put(getAbsoluteUrl(url), getRequestParams(argMaps), responseHandler);
        else
            mClient.put(getAbsoluteUrl(url), responseHandler);
    }

    public static void put(Context context, String url, List<NameValuePair> formparams, AsyncHttpResponseHandler responseHandler) {
        if (formparams != null && formparams.size() > 0) {
            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, HTTP.UTF_8);
                mClient.put(context, getAbsoluteUrl(url), entity, null, responseHandler);
            } catch (Exception e) {

            }
        } else
            mClient.put(getAbsoluteUrl(url), responseHandler);
    }

    public static void delete(String url, HashMap<String, Object> argMaps, AsyncHttpResponseHandler responseHandler) {
        if (argMaps != null && argMaps.size() > 0)
            mClient.delete(getAbsoluteUrl(url), getRequestParams(argMaps), responseHandler);
        else
            mClient.delete(getAbsoluteUrl(url), responseHandler);
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
        String sss = HttpRequestURL.BASEURL.concat(relativeUrl);
        return HttpRequestURL.BASEURL.concat(relativeUrl);
    }
}
