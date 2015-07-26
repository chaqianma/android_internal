package com.chaqianma.jd.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chaqianma.jd.R;
import com.chaqianma.jd.fragment.BaseFragment;
import butterknife.ButterKnife;
/**
 * Created by zhangxd on 2015/7/16.
 */
public class FrameOne extends BaseFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.temp_layout, container, false);
        ButterKnife.inject(this,view);
        ((TextView) view.findViewById(R.id.tv_temp)).setText("夏天");
        //setTitle("夏天One");
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden)
        {
            setTitle("夏天One");
        }
    }
}
