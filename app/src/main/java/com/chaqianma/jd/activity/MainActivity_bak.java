package com.chaqianma.jd.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.fragment.BorrowApplyFragment;
import com.chaqianma.jd.fragment.BottomFragment;
import com.chaqianma.jd.fragment.MessageCenterFragment;
import com.chaqianma.jd.fragment.RepaymentListFragment;
import com.chaqianma.jd.fragment.SettingFragment;
import com.chaqianma.jd.fragment.StaffStateFragment;
import com.chaqianma.jd.widget.JDToast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhangxd on 2015/7/21.
 */
public class MainActivity_bak extends FragmentActivity implements BottomFragment.ICheckedCallback {
    FragmentTransaction fragmentTransaction = null;
    private SettingFragment settingFragment = null;
    private StaffStateFragment staffStateFragment = null;
    private BorrowApplyFragment borrowApplyFragment = null;
    private MessageCenterFragment messageCenterFragment = null;
    private RepaymentListFragment repaymentListFragment = null;
    //回退判断
    private Timer timer = new Timer();
    private boolean isBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initFragment();
        onItemSelected(R.id.radio_main);
    }

    @Override
    public void onItemSelected(int checkedId) {
        hideFragments();
        switch (checkedId) {
            case R.id.radio_main:
                if (AppData.getInstance().getBorrowRequestInfo() != null) {
                    if (borrowApplyFragment == null) {
                        borrowApplyFragment = BorrowApplyFragment.newInstance();
                        fragmentTransaction.add(R.id.main_content, borrowApplyFragment, Constants.BORROWAPPLY);
                    }
                    fragmentTransaction.show(borrowApplyFragment);
                } else {
                    if (staffStateFragment == null) {
                        staffStateFragment = StaffStateFragment.newInstance();
                        fragmentTransaction.add(R.id.main_content, staffStateFragment, Constants.STAFFSTATE);
                    }
                    fragmentTransaction.show(staffStateFragment);
                }
                break;
            case R.id.radio_repayment:
                if (repaymentListFragment == null) {
                    repaymentListFragment = RepaymentListFragment.newInstance();
                    fragmentTransaction.add(R.id.main_content, repaymentListFragment, Constants.REPAYMENT);
                }
                fragmentTransaction.show(repaymentListFragment);
                break;
            case R.id.radio_message:
                if (messageCenterFragment == null) {
                    messageCenterFragment = new MessageCenterFragment().newInstance();
                    fragmentTransaction.add(R.id.main_content, messageCenterFragment, Constants.MESSAGECENTER);
                }
                fragmentTransaction.show(messageCenterFragment);
                break;
            case R.id.radio_setup:
                if (settingFragment == null) {
                    settingFragment = SettingFragment.newInstance();
                    fragmentTransaction.add(R.id.main_content, settingFragment, Constants.SETTING);
                }
                fragmentTransaction.show(settingFragment);
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }


    public void setShowFragment(int idxTag) {
        switch (idxTag)
        {
            case 0:
                onItemSelected(R.id.radio_main);
                break;
            case 1:
                onItemSelected(R.id.radio_repayment);
                break;
            case 2:
                onItemSelected(R.id.radio_message);
                break;
            case 3:
                onItemSelected(R.id.radio_setup);
                break;
            default:break;
        }
    }

    private void hideFragments() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (staffStateFragment != null)
            fragmentTransaction.hide(staffStateFragment);

        if (borrowApplyFragment != null)
            fragmentTransaction.hide(borrowApplyFragment);

        if (repaymentListFragment != null)
            fragmentTransaction.hide(repaymentListFragment);

        if (settingFragment != null)
            fragmentTransaction.hide(settingFragment);

        if (messageCenterFragment != null)
            fragmentTransaction.hide(messageCenterFragment);
    }

    @Override
    public void onBackPressed() {
        if (isBack) {
            super.onBackPressed();
        } else {
            isBack = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isBack = false;
                }
            }, 5 * 1000);
            JDToast.showShortText(MainActivity_bak.this, "再按一次退出系统");
        }
    }
}
