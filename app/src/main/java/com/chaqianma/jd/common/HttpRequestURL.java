package com.chaqianma.jd.common;

/**
 * Created by zhangxd on 2015/7/15.
 * 网络请求链接常量
 */
public class HttpRequestURL {

    //登录 POST
    public static final String LoginUrl="api/user/login";
    //尽调申请(贷款)
    public static final String loanApplyUrl="api/borrow/dealing";
    //修改密码
    public static final String updatePasswordUrl="api/user/changepassword";
    //尽调员转出尽调
    public static final String transformUrl="api/borrow/transform";
    //更换尽调员状态
    public static final String changeStateUrl="api/user/isbusy/update";
}
