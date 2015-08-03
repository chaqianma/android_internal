package com.chaqianma.jd.common;

/**
 * Created by zhangxd on 2015/7/15.
 * 网络请求链接常量
 */
public class HttpRequestURL {
    //请求URL 192.168.199.112
    public static final String BASEURL= "http://192.168.199.112:8080/";
    //登录 POST
    public static final String LoginUrl="api/user/login";
    //尽调申请(贷款)
    public static final String loanApplyUrl="api/borrow/dealing";
    //修改密码
    public static final String updatePasswordUrl="api/user/changepassword";
    //尽调员转出尽调
    public static final String transformUrl="api/borrow/transform";
    //结束尽调任务
    public static final String finishTaskUrl="api/borrow/finish";
    //更换尽调员状态
    public static final String changeStateUrl="api/user/isbusy/update";
    //开始尽调
    public static final String beginCheckUrl="api/borrow/accept";
    //实名认证
    public static final String realNameAuthenticationUrl="api/borrow/idnumber/verify";
    //上传图片
    public static final String uploadImgUrl="api/file/upload";
    //个人信息查询路径
    public static final String personalInfoUrl="api/borrow/dd/details";
    //下载
    public static final String downLoadFileUrl="api/file/download/";
    //删除文件
    public static final String deleteFileUrl="api/file/delete/";
    //修改个人信息
    public static final String updatePersonUrl="api/borrow/dd/userbaseinfo/update/";
    //修改企业信息
    public static final String updateBusinessInfoUrl="api/borrow/dd/businessinfo/update/";

    public static final String sss="api/borrow/dd/businessinfo/update/";
}
