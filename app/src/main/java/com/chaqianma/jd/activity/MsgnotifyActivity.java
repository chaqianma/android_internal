package com.chaqianma.jd.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import com.chaqianma.jd.R;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JPushUtil;
import com.chaqianma.jd.utils.SharedPreferencesUtil;
import com.chaqianma.jd.widget.JDToast;
import com.chaqianma.jd.widget.SwitchButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangxd on 2015/7/24.
 * 推送消息提醒
 */
public class MsgnotifyActivity extends BaseActivity {
    @InjectView(R.id.switch_notify)
    SwitchButton switch_notify;
    @InjectView(R.id.switch_sound)
    SwitchButton switch_sound;
    @InjectView(R.id.switch_shake)
    SwitchButton switch_shake;
    @InjectView(R.id.layout_notify)
    LinearLayout layout_notify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msgnotify);
        ButterKnife.inject(this);
        setTopBarState("消息提醒", true, true);
        initData();
        switch_notify.setOnCheckedChangeListener(onCheckedChangeListener);
        switch_sound.setOnCheckedChangeListener(onCheckedChangeListener);
        switch_shake.setOnCheckedChangeListener(onCheckedChangeListener);
    }


    /*
    * 设置默认值
    * */
    private void initData() {
        boolean isOpen = SharedPreferencesUtil.getShareBoolean(MsgnotifyActivity.this, Constants.MSGNOTIFY, true);
        switch_notify.setChecked(isOpen);
        if (isOpen) {
            layout_notify.setVisibility(View.VISIBLE);
            boolean notifyWay = SharedPreferencesUtil.getShareString(MsgnotifyActivity.this, Constants.MSGTOAST).equals(Constants.MSGSHAKE);
            if (notifyWay) {
                switch_sound.setChecked(false);
                switch_shake.setChecked(true);
            } else {
                switch_sound.setChecked(true);
                switch_shake.setChecked(false);
            }
        } else {
            switch_sound.setChecked(false);
            switch_shake.setChecked(false);
            layout_notify.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.top_right_btn)
    void onSubmit() {
        SharedPreferencesUtil.setShareBoolean(MsgnotifyActivity.this, Constants.MSGNOTIFY, switch_notify.isChecked());
        //只有开启才保存数据
        if (switch_notify.isChecked()) {
            SharedPreferencesUtil.setShareString(MsgnotifyActivity.this, Constants.MSGTOAST, switch_shake.isChecked() ? Constants.MSGSHAKE : Constants.MSGSOUND);
            JPushUtil.setPushNotificationBuilder(MsgnotifyActivity.this);
            //SharedPreferencesUtil.setShareBoolean(MsgnotifyActivity.this, Constants.MSGSOUND, switch_sound.isChecked());
            //SharedPreferencesUtil.setShareBoolean(MsgnotifyActivity.this, Constants.MSGSHAKE, switch_shake.isChecked());
        } else {
            //设置无任务提醒
           // JPushUtil.setSilenceTime(MsgnotifyActivity.this, 0, 59, 23, 59);
            JPushUtil.clearPushNotificationBuilder(MsgnotifyActivity.this);
        }
        JDToast.showLongText(MsgnotifyActivity.this, "消息提醒设置成功");
        finish();
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            CompoundButton switchView = buttonView;
            switch (switchView.getId()) {
                case R.id.switch_notify:
                    if (isChecked)
                        JDAppUtil.addShowAction(layout_notify);
                    else
                        JDAppUtil.addHiddenAction(layout_notify);
                    //SharedPreferencesUtil.setShareBoolean(MsgnotifyActivity.this, Constants.MSGNOTIFY, isChecked);
                    break;
                case R.id.switch_sound:
                    //SharedPreferencesUtil.setShareBoolean(MsgnotifyActivity.this, Constants.MSGSOUND, isChecked);
                    switch_shake.setChecked(!isChecked);
                    break;
                case R.id.switch_shake:
                    //SharedPreferencesUtil.setShareBoolean(MsgnotifyActivity.this, Constants.MSGSHAKE, isChecked);
                    switch_sound.setChecked(!isChecked);
                    break;
                default:
                    break;
            }
        }
    };
}
