package com.chaqianma.jd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.UserInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDAppUtil;
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
 * Created by zhangxd on 2015/7/24.
 */
public class StaffStateFragment extends BaseFragment {
    @InjectView(R.id.btn_state)
    Button btn_state;
    private boolean isBusy = false;
    private UserInfo userInfo = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_status, container, false);
        ButterKnife.inject(this, view);
        setTitle("首页", false);
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
        return view;
    }

    @OnClick(R.id.btn_state)
    void changeUserState() {
        try {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("isBusy", isBusy ? "0" : "1"));
            HttpClientUtil.put(getActivity(), HttpRequestURL.changeStateUrl, formparams, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
                @Override
                public void onSuccess(Object o) {
                    btn_state.setText(isBusy ? "空闲" : "忙碌");
                    userInfo.setIsBusy(isBusy ? "0" : "1");
                    isBusy = !isBusy;
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setTitle("首页", false);
        }
    }

    public static StaffStateFragment newInstance() {
        StaffStateFragment staffStateFragment = new StaffStateFragment();
        return staffStateFragment;
    }
}
