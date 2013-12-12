package com.korigan.znake.gesture;

public interface SnakeGestureListener{
	
	public static enum SNAKE_ORIENTATION{
		LEFT,
		RIGHT
	};	
	
	public void onRotateGesture(boolean on, SNAKE_ORIENTATION orientation);
}
