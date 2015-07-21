package com.jwy.jd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jwy.jd.R;
import com.jwy.jd.app.JDApplication;
import com.jwy.jd.common.AppData;
import com.jwy.jd.common.Constants;
import com.jwy.jd.model.UserInfo;
import com.jwy.jd.utils.HttpClientUtil;
import com.jwy.jd.common.HttpRequestURL;
import com.jwy.jd.utils.JDHttpResponseHandler;
import com.jwy.jd.utils.LocationUtil;
import com.jwy.jd.utils.ResponseHandler;
import com.jwy.jd.utils.SharedPreferencesUtil;
import com.jwy.jd.widget.JDAlertDialog;
import com.jwy.jd.widget.JDToast;

import java.util.HashMap;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by zhangxd on 2015/7/15.
 * 登陆
 */
public class LoginActivity extends Activity {
    @InjectView(R.id.tv_username)
    TextView tv_username;
    @InjectView(R.id.tv_password)
    TextView tv_password;
    @InjectView(R.id.cb_remember)
    CheckBox cb_remember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.inject(this);
        //tv_username.setText("13913999601");
        //tv_password.setText("password");

    }

    @OnClick(R.id.btn_submit)
    void onLogin() {
        final String username = tv_username.getText().toString().trim();
        final String password = tv_password.getText().toString().trim();
        if (username == null || username.length() <= 0) {
            JDToast.showLongText(LoginActivity.this, "用户名不能为空");
            return;
        }
        if (password == null || password.length() <= 0) {
            JDToast.showLongText(LoginActivity.this, "密码不能为空");
            return;
        }
        try {
            HashMap<String, Object> argMap = new HashMap<String, Object>();
            argMap.put("mobile", username);
            argMap.put("password", password);
            HttpClientUtil.post(HttpRequestURL.LoginUrl, argMap, new JDHttpResponseHandler(LoginActivity.this, new ResponseHandler<UserInfo>() {
                @Override
                public void onSuccess(UserInfo userInfo) {
                    if (userInfo != null) {
                        //理因为1
                        if (userInfo.getUserType().equals("0")) {
                            AppData.getInstance().setUserInfo(userInfo);
                            if (cb_remember.isChecked()) {
                                SharedPreferencesUtil.setShareString(LoginActivity.this, Constants.USERNAME, username);
                                SharedPreferencesUtil.setShareString(LoginActivity.this, Constants.PASSWORD, password);
                            }
                            //设置别名
                            ((JDApplication)getApplication()).setAlias(userInfo.getMobile());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            LoginActivity.this.finish();
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
}
