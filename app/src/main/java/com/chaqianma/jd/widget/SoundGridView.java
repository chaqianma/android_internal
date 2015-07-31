package com.chaqianma.jd.widget;

import android.widget.GridView;

/**
 * Created by zhangxd on 2015/7/30.
 *
 * 录音gridview
 */
public class SoundGridView extends GridView {
    public SoundGridView(android.content.Context context,
                         android.util.AttributeSet attrs) {
        super(context,attrs);
    }

    /**
     * 去除滚动条
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
