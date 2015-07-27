package com.chaqianma.jd.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.chaqianma.jd.R;

/**
 * Created by zhangxd on 2015/7/21.
 */
public class BottomFragment extends Fragment {

    RadioGroup radioGroup=null;
    public interface ICheckedCallback {
        void onItemSelected(int checkedId);
    }

    private ICheckedCallback callback = new ICheckedCallback() {
        @Override
        public void onItemSelected(int checkedId) {}
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof ICheckedCallback))
            throw new IllegalStateException(" must implements ICheckedCallback");
        callback = (ICheckedCallback) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.bottom, container, false);
         radioGroup=(RadioGroup)view.findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(changeListener);
        return view;
    }

    private RadioGroup.OnCheckedChangeListener changeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            callback.onItemSelected(checkedId);
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
