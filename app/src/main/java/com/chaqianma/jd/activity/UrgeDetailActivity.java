package com.chaqianma.jd.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.chaqianma.jd.R;

import butterknife.InjectView;

/**
 * Created by zhangxd on 2015/8/12.
 */
public class UrgeDetailActivity extends BaseActivity {

    @InjectView(R.id.tv_id)
    TextView tv_id;
    @InjectView(R.id.tv_urge_status)
    TextView tv_urge_status;
    @InjectView(R.id.tv_borrowName)
    TextView tv_borrowName;
    @InjectView(R.id.tv_phone)
    TextView tv_phone;
    @InjectView(R.id.tv_should_money)
    TextView tv_should_money;
    @InjectView(R.id.tv_should_date)
    TextView tv_should_date;
    @InjectView(R.id.tv_office_addr)
    TextView tv_office_addr;
    @InjectView(R.id.img_urge_count)
    TextView img_urge_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urge_detail);

    }
}
