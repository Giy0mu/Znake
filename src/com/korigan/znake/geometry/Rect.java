package com.korigan.znake.geometry;

import android.graphics.PointF;

public class Rect extends Shape {
	
	private int mWidth;
	private int mHeight;
	
	public Rect(int width, int height){
		mWidth = width;
		mHeight = height;
	}

	@Override
	public boolean contains(int x, int y, PointF p) {
		if(p.x >= x && p.x <= x+mWidth
		&& p.y >= y && p.y<= y+mHeight)
			return true;
		return false;
	}
	
	public int getWidth(){
		return mWidth;
	}
	
	public int getHeight(){
		return mHeight;
	}
}
