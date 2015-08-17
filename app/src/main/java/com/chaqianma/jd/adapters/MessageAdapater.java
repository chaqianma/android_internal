package com.chaqianma.jd.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.activity.RepaymentDetailActivity;
import com.chaqianma.jd.model.RepaymentInfo;
import com.chaqianma.jd.model.UrgeInfo;
import com.chaqianma.jd.utils.JDAppUtil;

import java.util.List;

/**
 * Created by zhangxd on 2015/8/12.
 */
public class MessageAdapater extends BaseAdapter {

    private Context mContext;
    private List<RepaymentInfo> mRepaymentInfoList = null;

    public MessageAdapater(Context context, List<RepaymentInfo> repaymentInfoList) {
        this.mContext = context;
        this.mRepaymentInfoList = repaymentInfoList;
    }

    @Override
    public int getCount() {
        if (mRepaymentInfoList != null)
            return mRepaymentInfoList.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //先不做复用
        ViewHolder viewHolder = null;
        RepaymentInfo repaymentInfo = mRepaymentInfoList.get(position);
        // 0 新任务  1 催款
        if (repaymentInfo.getFlag() == 1) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_repayment_item, null);
            TextView tv_come_date = (TextView) convertView.findViewById(R.id.tv_come_date);
            TextView tv_repayment_id = (TextView) convertView.findViewById(R.id.tv_repayment_id);
            TextView tv_repayment_status = (TextView) convertView.findViewById(R.id.tv_repayment_status);
            TextView tv_borrowName = (TextView) convertView.findViewById(R.id.tv_borrowName);
            TextView tv_borrow_phone = (TextView) convertView.findViewById(R.id.tv_borrow_phone);
            TextView tv_repayment_money = (TextView) convertView.findViewById(R.id.tv_repayment_money);
            TextView tv_repayment_date = (TextView) convertView.findViewById(R.id.tv_repayment_date);
            TextView tv_office_addr = (TextView) convertView.findViewById(R.id.tv_office_addr);
            TextView tv_repayment_Detail = (TextView) convertView.findViewById(R.id.tv_repayment_Detail);

            tv_come_date.setText("");
            tv_repayment_id.setText(repaymentInfo.getInvestmentNo());
            tv_repayment_status.setText(repaymentInfo.getDescStatus());
            tv_borrowName.setText(repaymentInfo.getUserName());
            tv_borrow_phone.setText(repaymentInfo.getUserMobile());
            tv_repayment_money.setText(repaymentInfo.getMoney());
            tv_repayment_date.setText(JDAppUtil.getStrDateTime(repaymentInfo.getRepaymentDateline()));
            tv_office_addr.setText(repaymentInfo.getUserWorkLocation());
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_task_item, null);
            TextView tv_task_date = (TextView) convertView.findViewById(R.id.tv_task_date);
            TextView tv_task_id = (TextView) convertView.findViewById(R.id.tv_task_id);
            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            TextView tv_borrowMoney = (TextView) convertView.findViewById(R.id.tv_borrowMoney);
            TextView tv_borrowDate = (TextView) convertView.findViewById(R.id.tv_borrowDate);
            TextView tv_borrowUse = (TextView) convertView.findViewById(R.id.tv_borrowUse);
            TextView tv_task_addr = (TextView) convertView.findViewById(R.id.tv_task_addr);
            TextView tv_taskDetail = (TextView) convertView.findViewById(R.id.tv_taskDetail);

            tv_task_date.setText("");
            tv_task_id.setText(repaymentInfo.getId());
            tv_name.setText(repaymentInfo.getName());
            tv_phone.setText(repaymentInfo.getPhone());
            tv_borrowMoney.setText(repaymentInfo.getBorrowMoney());
            tv_borrowDate.setText(repaymentInfo.getBorrowDate());
            tv_borrowUse.setText(repaymentInfo.getBorrowPurpose());
            tv_task_addr.setText("");
        }
        return convertView;
    }

    class ViewHolder {
        //任务标签

        TextView tv_task_id;
        TextView tv_task_date;
        TextView tv_name;
        TextView tv_phone;
        TextView tv_borrowMoney;
        TextView tv_borrowDate;
        TextView tv_borrowUse;
        TextView tv_task_addr;
        TextView tv_taskDetail;

        //催款
        TextView tv_come_date;
        TextView tv_repayment_date;
        TextView tv_repayment_id;
        TextView tv_repayment_status;
        TextView tv_borrowName;
        TextView tv_borrow_phone;
        TextView tv_repayment_money;
        TextView tv_office_addr;
        TextView tv_repayment_Detail;
    }
}
