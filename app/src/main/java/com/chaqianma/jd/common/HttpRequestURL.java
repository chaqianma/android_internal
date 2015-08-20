package com.chaqianma.jd.common;

/**
 * Created by zhangxd on 2015/7/15.
 * 网络请求链接常量
 */
public class HttpRequestURL {
    //请求URL 192.168.199.112
    public static final String BASEURL = "http://192.168.199.223:8088/";  //8088
    //public static final String BASEURL="http://rest-staging.chaqianma.com/"; //http://staging.chaqianma.com/
    //登录 POST
    public static final String LoginUrl = "api/user/login";
    //上传位置信息
    public static final String uploadLocationUrl="api/user/location/update";
    //尽调申请(贷款)
    public static final String loanApplyUrl = "api/borrow/dealing";
    //修改密码
    public static final String updatePasswordUrl = "api/user/changepassword";
    //尽调员转出尽调
    public static final String transformUrl = "api/borrow/transform";
    //结束尽调任务
    public static final String finishTaskUrl = "api/borrow/finish";
    //更换尽调员状态
    public static final String changeStateUrl = "api/user/isbusy/update";
    //开始尽调
    public static final String beginCheckUrl = "api/borrow/accept";
    //实名认证
    public static final String realNameAuthenticationUrl = "api/borrow/idnumber/verify";
    //上传图片
    public static final String uploadImgUrl = "api/file/upload";
    //个人信息查询路径
    public static final String personalInfoUrl = "api/borrow/dd/details";
    //下载
    public static final String downLoadFileUrl = "api/file/download/";
    //删除文件
    public static final String deleteFileUrl = "api/file/delete/";
    //修改个人信息
    public static final String updatePersonUrl = "api/borrow/dd/userbaseinfo/update/";
    //修改企业信息
    //public static final String updateBusinessInfoUrl="api/borrow/dd/businessinfo/update/";
    //更新个人资产信息
    public static final String updatePersonAssetUrl = "/api/borrow/dd/personalassetsinfo/update/";
    //批量修改企业信息
    public static final String updateBusinessInfoUrl = "api/borrow/dd/businessinfo/update/batch/";
    //修改社会关系信息
    public static final String updateSocialRelationURL = "api/borrow/dd/socialrelation/update/";
    //获取催收记录
    public static final String getRepaymentList = "api/repayment/dunning/list";
    //获取推送催收记录
    public static final String getNotifyRepaymentList = "api/repayment/dunning/ids";
    //获取催收详情  催收次数
    public static final String getRepaymentDetail = "api/repayment/dunning/";
}
