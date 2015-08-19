package com.chaqianma.jd.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.chaqianma.jd.R;
import com.chaqianma.jd.model.RepaymentInfo;
import com.chaqianma.jd.utils.JDAppUtil;

import java.util.List;

/**
 * Created by zhangxd on 2015/8/12.
 * 催收数据源
 */
public class RepaymentListAdapter extends BaseAdapter {

    private List<RepaymentInfo> mRepaymentInfoList;
    private Context mContext;

    public RepaymentListAdapter(Context context, List<RepaymentInfo> repaymentInfoList) {
        this.mContext = context;
        this.mRepaymentInfoList = repaymentInfoList;
    }

    @Override
    public int getCount() {
        //if (mRepaymentInfoList != null)
        //    return mRepaymentInfoList.size();
        return 10;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.repayment_item, null);
            viewHolder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            viewHolder.tv_borrowName = (TextView) convertView.findViewById(R.id.tv_borrowName);
            viewHolder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            viewHolder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            viewHolder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            viewHolder.tv_repayment_date = (TextView) convertView.findViewById(R.id.tv_repayment_date);
            viewHolder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //赋值
        position = 0;
        if (position < mRepaymentInfoList.size()) {
            RepaymentInfo repaymentInfo = mRepaymentInfoList.get(position);
            viewHolder.tv_id.setText(repaymentInfo.getInvestmentNo());
            viewHolder.tv_borrowName.setText(repaymentInfo.getUserName());
            viewHolder.tv_status.setText(repaymentInfo.getDescStatus());
            viewHolder.tv_money.setText(repaymentInfo.getMoney());
            viewHolder.tv_phone.setText(repaymentInfo.getUserMobile());
            viewHolder.tv_repayment_date.setText(JDAppUtil.getStrDateTime(repaymentInfo.getRepaymentDateline()));
            viewHolder.tv_address.setText("");
            repaymentInfo.getStrWorkLocation(viewHolder.tv_address);
        }
        return convertView;
    }


    class ViewHolder {
        TextView tv_id;
        TextView tv_borrowName;
        TextView tv_status;
        TextView tv_money;
        TextView tv_phone;
        TextView tv_repayment_date;
        TextView tv_address;
    }
}
