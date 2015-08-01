package com.chaqianma.jd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;

import butterknife.ButterKnife;

/**
 * Created by zhangxd on 2015/7/28.
 */
public class SocialRelationFragment extends BaseFragment {
    private String mBorrowRequestId = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social_relation, container, false);
        ButterKnife.inject(this, view);
        getSocialRelationInfo();
        return view;
    }

    private void getSocialRelationInfo() {
        if (AppData.getInstance().getBorrowRequestInfo() != null)
            mBorrowRequestId = AppData.getInstance().getBorrowRequestInfo().getBorrowRequestId();
        mBorrowRequestId = "23";
        String requestPath = HttpRequestURL.personalInfoUrl + "/" + Constants.CONTACTINFO + "/" + mBorrowRequestId;
        try {
            HttpClientUtil.get(requestPath, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
                @Override
                public void onSuccess(Object o) {
                    String sss = "23423423423";
                    sss = "23423423423";
                    sss = "23423423423";
                }
            }));
        } catch (Exception e) {
        }

    }

    public static SocialRelationFragment newInstance() {
        SocialRelationFragment socialRelationFragment = new SocialRelationFragment();
        return socialRelationFragment;
    }
}