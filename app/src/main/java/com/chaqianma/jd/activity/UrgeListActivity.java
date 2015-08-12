package com.chaqianma.jd.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.adapters.UrgeListAdapter;
import com.chaqianma.jd.model.UrgeInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxd on 2015/8/11.
 * 催收
 */
public class UrgeListActivity extends BaseActivity {
    @InjectView(R.id.list_urge)
    ListView list_urge;
    //数据源
    private UrgeListAdapter mUrgeListAdapter = null;
    private List<UrgeInfo> mUrgeInfoList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urge_list);
        ButterKnife.inject(this);
        initData();
    }

    /*
    * 初始化数据
    * */
    private void initData() {
        mUrgeInfoList = new ArrayList<UrgeInfo>();
        mUrgeListAdapter = new UrgeListAdapter(UrgeListActivity.this, mUrgeInfoList);
        list_urge.setAdapter(mUrgeListAdapter);
    }
}
