package com.korigan.znake.gesture;

import android.view.MotionEvent;

public class MenuGestureDetector extends AbstractGestureDetector {
	
	private MenuGestureListener mListener;
	private boolean mDown = false;
	
	public MenuGestureDetector(MenuGestureListener listener){
		mListener = listener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				mDown = true;
				break;
			case MotionEvent.ACTION_UP:
				if(mDown){
					mListener.onClick(event.getX(), event.getY());
				}
				break;
		}
		return true;
	}
}
