package com.chaqianma.jd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.chaqianma.jd.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangxd on 2015/7/27.
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        //setTopBarState("设置", false);
    }

    @OnClick(R.id.layout_msg_notify)
    void gotoMsgNotify(View v)
    {
        switchPage(v);
    }

    @OnClick(R.id.layout_no_disturb)
    void gotoNoDisturb(View v)
    {
        switchPage(v);
    }

    @OnClick(R.id.layout_update_password)
    void gotoUpdatePassword(View v)
    {
        switchPage(v);
    }

    private void switchPage(View v)
    {
        Intent intent=new Intent();
        switch (v.getId()) {
            case R.id.layout_msg_notify:
                intent.setClass(SettingActivity.this,MsgnotifyActivity.class);
                break;
            case R.id.layout_no_disturb:
                intent.setClass(SettingActivity.this,NodisturbActivity.class);
                break;
            case R.id.layout_update_password:
                intent.setClass(SettingActivity.this,UpdatePasswordActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    @OnClick(R.id.btn_signout)
    void onSignout()
    {

    }
}
