package com.chaqianma.jd.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by zhangxd on 2015/8/11.
 * <p/>
 * 解决了ViewPager在ScrollView中的滑动反弹问题
 */
public class ScrollViewExtend extends ScrollView {
    public interface ScrollViewExtendListener {
        public void OnScrollViewReachEnd();
    }

    ;
    ScrollViewExtendListener mListener;

    public ScrollViewExtend(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void SetScrollViewExtendListener(ScrollViewExtendListener Listener) {
        mListener = Listener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
//        System.out.println(x + "/" + y);
//        System.out.println(computeVerticalScrollRange());
    }

    @SuppressLint("NewApi")
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (clampedY) {
//        	System.out.println("==========================================");
//            System.out.println(scrollX + "/" + scrollY + getHeight());
//            System.out.println(clampedX + "/" + clampedY);
//            System.out.println("==========================================");
            if (null == mListener) {
                return;
            }
            mListener.OnScrollViewReachEnd();
        }
    }
}
