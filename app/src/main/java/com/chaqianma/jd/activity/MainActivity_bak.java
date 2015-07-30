package com.chaqianma.jd.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.fragment.BorrowApplyFragment;
import com.chaqianma.jd.fragment.BottomFragment;
import com.chaqianma.jd.fragment.SettingFragment;
import com.chaqianma.jd.fragment.StaffStateFragment;

/**
 * Created by zhangxd on 2015/7/21.
 */
public class MainActivity_bak extends FragmentActivity implements BottomFragment.ICheckedCallback {
    FragmentTransaction fragmentTransaction = null;
    private SettingFragment settingFragment = null;
    private StaffStateFragment staffStateFragment = null;
    private BorrowApplyFragment borrowApplyFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        onItemSelected(R.id.radio_msg);
    }

    private void initFragment() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //尽调员状态
        staffStateFragment = StaffStateFragment.newInstance();
        fragmentTransaction.add(R.id.main_content, staffStateFragment, Constants.STAFFSTATE);
        //设置页面
        settingFragment = SettingFragment.newInstance();
        fragmentTransaction.add(R.id.main_content, settingFragment, Constants.SETTING);

        //尽调申请
        borrowApplyFragment = BorrowApplyFragment.newInstance();
        fragmentTransaction.add(R.id.main_content, borrowApplyFragment, Constants.BORROWAPPLY);

        fragmentTransaction.commit();
    }

    @Override
    public void onItemSelected(int checkedId) {
        hideFragments();
        switch (checkedId) {
            case R.id.radio_msg:
                fragmentTransaction.show(staffStateFragment);
                break;
            case R.id.radio_setup:
                fragmentTransaction.show(settingFragment);
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    public void setShowFragment(String fragmentTag, boolean isAddToBackStack) {
        hideFragments();
        fragmentTransaction.show(getSupportFragmentManager().findFragmentByTag(fragmentTag));
        if (isAddToBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void hideFragments() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (staffStateFragment != null)
            fragmentTransaction.hide(staffStateFragment);

        if (settingFragment != null)
            fragmentTransaction.hide(settingFragment);

        if (borrowApplyFragment != null)
            fragmentTransaction.hide(borrowApplyFragment);
    }
}
