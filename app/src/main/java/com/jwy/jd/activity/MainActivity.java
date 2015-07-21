package com.jwy.jd.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.jwy.jd.R;
import com.jwy.jd.utils.LocationUtil;
import com.jwy.jd.widget.JDToast;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

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
        TabWidget tabWidget = mTabHost.getTabWidget();
        tabWidget.setStripEnabled(false);
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
                    case R.id.radio_discover:
                        mTabHost.setCurrentTab(2);
                        break;
                    case R.id.radio_me:
                        mTabHost.setCurrentTab(3);
                        break;
                    default:
                        break;
                }
            }
        });
        LocationUtil locationUtil=new LocationUtil(getApplicationContext(),mHandler);
        locationUtil.run();
    }

    private void addTabIntent() {
        try {
            this.mTabHost.addTab(buildTabSpec("tab1", "0", new Intent(this, FrameOne.class)));
            this.mTabHost.addTab(buildTabSpec("tab2", "1", new Intent(this, FrameTwo.class)));
            this.mTabHost.addTab(buildTabSpec("tab3", "2", new Intent(this, FrameThree.class)));
            this.mTabHost.addTab(buildTabSpec("tab4", "3", new Intent(this, FrameFour.class)));
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
        }
        else {
            isBack=true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isBack =false;
                }
            }, 5 * 1000);
            JDToast.showShortText(MainActivity.this,"再按一次退出系统");
        }
    }

    private Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
}
