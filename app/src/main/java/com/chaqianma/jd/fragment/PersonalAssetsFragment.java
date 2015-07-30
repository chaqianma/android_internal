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
public class PersonalAssetsFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_asset, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    public static PersonalAssetsFragment newInstance()
    {
        PersonalAssetsFragment  personalAssetsFragment=new PersonalAssetsFragment();
        return personalAssetsFragment;
    }
}
