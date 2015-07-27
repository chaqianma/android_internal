package com.chaqianma.jd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chaqianma.jd.R;
import com.chaqianma.jd.activity.StaffActivity;
import com.chaqianma.jd.common.Constants;

/**
 * Created by zhangxd on 2015/7/24.
 */
public class StaffStateFragment extends BaseFragment {

    private Button btn_state=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_staff_state,container,false);
        Button btn_state=(Button)view.findViewById(R.id.btn_state);
        //ButterKnife.inject(this,view);搞不懂为什么这个一用就报错，暂认为是在radiobutton里嵌套问题
        btn_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaffActivity staffActivity=((StaffActivity) getActivity());
                staffActivity.showFragment(staffActivity.borrowApplyFragment);
            }
        });
        return view;
    }



    public static StaffStateFragment newInstance()
    {
        StaffStateFragment staffStateFragment=new StaffStateFragment();
        return staffStateFragment;
    }
}
