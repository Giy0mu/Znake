package com.korigan.znake.gameobjects;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.korigan.znake.GameSettings;

public class FieldLine implements GameObject{
	
	private Random r;
	private final int MIN_SPACE;
	private int mPosY;
	private final int mHeight;
	private final int mWidth;
	private int mLeftObstacle;
	private int mRightObstacle;
	
	private Paint mRockPaint;
	
	
	public FieldLine(int y){
		this(0,0, y);
	}
	
	public FieldLine(int left, int right, int y){
		r = new Random();
		
		
		mHeight = (int) (GameSettings.getInstance().screen_height/10f);
		mWidth = (int) (GameSettings.getInstance().screen_width);
		
		MIN_SPACE = mWidth/2;
		
		mPosY = y;
		mLeftObstacle = left;
		mRightObstacle = right;
		
		mRockPaint = new Paint();
		mRockPaint.setColor(Color.GRAY);
	}

	public void move(double d) {
		mPosY += d;
	}

	@Override
	public void draw(Canvas canvas) {
		//Draw left obstacle
		if(mLeftObstacle>0)
			canvas.drawRect(0,mPosY, mLeftObstacle, mPosY+mHeight,mRockPaint);
		//Draw right obstacle
		if(mRightObstacle>0)
			canvas.drawRect(mWidth-mRightObstacle,mPosY, mWidth, mPosY+mHeight,mRockPaint);
		
	}
	
	public boolean isOut(){
		return mPosY >= GameSettings.getInstance().screen_height;
	}
	
	public int getTotalSpace(){
		return mWidth-mLeftObstacle-mRightObstacle;
	}

	public FieldLine generateFieldLine(Snake snake){		
		int lineWidth = generateIntBetween(0, MIN_SPACE);
		int left = mLeftObstacle + generateIntBetween((int)(-snake.maxPositionDif(mHeight)), (int)(snake.maxPositionDif(mHeight)));
		
		return new FieldLine(left, mWidth-(left+(mWidth-lineWidth)), mPosY-mHeight);
	}
	
	private int generateIntBetween(int min, int max){
		int dif = max-min;
		return r.nextInt(dif)+min;
	}
}
