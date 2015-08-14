package com.chaqianma.jd.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.model.LocationInfo;
import com.chaqianma.jd.utils.LocationUtil;
import com.chaqianma.jd.utils.SharedPreferencesUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.chaqianma.jd.common.Constants;

@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup {
    @InjectView(R.id.tabhost)
    TabHost mTabHost;
    @InjectView(R.id.radiogroup)
    RadioGroup mRadioGroup;
    @InjectView(R.id.top_title)
    TextView top_title;
    @InjectView(R.id.top_back_btn)
    LinearLayout top_back_btn;
    private boolean mIsHasTask = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_back);
        ButterKnife.inject(this);
        mTabHost.setup(getLocalActivityManager());
        mIsHasTask = (AppData.getInstance().getBorrowRequestInfo() != null);
        addTabIntent();
        mTabHost.setCurrentTab(0);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_main:
                        top_title.setText("首页");
                        mTabHost.setCurrentTab(0);
                        break;
                    case R.id.radio_urge:
                        top_title.setText("催收");
                        mTabHost.setCurrentTab(1);
                        break;
                    case R.id.radio_message:
                        top_title.setText("消息");
                        mTabHost.setCurrentTab(2);
                        break;
                    case R.id.radio_setup:
                        top_title.setText("设置");
                        mTabHost.setCurrentTab(3);
                        break;
                    default:
                        break;
                }
            }
        });
        LocationUtil locationUtil = new LocationUtil(getApplicationContext(), mHandler);
        locationUtil.run();
        top_title.setText("首页");
        top_back_btn.setVisibility(View.GONE);
    }

    /*
    * 添加页面
    * */
    private void addTabIntent() {
        try {
            if (mIsHasTask)
                this.mTabHost.addTab(buildTabSpec("tab0", "0", new Intent(this, BorrowApplyActivity.class)));
            else
                this.mTabHost.addTab(buildTabSpec("tab0", "0", new Intent(this, StaffActivity.class)));
            this.mTabHost.addTab(buildTabSpec("tab1", "1", new Intent(this, RepaymentListActivity.class)));
            this.mTabHost.addTab(buildTabSpec("tab2", "2", new Intent(this, MessageCenterActivity.class)));
            this.mTabHost.addTab(buildTabSpec("tab3", "3", new Intent(this, SettingActivity.class)));
        } catch (Exception e) {
        }
    }

    private TabHost.TabSpec buildTabSpec(String tag, String m,
                                         final Intent content) {
        return this.mTabHost.newTabSpec(tag).setIndicator(m).setContent(content);
    }

    /*
    * 重新刷新View
    * */
    private void refreshView() {
        if (AppData.getInstance().getBorrowRequestInfo() != null) {

        } else {

        }
    }

    //Handler to get something
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.GETLOCATION:
                    LocationInfo locationInfo = AppData.getInstance().getLocationInfo();
                    SharedPreferencesUtil.setShareString(getApplicationContext(), Constants.CITYTAG, locationInfo.getCity());
                    break;
                default:
                    break;
            }
        }
    };
}
