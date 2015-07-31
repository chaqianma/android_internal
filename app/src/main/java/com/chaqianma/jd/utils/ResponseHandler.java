package com.chaqianma.jd.utils;

/**
 * Created by zhangxd on 2015/7/15.
 */
public abstract class ResponseHandler<T> {
    public abstract void onSuccess(T t);
    public  void onFailure(String data) {}
    public  void onFailure(T t) {}
}
