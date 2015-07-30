package com.chaqianma.jd.widget.wheel;

import com.chaqianma.jd.widget.WheelViewTime;

public interface OnWheelClickedListener {
	/**
	 * Callback method to be invoked when current item clicked
	 * 
	 * @param wheel
	 *            the wheel view
	 * @param itemIndex
	 *            the index of clicked item
	 */
	void onItemClicked(WheelViewDate wheel, int itemIndex);
}
