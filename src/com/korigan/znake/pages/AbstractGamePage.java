package com.korigan.znake.pages;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.korigan.znake.gesture.AbstractGestureDetector;

abstract public class AbstractGamePage {
	protected Context mContext;
	protected AbstractGestureDetector mGestureDetector;
	protected boolean mIsRunning;	
	
	public AbstractGamePage(Context context){
		mContext = context;
	}
	
	public boolean onTouchEvent(MotionEvent event){
		return mGestureDetector.onTouchEvent(event);
	}
	
	public abstract void init();
	public abstract AbstractGamePage run(SurfaceView view);
	
	public void pause(){
		mIsRunning = false;
	}
	public void resume(){
		mIsRunning = true;
	}

}
