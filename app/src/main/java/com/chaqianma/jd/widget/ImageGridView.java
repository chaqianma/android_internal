package com.chaqianma.jd.widget;

import android.widget.GridView;

/**
 * Created by zhangxd on 2015/7/29.
 */
public class ImageGridView extends GridView {
    public ImageGridView(android.content.Context context,
                              android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
