package com.chaqianma.jd.activity;

import android.os.Bundle;
import android.widget.Button;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangxd on 2015/7/29.
 */
public class StaffActivity_bak extends BaseActivity {
    @InjectView(R.id.btn_state)
    Button btn_state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_staff_state);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.btn_state)
     void changeUserState() {
        btn_state.setText("忙碌");
        /*try {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("isBusy", "1"));
            HttpClientUtil.put(StaffActivity_bak.this, HttpRequestURL.changeStateUrl, formparams, new JDHttpResponseHandler(StaffActivity_bak.this, new ResponseHandler() {
                @Override
                public void onSuccess(Object o) {
                    btn_state.setText("忙碌");
                }
            }));
        } catch (Exception e) {
        }*/
    }
}
