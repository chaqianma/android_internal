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
 * 催收数据源
 */
public class UrgeListAdapter extends BaseAdapter {

    private List<UrgeInfo> mUrgeInfoList;
    private Context mContext;

    public UrgeListAdapter(Context context, List<UrgeInfo> urgeInfoList) {
        this.mContext = context;
        this.mUrgeInfoList = urgeInfoList;
    }

    @Override
    public int getCount() {
        //if (mUrgeInfoList != null)
        //    return mUrgeInfoList.size();
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.urge_item, null);
            viewHolder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            viewHolder.urgeStatus = (TextView) convertView.findViewById(R.id.tv_urgeStatus);
            viewHolder.tv_borrowName = (TextView) convertView.findViewById(R.id.tv_borrowName);
            viewHolder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            viewHolder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            viewHolder.tv_repayment_date = (TextView) convertView.findViewById(R.id.tv_repayment_date);
            viewHolder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            viewHolder.tv_msg_date = (TextView) convertView.findViewById(R.id.tv_msg_date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //赋值
        if (position < mUrgeInfoList.size()) {
            UrgeInfo urgeInfo = mUrgeInfoList.get(position);
            viewHolder.tv_id.setText(urgeInfo.getId());
            viewHolder.urgeStatus.setText(urgeInfo.getUrgeStatus());
            viewHolder.tv_borrowName.setText(urgeInfo.getBorrowName());
            viewHolder.tv_money.setText(urgeInfo.getMoney());
            viewHolder.tv_phone.setText(urgeInfo.getPhone());
            viewHolder.tv_repayment_date.setText(urgeInfo.getRepaymentDate());
            viewHolder.tv_address.setText(urgeInfo.getAddress());
            viewHolder.tv_msg_date.setText(urgeInfo.getMsgDate());
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_id;
        TextView urgeStatus;
        TextView tv_borrowName;
        TextView tv_money;
        TextView tv_phone;
        TextView tv_repayment_date;
        TextView tv_address;
        TextView tv_msg_date;
    }
}
