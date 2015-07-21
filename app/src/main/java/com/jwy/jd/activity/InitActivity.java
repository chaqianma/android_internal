package com.jwy.jd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jwy.jd.app.JDApplication;
import com.jwy.jd.common.AppData;
import com.jwy.jd.common.Constants;
import com.jwy.jd.common.HttpRequestURL;
import com.jwy.jd.model.UserInfo;
import com.jwy.jd.utils.HttpClientUtil;
import com.jwy.jd.utils.JDHttpResponseHandler;
import com.jwy.jd.utils.ResponseHandler;
import com.jwy.jd.utils.SharedPreferencesUtil;
import java.util.HashMap;

/**
 * Created by zhangxd on 2015/7/21.
 * <p/>
 * 引导页面，用于自动登录与启动动画
 */
public class InitActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String username = SharedPreferencesUtil.getShareString(InitActivity.this, Constants.USERNAME);
        String password = SharedPreferencesUtil.getShareString(InitActivity.this, Constants.PASSWORD);
        if (username.length() > 0 && password.length() > 0) {
            try {
                HashMap<String, Object> argMap = new HashMap<String, Object>();
                argMap.put("mobile", username);
                argMap.put("password", password);
                HttpClientUtil.post(HttpRequestURL.LoginUrl, argMap, new JDHttpResponseHandler(InitActivity.this, new ResponseHandler<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        if (userInfo != null) {
                            //理因为1
                            if (userInfo.getUserType().equals("0")) {
                                AppData.getInstance().setUserInfo(userInfo);
                                //设置别名
                                ((JDApplication) getApplication()).setAlias(userInfo.getMobile());
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent();
                                intent.setClass(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                            InitActivity.this.finish();
                        }
                    }
                }, Class.forName(UserInfo.class.getName()), true));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            InitActivity.this.finish();
        }
    }
}
