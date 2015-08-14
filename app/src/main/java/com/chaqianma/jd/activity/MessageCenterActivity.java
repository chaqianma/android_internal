package com.chaqianma.jd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.chaqianma.jd.R;
import com.chaqianma.jd.adapters.MessageAdapater;
import com.chaqianma.jd.adapters.RepaymentListAdapter;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.BorrowRequestInfo;
import com.chaqianma.jd.model.RepaymentInfo;
import com.chaqianma.jd.model.UrgeInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxd on 2015/8/11
 * 消息中心
 */
public class MessageCenterActivity extends BaseActivity {
    @InjectView(R.id.list_msgs)
    ListView list_msgs;
    private List<RepaymentInfo> mRepaymentList = null;
    private MessageAdapater messageAdapater = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        ButterKnife.inject(this);
        initData();
    }

    /*
    * 初始化数据
    * */
    private void initData() {
        mRepaymentList = new ArrayList<RepaymentInfo>();
        messageAdapater = new MessageAdapater(MessageCenterActivity.this, mRepaymentList);
        list_msgs.setAdapter(messageAdapater);
        //获取任务信息
        getBorrowRequest();
        //获取催收信息
        getRepaymentList();
        list_msgs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < mRepaymentList.size()) {
                    RepaymentInfo rInfo = mRepaymentList.get(position);
                    //0 新任务  1 催款
                    if (rInfo.getFlag() == 1) {
                        startActivity(RepaymentDetailActivity.class, rInfo.getId());
                    } else {
                        startActivity(MainActivity.class);
                    }
                }
            }
        });
    }

    /*
    *查看是否有任务
    */
    private void getBorrowRequest() {
        try {
            if (AppData.getInstance().getBorrowRequestInfo() != null) {
                transform(AppData.getInstance().getBorrowRequestInfo());
            } else {
                HttpClientUtil.get(HttpRequestURL.loanApplyUrl, null, new JDHttpResponseHandler(MessageCenterActivity.this, new ResponseHandler<BorrowRequestInfo>() {
                    @Override
                    public void onSuccess(BorrowRequestInfo borrowRequestInfo) {
                        if (borrowRequestInfo != null) {
                            transform(borrowRequestInfo);
                        }
                    }
                }, Class.forName(BorrowRequestInfo.class.getName())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
   * 获取催款数据
   * */
    private void getRepaymentList() {
        HttpClientUtil.get(HttpRequestURL.getRepaymentList, null, new JDHttpResponseHandler(MessageCenterActivity.this, new ResponseHandler() {
            @Override
            public void onSuccess(Object o) {
                if (o != null) {
                    List<RepaymentInfo> repaymentInfoList = JSON.parseArray(o.toString(), RepaymentInfo.class);
                    if (repaymentInfoList != null && repaymentInfoList.size() > 0) {
                        mRepaymentList.addAll(repaymentInfoList);
                        messageAdapater.notifyDataSetChanged();
                    }
                }
            }
        }));
    }

    /**
     * BorrowRequestInfo  to  RepaymentInfo
     */
    private void transform(BorrowRequestInfo borrowRequestInfo) {
        RepaymentInfo repaymentInfo = new RepaymentInfo();
        repaymentInfo.setId(borrowRequestInfo.getBorrowRequestId());
        repaymentInfo.setName(borrowRequestInfo.getName());
        repaymentInfo.setPhone(borrowRequestInfo.getMobile());
        repaymentInfo.setBorrowMoney(borrowRequestInfo.getAmount());
        repaymentInfo.setBorrowDate(borrowRequestInfo.getLength());
        repaymentInfo.setBorrowPurpose(borrowRequestInfo.getBorrowPurpose());
        //0 新任务  1 催款
        repaymentInfo.setFlag(0);
        //办公地址
        mRepaymentList.add(0, repaymentInfo);
        messageAdapater.notifyDataSetChanged();
    }
}
