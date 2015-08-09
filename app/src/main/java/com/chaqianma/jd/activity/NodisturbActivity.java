package com.chaqianma.jd.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.utils.SharedPreferencesUtil;
import com.chaqianma.jd.widget.JDToast;
import com.chaqianma.jd.widget.SwitchButton;
import com.chaqianma.jd.widget.WheelViewDialog;

import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by zhangxd on 2015/7/24.
 */
public class NodisturbActivity extends BaseActivity {
    @InjectView(R.id.tv_begin_time)
    TextView tv_begin_time;
    @InjectView(R.id.tv_end_time)
    TextView tv_end_time;
    @InjectView(R.id.switch_disturb)
    SwitchButton switch_disturb;
    private WheelViewDialog mWheelViewDialog = null;
    private boolean isBegin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_disturb);
        ButterKnife.inject(this);
        setTopBarState("勿扰模式", true, true);
        initData();
    }

    //初始化数据
    private void initData() {
        switch_disturb.setChecked(SharedPreferencesUtil.getShareBoolean(NodisturbActivity.this, Constants.NODISTURB));
        String begin_time = SharedPreferencesUtil.getShareString(NodisturbActivity.this, Constants.NODISTURB_BEGINTIME);
        String end_time = SharedPreferencesUtil.getShareString(NodisturbActivity.this, Constants.NODISTURB_ENDTIME);
        if (begin_time != null && begin_time.length() > 0)
            tv_begin_time.setText(begin_time);
        if (end_time != null && end_time.length() > 0)
            tv_end_time.setText(end_time);
    }

    @OnClick(R.id.layout_begin_time)
    void onBeginTimeClick() {
        isBegin = true;
        showDialog();
    }

    @OnClick(R.id.layout_end_time)
    void onEndTimeClick() {
        isBegin = false;
        showDialog();
    }

    //初始化Dialog
    private void showDialog() {
        if (mWheelViewDialog == null) {
            mWheelViewDialog = new WheelViewDialog(this);
            mWheelViewDialog.setIChangValueListener(new WheelViewDialog.IChangValueListener() {
                @Override
                public void ChangingValue(String time) {
                    if (isBegin)
                        tv_begin_time.setText(time);
                    else
                        tv_end_time.setText(time);
                }

                @Override
                public void ChangedValue(String time) {
                }
            });
        }
        mWheelViewDialog.showDialog();
    }

    //提交 本地保存数据
    @OnClick(R.id.top_right_btn)
    void onSubmit() {
        try {
            if (switch_disturb.isChecked()) {
                String begin_time = tv_begin_time.getText().toString();
                int beginHour = 0, endHour = 0;
                if (begin_time != null && begin_time.indexOf(":") > -1) {
                    beginHour = Integer.parseInt(begin_time.split(":")[0]);
                }
                String end_time = tv_begin_time.getText().toString();
                if (end_time != null && end_time.indexOf(":") > -1) {
                    endHour = Integer.parseInt(end_time.split(":")[0]);
                }
                if (beginHour > endHour) {
                    JDToast.showLongText(NodisturbActivity.this, "开始时间不能大于结束时间");
                    return;
                }
                /*
                * Set days 0表示星期天，1表示星期一，以此类推。 （7天制，Set集合里面的int范围为0到6）

                Sdk1.2.9 – 新功能:set的值为null,则任何时间都可以收到消息和通知，set的size为0，则表示任何时间都收不到消息和通知.

                int startHour 允许推送的开始时间 （24小时制：startHour的范围为0到23）
                int endHour 允许推送的结束时间 （24小时制：endHour的范围为0到23）
                * */
                Set<Integer> days = new HashSet<Integer>();
                days.add(0);
                days.add(1);
                days.add(2);
                days.add(3);
                days.add(4);
                days.add(5);
                days.add(6);
                JPushInterface.setPushTime(getApplicationContext(), days, beginHour, endHour);
            }
            SharedPreferencesUtil.setShareBoolean(NodisturbActivity.this, Constants.NODISTURB, switch_disturb.isChecked());
            SharedPreferencesUtil.setShareString(NodisturbActivity.this, Constants.NODISTURB_BEGINTIME, tv_begin_time.getText().toString());
            SharedPreferencesUtil.setShareString(NodisturbActivity.this, Constants.NODISTURB_ENDTIME, tv_begin_time.getText().toString());
        } catch (Exception e) {

        }
    }
}
