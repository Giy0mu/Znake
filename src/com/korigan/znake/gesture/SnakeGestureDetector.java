package com.korigan.znake.gesture;

import com.korigan.znake.GameSettings;
import android.view.MotionEvent;

public class SnakeGestureDetector extends AbstractGestureDetector{
	
	private SnakeGestureListener mListener;
	private float mLastXTouch;
	
	public SnakeGestureDetector(SnakeGestureListener listener){
		mListener = listener;		
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				mLastXTouch = event.getX();
				if(mLastXTouch < GameSettings.getInstance().screen_width/2){
					mListener.onRotateGesture(true, SnakeGestureListener.SNAKE_ORIENTATION.LEFT);
				}
				else{
					mListener.onRotateGesture(true, SnakeGestureListener.SNAKE_ORIENTATION.RIGHT);
				}
				return true;
			case MotionEvent.ACTION_POINTER_DOWN:
				//Always the last touch that orientate the snake
				mListener.onRotateGesture(false, SnakeGestureListener.SNAKE_ORIENTATION.LEFT);
				mLastXTouch = event.getX(event.getActionIndex());
				if(mLastXTouch < GameSettings.getInstance().screen_width/2){
					mListener.onRotateGesture(true, SnakeGestureListener.SNAKE_ORIENTATION.LEFT);
				}
				else{
					mListener.onRotateGesture(true, SnakeGestureListener.SNAKE_ORIENTATION.RIGHT);
				}
				return true;
			case MotionEvent.ACTION_UP:
				if(mLastXTouch < GameSettings.getInstance().screen_width/2){
					mListener.onRotateGesture(false, SnakeGestureListener.SNAKE_ORIENTATION.LEFT);
				}
				else{
					mListener.onRotateGesture(false, SnakeGestureListener.SNAKE_ORIENTATION.RIGHT);
				}
				return true;
//			case MotionEvent.ACTION_POINTER_UP:
//				return true;
			
		}
		return true;
	}
}
