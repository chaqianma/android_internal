package com.chaqianma.jd.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.model.UrgeInfo;

import java.util.List;

/**
 * Created by zhangxd on 2015/8/12.
 */
public class MessageAdapater extends BaseAdapter {

    private Context mContext;
    private List<UrgeInfo> mUrgeInfoList = null;

    public MessageAdapater(Context context, List<UrgeInfo> urgeInfoList) {
        this.mContext = context;
        this.mUrgeInfoList = urgeInfoList;
    }

    @Override
    public int getCount() {
        return 2;
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
        UrgeInfo urgeInfo = mUrgeInfoList.get(position);
        // 0 新任务  1 催款
        if (urgeInfo.getFlag() == 1) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_urge_item, null);
            TextView tv_urge_id = (TextView) convertView.findViewById(R.id.tv_urge_id);
            TextView tv_urge_date = (TextView) convertView.findViewById(R.id.tv_urge_date);
            TextView tv_urge_status = (TextView) convertView.findViewById(R.id.tv_urge_status);
            TextView tv_borrowName = (TextView) convertView.findViewById(R.id.tv_borrowName);
            TextView tv_urge_phone = (TextView) convertView.findViewById(R.id.tv_urge_phone);
            TextView tv_should_money = (TextView) convertView.findViewById(R.id.tv_should_money);
            TextView tv_should_date = (TextView) convertView.findViewById(R.id.tv_should_date);
            TextView tv_urgeAddr = (TextView) convertView.findViewById(R.id.tv_urgeAddr);

        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_task_item, null);
            TextView tv_task_date = (TextView) convertView.findViewById(R.id.tv_task_date);
            TextView tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            TextView tv_task_phone = (TextView) convertView.findViewById(R.id.tv_task_phone);
            TextView tv_borrowMoney = (TextView) convertView.findViewById(R.id.tv_borrowMoney);
            TextView tv_borrowDate = (TextView) convertView.findViewById(R.id.tv_borrowDate);
            TextView tv_borrowUse = (TextView) convertView.findViewById(R.id.tv_borrowUse);
            TextView tv_taskAddr = (TextView) convertView.findViewById(R.id.tv_taskAddr);
        }
        return convertView;
    }

    class ViewHolder {
        //任务标签
        TextView tv_task_date;
        TextView tv_id;
        TextView tv_name;
        TextView tv_task_phone;
        TextView tv_borrowMoney;
        TextView tv_borrowDate;//借款期限
        TextView tv_borrowUse;//借款用途
        TextView tv_taskAddr;//办公地址

        //催款
        TextView tv_urge_date;
        TextView tv_urge_id;
        TextView tv_urge_status;
        TextView tv_borrowName;
        TextView tv_urge_phone;
        TextView tv_should_money;
        TextView tv_should_date;
        TextView tv_urgeAddr;
    }
}
