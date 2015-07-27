package com.chaqianma.jd.model;

import java.io.Serializable;

/**
 * Created by zhangxd on 2015/7/15.
 */
public class UserInfo extends ErrorInfo implements Serializable {
    //{"userId":1,"mobile":"13913999601","password":"$2a$10$r0nQQraNY0.aBZKhxdErWeKweXDAjSa5zyrAK8NGgHkd5Os6Tox.O",
    // "email":null,"name":"马化腾","idNumber":"","nameIdVerified":0,"userType":0,"balanceInvestment":8900.0,"balanceGuaranty":1000.0}
    private String userId=null;
    private String mobile=null;
    private String password=null;
    private String email=null;
    private String name=null;
    private String idNumber=null;
    private String nameIdVerified=null;
    private String userType=null;
    private String balanceInvestment=null;
    private String balanceGuaranty=null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getNameIdVerified() {
        return nameIdVerified;
    }

    public void setNameIdVerified(String nameIdVerified) {
        this.nameIdVerified = nameIdVerified;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getBalanceInvestment() {
        return balanceInvestment;
    }

    public void setBalanceInvestment(String balanceInvestment) {
        this.balanceInvestment = balanceInvestment;
    }

    public String getBalanceGuaranty() {
        return balanceGuaranty;
    }

    public void setBalanceGuaranty(String balanceGuaranty) {
        this.balanceGuaranty = balanceGuaranty;
    }
}
