package com.chaqianma.jd.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zhangxd on 2015/7/29.
 */
public class GalleryViewPager extends ViewPager {
    public GalleryViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        try {
            return super.onInterceptTouchEvent(arg0);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
