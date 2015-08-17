package com.chaqianma.jd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.chaqianma.jd.R;
import com.chaqianma.jd.activity.MainActivity;
import com.chaqianma.jd.activity.RepaymentDetailActivity;
import com.chaqianma.jd.adapters.MessageAdapater;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.BorrowRequestInfo;
import com.chaqianma.jd.model.RepaymentInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxd on 2015/8/17.
 */
public class MessageCenterFragment extends BaseFragment {
    @InjectView(R.id.list_msgs)
    ListView list_msgs;
    private List<RepaymentInfo> mRepaymentList = null;
    private MessageAdapater messageAdapater = null;
    private boolean isGotoMainPage = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_message_center, container, false);
        ButterKnife.inject(this, view);
        initData();
        setTitle("消息中心", false);
        return view;
    }

    /*
   * 初始化数据
   * */
    private void initData() {
        mRepaymentList = new ArrayList<RepaymentInfo>();
        messageAdapater = new MessageAdapater(getActivity(), mRepaymentList);
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
                        if (AppData.getInstance().getBorrowRequestInfo() != null) {
                            ((MainActivity) (getActivity())).setShowFragment(0);
                        } else {
                            isGotoMainPage = true;
                            getBorrowRequest();
                        }
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
                HttpClientUtil.get(HttpRequestURL.loanApplyUrl, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler<BorrowRequestInfo>() {
                    @Override
                    public void onSuccess(BorrowRequestInfo borrowRequestInfo) {
                        if (borrowRequestInfo != null) {
                            if (!isGotoMainPage)
                                transform(borrowRequestInfo);
                            else {
                                isGotoMainPage = false;
                                ((MainActivity) (getActivity())).setShowFragment(0);
                            }
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
        HashMap<String, Object> argMaps = new HashMap<String, Object>();
        argMaps.put("idList", "1,2,3");//SharedPreferencesUtil.getRepaymentId(.this);
        HttpClientUtil.get(HttpRequestURL.getNotifyRepaymentList, argMaps, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setTitle("消息中心", false);
        }
    }

    public static MessageCenterFragment newInstance() {
        MessageCenterFragment messageCenterFragment = new MessageCenterFragment();
        return messageCenterFragment;
    }
}
