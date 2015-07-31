package com.chaqianma.jd.model;

import java.io.Serializable;

/**
 * Created by zhangxd on 2015/7/27.
 *
 * 借款人
 */
public class BorrowRequestInfo implements Serializable {
   // {"borrowRequestId":1,"userId":1,"name":"黄啸宇","mobile":"13913999601","staffUserId":1,"amount":10000.0,"length":10,"borrowPurpose":null,"dateline":1435461948607,"location":null,"status":1}
    private String borrowRequestId=null;
    private String userId=null;
    private String name=null;
    private String mobile=null;
    private String staffUserId=null;
    private String amount=null;
    private String length=null;
    private String borrowPurpose=null;
    private String dateline=null;
    private String location=null;
    private String status=null;   // 1 没点  2点了

    public String getBorrowRequestId() {
        return borrowRequestId;
    }

    public void setBorrowRequestId(String borrowRequestId) {
        this.borrowRequestId = borrowRequestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStaffUserId() {
        return staffUserId;
    }

    public void setStaffUserId(String staffUserId) {
        this.staffUserId = staffUserId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getBorrowPurpose() {
        return borrowPurpose;
    }

    public void setBorrowPurpose(String borrowPurpose) {
        this.borrowPurpose = borrowPurpose;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
