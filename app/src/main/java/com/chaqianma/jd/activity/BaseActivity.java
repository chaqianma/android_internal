package com.chaqianma.jd.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.Constants;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by zhangxd on 2015/7/17.
 */
public class BaseActivity extends Activity implements View.OnClickListener {
    protected iBackPressedListener ibackPressedListener;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void setTopBarState(String title) {
        setTopBarState(title, false, false);
    }

    public void setTopBarState(String title, boolean isShowBackBtn) {
        setTopBarState(title, isShowBackBtn, false);
    }

    public void setTopBarState(String title, final boolean isShowBackBtn, boolean isShowRightBtn) {
        View tv_title = findViewById(R.id.top_title);
        if (tv_title != null)
            ((TextView) tv_title).setText(title);
        View top_back_btn = findViewById(R.id.top_back_btn);
        if (top_back_btn != null) {
            top_back_btn.setVisibility(isShowBackBtn ? View.VISIBLE : View.GONE);
            if (isShowBackBtn) {
                top_back_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseActivity.this.finish();
                    }
                });
            }
        }
        View top_right_btn = findViewById(R.id.top_right_btn);
        if (top_right_btn != null) {
            top_right_btn.setVisibility(isShowRightBtn ? View.VISIBLE : View.GONE);
        }
    }

    //启动Intent
    protected void startActivity(Context context, Class<?> dataType) {
        Intent intent = new Intent();
        intent.setClass(context, dataType);
        startActivity(intent);
    }

    protected void startActivity(Class<?> toClass)
    {
        startActivity(toClass, "");
    }

    protected  void startActivity(Class<?> toClass,String value)
    {
        Intent intent=new Intent();
        intent.setClass(BaseActivity.this, toClass);
        if(value!=null && value.length()>0)
        {
            intent.putExtra(Constants.TOVALUEKEY, value);
        }
        startActivity(intent);
    }

    protected  void startActivity(Class<?> toClass,Bundle bundle)
    {
        Intent intent=new Intent();
        intent.setClass(BaseActivity.this, toClass);
        if(bundle!=null)
        {
            intent.putExtra(Constants.TOVALUEKEY,bundle);
        }
        startActivity(intent);
    }

    protected interface iBackPressedListener {
        void onBackPressed();
    }

    @Override
    public void onClick(View v) {

    }
}
