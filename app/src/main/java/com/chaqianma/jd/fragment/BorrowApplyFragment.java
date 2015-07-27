package com.chaqianma.jd.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.widget.JDToast;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxd on 2015/7/24.
 */
public class BorrowApplyFragment extends BaseFragment {
    @InjectView(R.id.tv_id)
    TextView tv_id;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_telephone)
    TextView tv_telephone;
    @InjectView(R.id.img_telephone)
    ImageView img_telephone;
    @InjectView(R.id.tv_money)
    TextView tv_money;
    @InjectView(R.id.tv_date)
    TextView tv_date;
    @InjectView(R.id.tv_address)
    TextView tv_address;
    @InjectView(R.id.tv_apply_time)
    TextView tv_apply_time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_borrow_apply, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    private void initData() {
        HttpClientUtil.get(HttpRequestURL.loanApplyUrl, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler<String>() {
            @Override
            public void onSuccess(String buffer) {

            }
        }));
    }

    public static BorrowApplyFragment newInstance() {
        BorrowApplyFragment borrowApplyFragment = new BorrowApplyFragment();
        return borrowApplyFragment;
    }
}
