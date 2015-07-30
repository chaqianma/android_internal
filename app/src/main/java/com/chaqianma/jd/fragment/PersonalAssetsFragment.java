package com.chaqianma.jd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhangxd on 2015/7/28.
 */
public class PersonalAssetsFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static PersonalAssetsFragment newInstance()
    {
        PersonalAssetsFragment  personalAssetsFragment=new PersonalAssetsFragment();
        return personalAssetsFragment;
    }
}
