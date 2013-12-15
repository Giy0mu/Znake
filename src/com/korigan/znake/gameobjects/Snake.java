package com.korigan.znake.gameobjects;

import java.util.LinkedList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import com.korigan.znake.GameSettings;
import com.korigan.znake.gesture.SnakeGestureListener.SNAKE_ORIENTATION;

public class Snake implements GameObject{
	
	private final int MAX_DOT_NUMBER = 25;
	private final int ORIENTATION_MAX = 90;
	private final float mOrientationSpeed;
	private PointF mHitbox;
	
	private final Paint mOrientationPaint;
	private final Paint mHitboxPaint;
	
	private float mPosX;
	private final float mPosY;
	private float mSpeed;
	
	
	private LinkedList<PointF> mSnakePoints;
	private float mOrientation;
	
	public Snake(){
		mPosX = GameSettings.getInstance().screen_width/2;
		mPosY = (GameSettings.getInstance().screen_height*80f)/100f;
			
		mOrientation = 0;
		mOrientationSpeed = 5;
		
		mOrientationPaint = new Paint();
		mOrientationPaint.setColor(Color.RED);
		
		mHitboxPaint = new Paint();
		mHitboxPaint.setColor(Color.GREEN);
		mHitboxPaint.setStrokeWidth(20f);
		
		mSpeed = 200f;
		mSnakePoints = new LinkedList<PointF>();
	}
	
	public void incOrientation(SNAKE_ORIENTATION orientation){
		if(orientation == SNAKE_ORIENTATION.RIGHT){
			mOrientation-= mOrientationSpeed;
			if(mOrientation<-ORIENTATION_MAX)
				mOrientation = -ORIENTATION_MAX;
		}
		else if(orientation == SNAKE_ORIENTATION.LEFT){
			mOrientation += mOrientationSpeed;
			if(mOrientation>ORIENTATION_MAX)
				mOrientation = ORIENTATION_MAX;
		}
	}
	
	public float getSpeed(){
		return mSpeed;
	}
	
	public float maxPositionDif(int height){
		 float ret = (float) (mSpeed*Math.sin(ORIENTATION_MAX*(2*Math.PI)/360));
		 return ret;
	}
	
	public void neutralizeOrientation(){
		if(mOrientation != 0){
			if(mOrientation<0){
				if(mOrientation > -mOrientationSpeed){
					mOrientation = 0;
				}
				else{
					mOrientation+=mOrientationSpeed;
				}
			}
			else if(mOrientation>0){
				if(mOrientation < mOrientationSpeed){
					mOrientation = 0;
				}
				else{
					mOrientation-=mOrientationSpeed;
				}
			}
		}
	}
	
	/**
	 * Calculate the X position of the snake
	 */
	public void move(double elapsed){
		mPosX = (float) (mPosX-(1.5*mSpeed*elapsed*Math.sin(mOrientation*(2*Math.PI)/360)));
		for(PointF p: mSnakePoints){
			p.y += mSpeed*elapsed;
		}
	}
	
	public void draw(Canvas canvas){
		drawOrientationLine(canvas);
		mHitbox = new PointF((int)mPosX, (int)mPosY);
		canvas.drawPoint(mHitbox.x, mHitbox.y, mHitboxPaint);
		
		for(PointF p : mSnakePoints){
			canvas.drawPoint(p.x, p.y, mHitboxPaint);
		}
		
		mSnakePoints.addLast(mHitbox);
		if(mSnakePoints.size()>=MAX_DOT_NUMBER){
			mSnakePoints.removeFirst();
		}
		
	}
	
	private void drawOrientationLine(Canvas canvas){
		int tongueLength = 50;
		canvas.drawLine(mPosX, mPosY, (float)(mPosX-tongueLength*Math.sin(mOrientation*(2*Math.PI)/360)), (float)(mPosY-tongueLength*Math.cos(mOrientation*(2*Math.PI)/360)), mOrientationPaint);
	}
	
	public PointF getHitbox(){
		return new PointF((int)mPosX, (int)mPosY);
	}
}
