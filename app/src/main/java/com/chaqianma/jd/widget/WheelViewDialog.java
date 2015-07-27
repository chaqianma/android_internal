package com.chaqianma.jd.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;

import com.chaqianma.jd.R;
import com.chaqianma.jd.adapters.NumericWheelAdapter;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxd on 2015/7/26
 * <p/>
 * 时间选择Dialog
 */
public class WheelViewDialog extends Dialog {
    private Context mContext = null;
    private WheelView hour_picker = null;
    private WheelView min_picker = null;
    private boolean timeScrolled = false;
    private boolean timeChanged = true;
    private IChangValueListener iChangValueListener;
    public WheelViewDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.time_picker);
        setCancelable(true);

        hour_picker = (WheelView) findViewById(R.id.hour_picker);
        hour_picker.setAdapter(new NumericWheelAdapter(0, 23));
        hour_picker.setLabel("时");
        hour_picker.setCyclic(true);

        min_picker = (WheelView) findViewById(R.id.min_picker);
        min_picker.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
        min_picker.setLabel("分");
        min_picker.setCyclic(true);
        Calendar c = Calendar.getInstance();
        int curHours = c.get(Calendar.HOUR_OF_DAY);
        int curMinutes = c.get(Calendar.MINUTE);
        hour_picker.setCurrentItem(curHours);
        min_picker.setCurrentItem(curMinutes);

        addChangingListener(min_picker);
        addChangingListener(hour_picker);

        addScrollListener(hour_picker);
        addScrollListener(min_picker);


        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.AniDialogStyle);
    }

    private void addChangingListener(final WheelView wheel) {
        wheel.addChangingListener(new WheelView.OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if(iChangValueListener!=null)
                    iChangValueListener.ChangingValue( String.format("%02d", hour_picker.getCurrentItem()) + ":" + String.format("%02d", min_picker.getCurrentItem()));
            }
        });
    }

    private void addChangingListener(final WheelView wheel, final String label) {
        wheel.addChangingListener(new WheelView.OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                wheel.setLabel(newValue != 1 ? label + "s" : label);
            }
        });
    }

    private void addScrollListener(final WheelView wheel) {
        wheel.addScrollingListener(new WheelView.OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                timeScrolled = true;
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                timeScrolled = false;
                timeChanged = true;
                timeChanged = false;
            }
        });
    }

    public void showDialog() {
        if (!isShowing()) {
            this.show();
        }
    }

    //设置接口
    public void setIChangValueListener(IChangValueListener iChangValueListener)
    {
        this.iChangValueListener=iChangValueListener;
    }

    //定义接口 ---用于获取选择的值
    public interface IChangValueListener {
        void ChangingValue(String time);

        void ChangedValue(String time);
    }
}
