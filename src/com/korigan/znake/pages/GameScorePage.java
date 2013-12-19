package com.korigan.znake.pages;

import com.korigan.znake.GameSettings;
import com.korigan.znake.gesture.MenuGestureDetector;
import com.korigan.znake.gesture.MenuGestureListener;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameScorePage extends AbstractGamePage implements MenuGestureListener{

	private int mFinalScore;
	private boolean mClicked = false;
	private Paint mTextPaint;
	
	public GameScorePage(Context context, int final_score) {
		super(context);
		mFinalScore = final_score;
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
		
		
		boolean newBestScore = false;
		
		if(GameSettings.best_score < mFinalScore){
			newBestScore = true;
			GameSettings.saveNewScore(mFinalScore);
		}
		while(!mClicked){
			
			if(mIsRunning){
			
				if(view.getHolder().getSurface().isValid()){
				    Canvas canvas = view.getHolder().lockCanvas();
				    
				    //Background Color
					canvas.drawColor(Color.YELLOW);
					
					if(newBestScore){
						canvas.drawText("New record!\nYour score: "+mFinalScore, 100, 100, mTextPaint);
					}
					else{
						canvas.drawText("Your score: "+mFinalScore, 100, 100, mTextPaint);
					}
					view.getHolder().unlockCanvasAndPost(canvas);
				}
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) { 
					e.printStackTrace();
				}
			}
			else{
				return this;
			}
		}
		return new GameMainMenuPage(mContext);
	}

	@Override
	public void onClick(float x, float y) {
		mClicked = true;
	}

}
