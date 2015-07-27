package com.chaqianma.jd.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chaqianma.jd.R;
import com.chaqianma.jd.activity.MainActivity;
import com.chaqianma.jd.activity.MainActivity_bak;

/**
 * Created by zhangxd on 2015/7/21.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void setTitle(String title) {
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity != null) {
            View view = fragmentActivity.findViewById(R.id.top_title);
            if (view != null)
                ((TextView) view).setText(title);
        }
    }

    protected void setShowFragment(String fragmentTag,boolean isAddToBackStack)
    {
        FragmentActivity fragmentActivity = getActivity();
        if(fragmentActivity instanceof MainActivity_bak)
        {
            MainActivity_bak activity=(MainActivity_bak)fragmentActivity;
            activity.setShowFragment(fragmentTag,isAddToBackStack);
        }
    }
}
