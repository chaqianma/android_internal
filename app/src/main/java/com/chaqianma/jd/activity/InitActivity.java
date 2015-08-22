package com.chaqianma.jd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.chaqianma.jd.app.JDApplication;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.UserInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.utils.SharedPreferencesUtil;
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
        String uuid=SharedPreferencesUtil.getShareString(InitActivity.this,Constants.UUID);
        username="";
        if (username.length() > 0 && password.length() > 0) {
            try {
                HashMap<String, Object> argMap = new HashMap<String, Object>();
                argMap.put("mobile", "15651782303");
                argMap.put("password", "password");
                //argMap.put("userType","1");
                argMap.put("uuid", uuid);
                HttpClientUtil.post(HttpRequestURL.LoginUrl, argMap, new JDHttpResponseHandler(InitActivity.this, new ResponseHandler<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        if (userInfo != null) {
                            if (userInfo.getUserType().equals("1")) {
                                AppData.getInstance().setUserInfo(userInfo);
                                //设置别名
                                ((JDApplication) getApplication()).setAlias();
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

                    @Override
                    public void onFailure(String data) {
                        super.onFailure(data);
                        SharedPreferencesUtil.setShareString(InitActivity.this, Constants.USERNAME, "");
                        SharedPreferencesUtil.setShareString(InitActivity.this, Constants.PASSWORD,"");
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
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
