package com.jwy.jd.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.jwy.jd.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxd on 2015/7/16.
 */
public class FrameTwo extends Activity {
    @InjectView(R.id.tv_temp)
    TextView tv_temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_layout);
        ButterKnife.inject(this);
        tv_temp.setText("2");
    }
}
