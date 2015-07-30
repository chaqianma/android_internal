package com.chaqianma.jd.widget.wheel;

import com.chaqianma.jd.widget.WheelViewTime;

public interface OnWheelScrollListener {
	/**
	 * Callback method to be invoked when scrolling started.
	 * 
	 * @param wheel
	 *            the wheel view whose state has changed.
	 */
	void onScrollingStarted(WheelViewDate wheel);

	/**
	 * Callback method to be invoked when scrolling ended.
	 * 
	 * @param wheel
	 *            the wheel view whose state has changed.
	 */
	void onScrollingFinished(WheelViewDate wheel);
}
