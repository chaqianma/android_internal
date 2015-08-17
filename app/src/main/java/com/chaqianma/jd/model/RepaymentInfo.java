package com.chaqianma.jd.model;

/**
 * Created by zhangxd on 2015/8/14.
 */
public class RepaymentInfo extends ErrorInfo {
    private String id = null;
    private String name=null;
    private String phone=null;
    private String investmentNo=null;  //标的ID
    private String userMobile=null;
    private String userName=null;
    private String userWorkLocation=null;
    private String interestAmount = null;//应还利息
    private String overdueFee = null;//滞纳金
    private String period = null; //期数
    private String principalAmount = null;//应还本金
    private String repaymentDateline = null;//还款日期
    private String actualDateline = null;//实际还款日期
    private String dunningCount = null;//催收次数
    private String repaymentMethod = null;//还款方式 1平台账户 2银行代扣
    private String status = null; //还款状态 1待还款 2账单日 3逾期 4已还款
    private String money = null;//应还金额
    private String descStatus = null;
    private String borrowMoney=null;
    private String borrowDate=null;
    private String borrowPurpose=null;//借款用途
    private int flag = 1;  //标明  0 新任务  1 催款
    public String getActualDateline() {
        return actualDateline;
    }

    public void setActualDateline(String actualDateline) {
        this.actualDateline = actualDateline;
    }

    public String getDunningCount() {
        return dunningCount;
    }

    public void setDunningCount(String dunningCount) {
        this.dunningCount = dunningCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(String interestAmount) {
        this.interestAmount = interestAmount;
    }

    public String getOverdueFee() {
        return overdueFee;
    }

    public void setOverdueFee(String overdueFee) {
        this.overdueFee = overdueFee;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(String principalAmount) {
        this.principalAmount = principalAmount;
    }

    public String getRepaymentDateline() {
        return repaymentDateline;
    }

    public void setRepaymentDateline(String repaymentDateline) {
        this.repaymentDateline = repaymentDateline;
    }

    public String getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(String repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMoney() {
        return getRepaymentMoney() + "";
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDescStatus() {

        //还款状态 1待还款 2账单日 3逾期 4已还款
        switch (Integer.parseInt(getStatus())) {
            case 1:
                descStatus = "待还款";
                break;
            case 2:
                descStatus = "账单日";
                break;
            case 3:
                descStatus = "逾期";
                break;
            case 4:
                descStatus = "已还款";
                break;
            default:
                break;
        }
        return descStatus;
    }

    public void setDescStatus(String descStatus) {
        this.descStatus = descStatus;
    }

    /*
    * 获取应还金额
    * */
    private int getRepaymentMoney() {
        int money = 0;
        try {
            money += Integer.parseInt(getPrincipalAmount()) + Integer.parseInt(getInterestAmount()) + Integer.parseInt(getOverdueFee());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlag() {
        return flag;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBorrowMoney() {
        return borrowMoney;
    }

    public void setBorrowMoney(String borrowMoney) {
        this.borrowMoney = borrowMoney;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getBorrowPurpose() {
        return borrowPurpose;
    }

    public void setBorrowPurpose(String borrowPurpose) {
        this.borrowPurpose = borrowPurpose;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getInvestmentNo() {
        return investmentNo;
    }

    public void setInvestmentNo(String investmentNo) {
        this.investmentNo = investmentNo;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserWorkLocation() {
        return userWorkLocation;
    }

    public void setUserWorkLocation(String userWorkLocation) {
        this.userWorkLocation = userWorkLocation;
    }
}
