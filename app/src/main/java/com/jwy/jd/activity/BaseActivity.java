package com.jwy.jd.activity;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by zhangxd on 2015/7/17.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
