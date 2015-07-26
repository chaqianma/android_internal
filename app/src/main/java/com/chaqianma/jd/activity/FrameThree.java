package com.chaqianma.jd.activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaqianma.jd.R;
import com.chaqianma.jd.fragment.BaseFragment;


/**
 * Created by zhangxd on 2015/7/16.
 */
public class FrameThree extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setTitle("夏天Three");
        return inflater.inflate(R.layout.temp_layout,container,false);
    }
}
