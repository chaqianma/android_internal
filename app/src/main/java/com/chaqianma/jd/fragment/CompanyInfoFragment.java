package com.chaqianma.jd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaqianma.jd.R;

import butterknife.ButterKnife;

/**
 * Created by zhangxd on 2015/7/28.
 */
public class CompanyInfoFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_info, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    //初化始下拉框
    private void initSpinner()
    {

    }

    public static CompanyInfoFragment newInstance() {
        CompanyInfoFragment companyInfoFragment = new CompanyInfoFragment();
        return companyInfoFragment;
    }
}
