package com.chaqianma.jd.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.model.LocationInfo;
import com.chaqianma.jd.utils.LocationUtil;
import com.chaqianma.jd.utils.SharedPreferencesUtil;
import com.chaqianma.jd.widget.JDToast;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.chaqianma.jd.common.Constants;

@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup {
    @InjectView(R.id.tabhost)
    TabHost mTabHost;
    @InjectView(R.id.radiogroup)
    RadioGroup mRadioGroup;
    private Timer timer = new Timer();
    private boolean isBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_back);
        ButterKnife.inject(this);
        mTabHost.setup(getLocalActivityManager());
        addTabIntent();
        mTabHost.setCurrentTab(0);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_choumeibang:
                        mTabHost.setCurrentTab(0);
                        break;
                    case R.id.radio_hairscan:
                        mTabHost.setCurrentTab(1);
                        break;

                    default:
                        break;
                }
            }
        });
        LocationUtil locationUtil = new LocationUtil(getApplicationContext(), mHandler);
        locationUtil.run();
    }

    private void addTabIntent() {
        try {

        } catch (Exception e) {
        }
    }

    private TabHost.TabSpec buildTabSpec(String tag, String m,
                                         final Intent content) {
        return this.mTabHost.newTabSpec(tag).setIndicator(m).setContent(content);
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        if (isBack) {
            super.onBackPressed();
        } else {
            isBack = true;

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isBack = false;
                }
            }, 5 * 1000);
            JDToast.showShortText(MainActivity.this, "再按一次退出系统");
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