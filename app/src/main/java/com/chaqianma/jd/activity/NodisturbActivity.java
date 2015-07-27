package com.chaqianma.jd.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.utils.SharedPreferencesUtil;
import com.chaqianma.jd.widget.SwitchButton;
import com.chaqianma.jd.widget.WheelView;
import com.chaqianma.jd.widget.WheelViewDialog;

import org.w3c.dom.Text;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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
    private WheelViewDialog mWheelViewDialog=null;
    private boolean isBegin=true;
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
        tv_begin_time.setText(SharedPreferencesUtil.getShareString(NodisturbActivity.this, Constants.NODISTURB_BEGINTIME));
        tv_end_time.setText(SharedPreferencesUtil.getShareString(NodisturbActivity.this, Constants.NODISTURB_ENDTIME));
    }

    @OnClick(R.id.layout_begin_time)
    void onBeginTimeClick() {
        isBegin=true;
        showDialog();
    }

    @OnClick(R.id.layout_end_time)
    void onEndTimeClick() {
        isBegin=false;
        showDialog();
    }

    //初始化Dialog
    private void showDialog()
    {
        if(mWheelViewDialog==null){
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
        SharedPreferencesUtil.setShareBoolean(NodisturbActivity.this, Constants.NODISTURB, switch_disturb.isChecked());
        SharedPreferencesUtil.setShareString(NodisturbActivity.this, Constants.NODISTURB_BEGINTIME, tv_begin_time.getText().toString());
        SharedPreferencesUtil.setShareString(NodisturbActivity.this, Constants.NODISTURB_ENDTIME, tv_begin_time.getText().toString());
    }
}
