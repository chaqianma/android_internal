package com.chaqianma.jd.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chaqianma.jd.R;

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

    public void setTitle(Context context,String title)
    {
        View view=((BaseActivity)context).findViewById(R.id.top_title);
        if(view!=null)
        {
            ((TextView)view).setText(title);
        }
    }
}
