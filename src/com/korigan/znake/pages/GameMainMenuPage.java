package com.korigan.znake.pages;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;

import com.korigan.znake.gesture.MenuGestureDetector;
import com.korigan.znake.gesture.MenuGestureListener;

public class GameMainMenuPage extends AbstractGamePage implements MenuGestureListener{
	
	private static final int PERIOD = 33;
	private boolean mClicked = false;
	private Paint mTextPaint;
	
	public GameMainMenuPage(Context context) {
		super(context);
		mTextPaint = new Paint();
		mTextPaint.setColor(Color.RED);
		mTextPaint.setTextSize(15);
	}

	@Override
	public void init() {
		mIsRunning = true;
		mGestureDetector = new MenuGestureDetector(this);
	}
	
	@Override
	public AbstractGamePage run(SurfaceView view) {
		long startTime, sleepTime;
		
		startTime = System.currentTimeMillis();
		while(!mClicked){
			
			if(!mIsRunning){
				return this;
			}
			
			if(view.getHolder().getSurface().isValid()){
			    Canvas canvas = view.getHolder().lockCanvas();
			    
			    //Background Color
				canvas.drawColor(Color.GREEN);
				
				canvas.drawText("Touch to play", 100, 100, mTextPaint);
				
				view.getHolder().unlockCanvasAndPost(canvas);
			}
			
			//This block causes the 'shaking' problem, although it isnt directly related to a negative sleepTime
			sleepTime = (int) (PERIOD - (System.currentTimeMillis() - startTime));
			Log.e("GameMainMenuPage", "Sleep time: "+sleepTime);
			if(sleepTime>0){
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else{
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Log.e("GamePlayingPage", "work too long: sleepTime="+sleepTime);
			}
			startTime = System.currentTimeMillis();
		}
		return new GamePlayingPage(mContext);
	}

	@Override
	public void onClick(float x, float y) {
		mClicked = true;
	}

	


}
