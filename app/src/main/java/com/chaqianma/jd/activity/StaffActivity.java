package com.chaqianma.jd.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.fragment.BaseFragment;
import com.chaqianma.jd.fragment.BorrowApplyFragment;
import com.chaqianma.jd.fragment.SettingFragment;
import com.chaqianma.jd.fragment.StaffStateFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangxd on 2015/7/27.
 */
public class StaffActivity extends FragmentActivity {
    private FragmentTransaction fragmentTransaction=null;
    public StaffStateFragment staffStateFragment=null;
    public BorrowApplyFragment borrowApplyFragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_state);
        initFragment();
        showFragment(staffStateFragment);
    }

    private void initFragment() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //尽调员状态
        staffStateFragment = StaffStateFragment.newInstance();
        fragmentTransaction.add(R.id.main_content, staffStateFragment, Constants.STAFFSTATE);
        //尽调申请
        borrowApplyFragment = BorrowApplyFragment.newInstance();
        fragmentTransaction.add(R.id.main_content, borrowApplyFragment, Constants.BORROWAPPLY);

        fragmentTransaction.commit();
    }

    //隐藏Fragment
    private void hideFragments() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (staffStateFragment != null)
            fragmentTransaction.hide(staffStateFragment);

        if (borrowApplyFragment != null)
            fragmentTransaction.hide(borrowApplyFragment);
    }

    //显示Fragment
    public void showFragment(BaseFragment fragment)
    {
        hideFragments();
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }
}
