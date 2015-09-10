package com.chaqianma.jd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.app.JDApplication;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.model.BorrowRequestInfo;
import com.chaqianma.jd.model.UserInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.LocationUtil;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.utils.SharedPreferencesUtil;
import com.chaqianma.jd.widget.JDAlertDialog;
import com.chaqianma.jd.widget.JDToast;
import com.loopj.android.http.SyncHttpClient;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangxd on 2015/7/15.
 * 登录
 */
public class LoginActivity extends BaseActivity {
    @InjectView(R.id.tv_username)
    TextView tv_username;
    @InjectView(R.id.tv_password)
    TextView tv_password;
    @InjectView(R.id.cb_remember)
    CheckBox cb_remember;
    private String mUUID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        //tv_username.setText("15651782303");
        //tv_password.setText("password");
        //tv_username.setText("18114803034");
        //tv_password.setText("1234567a");
        mUUID = JDAppUtil.getUniqueId(LoginActivity.this);
        setTopBarState("登录", false);
    }

    @OnClick(R.id.btn_submit)
    void onLogin() {
        final String username = tv_username.getText().toString().trim();
        final String password = tv_password.getText().toString().trim();
        if (username == null || username.length() <= 0) {
            JDToast.showLongText(LoginActivity.this, "用户名不能为空");
            return;
        } else {
            //手机号验证  只验证是否为数字 开头是1 并且11 位
            if (username.matches("^[0-9]*$") && username.length() == 11 && username.charAt(0) == '1') {

            } else {
                JDToast.showLongText(LoginActivity.this, "用户名只能是手机号");
                return;
            }
        }
        if (password == null || password.length() <= 0) {
            JDToast.showLongText(LoginActivity.this, "密码不能为空");
            return;
        }
        try {
            HashMap<String, Object> argMap = new HashMap<String, Object>();
            argMap.put("mobile", username);
            argMap.put("password", password);
            //argMap.put("userType", "1");
            argMap.put("uuid", mUUID);
            HttpClientUtil.post(HttpRequestURL.LoginUrl, argMap, new JDHttpResponseHandler(LoginActivity.this, new ResponseHandler<UserInfo>() {
                @Override
                public void onSuccess(UserInfo userInfo) {
                    if (userInfo != null) {
                        if (userInfo.getUserType().equals(Constants.USERTYPE)) {
                            AppData.getInstance().setUserInfo(userInfo);
                            SharedPreferencesUtil.setShareBoolean(LoginActivity.this, Constants.REMEMBERPASSWORD, cb_remember.isChecked());
                            SharedPreferencesUtil.setShareString(LoginActivity.this, Constants.USERNAME, username);
                            SharedPreferencesUtil.setShareString(LoginActivity.this, Constants.PASSWORD, password);
                            SharedPreferencesUtil.setShareString(LoginActivity.this, Constants.UUID, mUUID);
                            //设置别名
                            ((JDApplication) getApplication()).setAlias();
                            //再调用查看任务接口。。。不知为何要这么设计
                            getBorrowRequest();
                        } else {
                            JDAlertDialog.showAlertDialog(LoginActivity.this, "所登录用户为非进调人员", null);
                        }
                    }
                }
            }, Class.forName(UserInfo.class.getName()), true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //查看是否有任务
    private void getBorrowRequest() {
        try {
            HttpClientUtil.get(HttpRequestURL.loanApplyUrl, null, new JDHttpResponseHandler(LoginActivity.this, new ResponseHandler<BorrowRequestInfo>() {
                @Override
                public void onSuccess(BorrowRequestInfo borrowRequestInfo) {
                    if (borrowRequestInfo != null) {
                        String borrowRequestId = borrowRequestInfo.getBorrowRequestId();
                        if (borrowRequestId != null && borrowRequestId.length() > 0) {
                            AppData.getInstance().setBorrowRequestInfo(borrowRequestInfo);
                        }
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
            }, Class.forName(BorrowRequestInfo.class.getName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
