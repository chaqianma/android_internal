package com.chaqianma.jd.activity;

import android.os.Bundle;
import android.widget.Button;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.UserInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.widget.JDToast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangxd on 2015/7/29.
 */
public class StaffActivity extends BaseActivity {
    @InjectView(R.id.btn_state)
    Button btn_state;
    private Timer timer = new Timer();
    private boolean isBack = false;
    private boolean isBusy = false;
    private UserInfo userInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_staff_state);
        ButterKnife.inject(this);
        //是否忙碌着 1忙 0闲置
        userInfo = AppData.getInstance().getUserInfo();
        if (userInfo != null) {
            if (!JDAppUtil.isEmpty(userInfo.getIsBusy()))
                isBusy = userInfo.getIsBusy().equals("1");
            if (!isBusy)
                btn_state.setText("空闲");
            else
                btn_state.setText("忙碌");
        }
    }

    @OnClick(R.id.btn_state)
    void changeUserState() {
        try {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("isBusy", isBusy ? "0" : "1"));
            HttpClientUtil.put(StaffActivity.this, HttpRequestURL.changeStateUrl, formparams, new JDHttpResponseHandler(StaffActivity.this, new ResponseHandler() {
                @Override
                public void onSuccess(Object o) {
                    btn_state.setText(isBusy ? "空闲" : "忙碌");
                    userInfo.setIsBusy(isBusy ? "0" : "1");
                    isBusy = !isBusy;
                }
            }));
        } catch (Exception e) {
        }
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
            JDToast.showShortText(StaffActivity.this, "再按一次退出系统");
        }
    }
}
