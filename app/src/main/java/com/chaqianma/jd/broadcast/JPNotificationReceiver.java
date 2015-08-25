package com.chaqianma.jd.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.chaqianma.jd.DBHelper.RepaymentDBHelper;
import com.chaqianma.jd.activity.LoginActivity;
import com.chaqianma.jd.activity.MainActivity;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.BorrowRequestInfo;
import com.chaqianma.jd.model.RepaymentInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.utils.SharedPreferencesUtil;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zhangxd on 2015/7/20.
 * <p/>
 * 借款申请状态： -3弃标 -2驳回补充资料 -1用户取消 0待预审 1待分配 2已分配 3尽调中 4录入中
 * 5初审中 6复审中 7会审中 8主管审核中 9合同签订中 10生成标的中 11已生成标的
 * 20、账单日 21、还款逾期
 */
public class JPNotificationReceiver extends BroadcastReceiver {
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            //接受到推送下来的通知
            /*
            * bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE)
            * bundle.getString(JPushInterface.EXTRA_ALERT)
            * bundle.getString(JPushInterface.EXTRA_EXTRA)
            * */
            String msgType = getMsgType(intent.getExtras());
            if (msgType.equals("2")) {

            } else if (msgType.equals("20") || (msgType.equals("21"))) {
                SaveRepaymentId(intent.getExtras());
            } else {

            }

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            //1 新任务  5 还款日  6 逾期
            //接受到推送下来的自定义消息
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            //打开了推送通知
            String msgType = getMsgType(intent.getExtras());
            if (AppData.getInstance().getUserInfo() == null) {
                if (msgType.equals("2") || msgType.equals("20") || msgType.equals("21")) {
                    Intent startIntent = new Intent();
                    startIntent.setClass(context, LoginActivity.class);
                    startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(startIntent);
                }
            } else {
                Intent startIntent = new Intent();
                startIntent.setClass(context, MainActivity.class);
                startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (msgType.equals("2")) {
                    startIntent.putExtra(Constants.REFRESH, true);
                    context.startActivity(startIntent);
                } else {
                    context.startActivity(startIntent);
                }
            }
        } else {
        }
    }

    /*
    * 获取通知类型
    * */
    private String getMsgType(Bundle bundle) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
            return jsonObject.getString("messageType");
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    /*
    * 保存催收ID
    * */
    private void SaveRepaymentId(Bundle bundle) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
            RepaymentDBHelper dbHelper = new RepaymentDBHelper();
            RepaymentInfo repaymentInfo = new RepaymentInfo();
            repaymentInfo.setId(jsonObject.getString("repaymentId"));
            repaymentInfo.setDateLine(jsonObject.getString("dateline"));
            dbHelper.insert(mContext, repaymentInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
