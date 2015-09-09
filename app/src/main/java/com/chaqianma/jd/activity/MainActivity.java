package com.chaqianma.jd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;

import com.chaqianma.jd.R;
import com.chaqianma.jd.app.JDApplication;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.fragment.BorrowApplyFragment;
import com.chaqianma.jd.fragment.BottomFragment;
import com.chaqianma.jd.fragment.MessageCenterFragment;
import com.chaqianma.jd.fragment.RepaymentListFragment;
import com.chaqianma.jd.fragment.SettingFragment;
import com.chaqianma.jd.fragment.StaffStateFragment;
import com.chaqianma.jd.model.BorrowRequestInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.LocationUtil;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.widget.JDToast;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxd on 2015/7/21.
 */
public class MainActivity extends FragmentActivity implements BottomFragment.ICheckedCallback {
    @InjectView(R.id.radio_main)
    RadioButton radio_main;
    @InjectView(R.id.radio_repayment)
    RadioButton radio_repayment;
    @InjectView(R.id.radio_message)
    RadioButton radio_message;
    @InjectView(R.id.radio_setup)
    RadioButton radio_setup;
    FragmentTransaction fragmentTransaction = null;
    private SettingFragment settingFragment = null;
    private StaffStateFragment staffStateFragment = null;
    private BorrowApplyFragment borrowApplyFragment = null;
    private MessageCenterFragment messageCenterFragment = null;
    private RepaymentListFragment repaymentListFragment = null;
    //回退判断
    private Timer timer = new Timer();
    private boolean isBack = false;
    private LocationUtil mLocationUtil=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        // initFragment();
        if (getIntent().hasExtra(Constants.REFRESH)) {
            getBorrowRequest();
        } else
            onItemSelected(R.id.radio_main);
        //上传位置
        mLocationUtil=new LocationUtil(MainActivity.this);
    }

    //查看是否有任务
    private void getBorrowRequest() {
        try {
            HttpClientUtil.get(HttpRequestURL.loanApplyUrl, null, new JDHttpResponseHandler(MainActivity.this, new ResponseHandler<BorrowRequestInfo>() {
                @Override
                public void onSuccess(BorrowRequestInfo borrowRequestInfo) {
                    if (borrowRequestInfo != null) {
                        String borrowRequestId = borrowRequestInfo.getBorrowRequestId();
                        if (borrowRequestId != null && borrowRequestId.length() > 0) {
                            AppData.getInstance().setBorrowRequestInfo(borrowRequestInfo);
                        }
                        onItemSelected(R.id.radio_main);
                    }
                }
            }, Class.forName(BorrowRequestInfo.class.getName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                        if(messageCenterFragment!=null)
                            messageCenterFragment.refreshData();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLocationUtil!=null)
            mLocationUtil.stopLocation();
    }

    public void setShowFragment(int idxTag) {
        switch (idxTag) {
            case 0:
                onItemSelected(R.id.radio_main);
                radio_main.setChecked(true);
                break;
            case 1:
                onItemSelected(R.id.radio_repayment);
                radio_repayment.setChecked(true);
                break;
            case 2:
                onItemSelected(R.id.radio_message);
                radio_message.setChecked(true);
                break;
            case 3:
                onItemSelected(R.id.radio_setup);
                radio_setup.setChecked(true);
                break;
            default:
                break;
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
            ((JDApplication) getApplication()).cancelAlias();
            super.onBackPressed();
        } else {
            isBack = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isBack = false;
                }
            }, 5 * 1000);
            JDToast.showShortText(MainActivity.this, "再按一次退出系统");
        }
    }
}
