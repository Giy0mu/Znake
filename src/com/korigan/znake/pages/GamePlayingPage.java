package com.korigan.znake.pages;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;

import com.korigan.znake.GameEventListener;
import com.korigan.znake.gameobjects.Field;
import com.korigan.znake.gameobjects.Snake;
import com.korigan.znake.gesture.SnakeGestureDetector;
import com.korigan.znake.gesture.SnakeGestureListener;

public class GamePlayingPage extends AbstractGamePage implements GameEventListener, SnakeGestureListener{

	private static final int PERIOD = 17;
	private Paint mScorePaint;
	private int mScore;
	
	//Control
	private static Object touchLock = new Object();
	private boolean mTouching = false;
	private SNAKE_ORIENTATION mLastOrientation;
	private Snake mSnake;
	private Field mField;
	
	//States
	private boolean mGameOver = false;
	private boolean mGamePause = false;
	
	public GamePlayingPage(Context context){
		super(context);
		mScore = 0;
		mScorePaint = new Paint();
		mScorePaint.setColor(Color.BLACK);
		mScorePaint.setTextSize(50);
		
		//Instantiates game objects
		mSnake = new Snake();
		mField = new Field();
	}
	
	@Override
	public void init(){
		mIsRunning = true;
		mGestureDetector = new SnakeGestureDetector(this);
	}
	
	public AbstractGamePage run(SurfaceView view){
		AbstractGamePage returnPage = this;
		long startTime, sleepTime;
		long lastUpdate;
		double elapsed;
		
		//Running
		startTime = System.currentTimeMillis();
		lastUpdate = startTime;
		Canvas canvas = null;
		
		while(mIsRunning){
			executeInput();
			elapsed = (System.currentTimeMillis() - lastUpdate)/1000d;
			updatePosition(elapsed);
			lastUpdate = System.currentTimeMillis();
			checkCollisions();
			
			if(mGameOver){
				returnPage = new GameScorePage(mContext, mScore);
				break;
			}
			
			if(view.getHolder().getSurface().isValid()){
	        	try{
				    canvas = view.getHolder().lockCanvas();
				    synchronized(view.getHolder()){
					    //Background Color
						canvas.drawColor(Color.BLUE);
						
						//Field & Items
						mField.draw(canvas);
						
						//Player	
						mSnake.draw(canvas);
						
						//Score
						canvas.drawText("Score: "+String.valueOf(mScore), 50, 50, mScorePaint); 
				    }
	        	}
	        	catch(Exception e){
	        		e.printStackTrace();
	        	}
	        	finally{
	        		if(canvas != null)
	        			view.getHolder().unlockCanvasAndPost(canvas);
	        	}
			}
			
			sleepTime = (int) (PERIOD - (System.currentTimeMillis() - startTime));
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
		return returnPage;
	}
	
	private void executeInput(){
		synchronized(touchLock){
			if(mTouching){
				mSnake.incOrientation(mLastOrientation);
			}
			else{
				mSnake.neutralizeOrientation();
			}
		}
	}
	
	private void checkCollisions(){
		//A collision can only happens between the snake and an item
		mField.checkCollisions(mSnake.getHitbox(), this);
	}
	
	private void updatePosition(double elapsed){
		mSnake.move(elapsed);
		mField.move(elapsed, mSnake);
	}

	@Override
	public void onRotateGesture(boolean on, SNAKE_ORIENTATION orientation) {
		mTouching = on;
		mLastOrientation = orientation;
		
	}

	@Override
	public void gameOver() {
		mGameOver = true;
	}

	@Override
	public void gamePause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void scoreBonus(int bonus) {
		mScore += bonus;
		
	}
}
