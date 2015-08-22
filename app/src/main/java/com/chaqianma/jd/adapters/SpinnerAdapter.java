package com.chaqianma.jd.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangxd on 2015/8/21.
 */
public class SpinnerAdapter extends BaseAdapter {
    private List<HashMap<String, String>> mDataList = null;

    public SpinnerAdapter(List<HashMap<String, String>> dataList) {
        this.mDataList = dataList;
    }

    @Override
    public int getCount() {
        if (mDataList != null)
            return mDataList.size();
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
        return null;
    }
}
