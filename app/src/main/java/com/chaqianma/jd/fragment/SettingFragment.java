package com.chaqianma.jd.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.chaqianma.jd.R;
import com.chaqianma.jd.activity.MsgnotifyActivity;
import com.chaqianma.jd.activity.NodisturbActivity;
import com.chaqianma.jd.activity.UpdatePasswordActivity;
import com.chaqianma.jd.app.JDApplication;
import com.chaqianma.jd.widget.JDAlertDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxd on 2015/7/24.
 */
public class SettingFragment extends BaseFragment {
    @InjectView(R.id.layout_msg_notify)
    RelativeLayout layout_msg_notify;
    @InjectView(R.id.layout_no_disturb)
    RelativeLayout layout_no_disturb;
    @InjectView(R.id.layout_update_password)
    RelativeLayout layout_update_password;
    @InjectView(R.id.btn_signout)
    Button btn_signout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, container, false);
        ButterKnife.inject(this, view);
        setTitle("设置", false);
        layout_msg_notify.setOnClickListener(onClickListener);
        layout_no_disturb.setOnClickListener(onClickListener);
        layout_update_password.setOnClickListener(onClickListener);
        btn_signout.setOnClickListener(onClickListener);
        return view;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.layout_msg_notify:
                    intent.setClass(getActivity(), MsgnotifyActivity.class);
                    startActivity(intent);
                    break;
                case R.id.layout_no_disturb:
                    intent.setClass(getActivity(), NodisturbActivity.class);
                    startActivity(intent);
                    break;
                case R.id.layout_update_password:
                    intent.setClass(getActivity(), UpdatePasswordActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_signout:
                    JDAlertDialog.showAlertDialog(getActivity(), "确定退出系统吗？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((JDApplication) getActivity().getApplication()).cancelAlias();
                            android.os.Process.killProcess(android.os.Process.myPid());   //获取PID
                            System.exit(0);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            dialog.cancel();
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            setTitle("设置", false);
        }
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }
}
