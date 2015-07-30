package com.chaqianma.jd.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.LocationManager;
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

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxd on 2015/7/24.
 */
public class SettingFragment extends BaseFragment {
    @InjectView(R.id.layout_msg_notify)
    RelativeLayout tv_msg_notify;
    @InjectView(R.id.layout_no_disturb)
    RelativeLayout tv_no_disturb;
    @InjectView(R.id.layout_update_password)
    RelativeLayout tv_update_password;
    @InjectView(R.id.btn_signout)
    Button btn_signout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_setting,container,false);
        ButterKnife.inject(this, view);
        setTitle("设置");
        tv_msg_notify.setOnClickListener(onClickListener);
        tv_no_disturb.setOnClickListener(onClickListener);
        tv_update_password.setOnClickListener(onClickListener);
        btn_signout.setOnClickListener(onClickListener);
        return view;
    }
  private View.OnClickListener onClickListener=new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          Intent intent=new Intent();
            switch (v.getId())
            {
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

                    break;
                default:break;
            }
      }
  };

    public static SettingFragment newInstance()
    {
        SettingFragment fragment=new SettingFragment();
        return fragment;
    }
}
