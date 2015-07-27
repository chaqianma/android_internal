package com.chaqianma.jd.fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chaqianma.jd.R;

/**
 * Created by zhangxd on 2015/7/24.
 */
public class BaiduMapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_baidu_map,container,false);
        return view;
    }
}
