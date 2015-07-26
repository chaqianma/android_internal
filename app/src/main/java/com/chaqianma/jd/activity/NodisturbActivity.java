package com.chaqianma.jd.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.widget.WheelViewDialog;

import org.w3c.dom.Text;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangxd on 2015/7/24.
 */
public class NodisturbActivity extends Activity {
    @InjectView(R.id.tv_begin_time)
    TextView tv_begin_time;
    @InjectView(R.id.tv_end_time)
    TextView tv_end_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_disturb);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.layout_begin_time)
    void onClick() {
       // showWHDialog();
        WheelViewDialog ddd=new WheelViewDialog(this);
        ddd.showDialog();
    }

    private void showWHDialog() {
        final Dialog dialog = new Dialog(this, R.style.WheelDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        View view = LayoutInflater.from(this).inflate(R.layout.time_picker, null, false);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.AniDialogStyle);
        if ((dialog != null) && (!dialog.isShowing())) {
            dialog.getWindow().setContentView(view);
            dialog.show();
        }
    }
}
