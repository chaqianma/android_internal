package com.chaqianma.jd.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.chaqianma.jd.R;
import com.chaqianma.jd.widget.JDAlertDialog;
import com.chaqianma.jd.widget.JDToast;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangxd on 2015/7/27.
 */
public class SettingActivity extends BaseActivity {
    private Timer timer = new Timer();
    private boolean isBack = false;
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
        JDAlertDialog.showAlertDialog(SettingActivity.this, "确定退出系统吗？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.os.Process.killProcess(android.os.Process.myPid());   //获取PID
                System.exit(0);
            }
        });
    }

    @Override
    public void onBackPressed() {
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
            JDToast.showShortText(SettingActivity.this, "再按一次退出系统");
        }
    }
}
