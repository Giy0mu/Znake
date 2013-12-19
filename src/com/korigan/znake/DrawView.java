package com.korigan.znake;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import com.korigan.znake.pages.AbstractGamePage;
import com.korigan.znake.pages.GameMainMenuPage;

/**
 * This is were stuff begin
 * @author Guillaume
 *
 */
public class DrawView extends SurfaceView implements Runnable{

	private Context mContext;
	
	Thread mThread = null;
	SurfaceHolder mHolder;
	boolean mRunning = false;
	boolean mInit = false;
	boolean mSurfaceReady = false;
	
	private AbstractGamePage mGamePage;
	
	public DrawView(Context context) {
		super(context);	
		mContext = context;
		
		mHolder = getHolder();
		mHolder.addCallback(new Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				mSurfaceReady = false;
				while(retry){
					try{
						mThread.join();
						retry = false;
					} catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				if(!mInit){
					initGameSettings();
					mGamePage = new GameMainMenuPage(mContext);
				}
				mSurfaceReady = true;
				start();
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				Log.e("DrawView", "Surface Changed!");
			}
		});
		

		setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return mGamePage.onTouchEvent(event);
			}
		});
	}

	/**
	 * Instantiates resources (TODO)
	 * then
	 * Run indefinitely, alternating between pages
	 */
	@Override
	public void run() {
		while(mRunning){
				
			if(mSurfaceReady){
				mGamePage.init();
				mGamePage = mGamePage.run(this);
			}
			else{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
        }
	}
	
	private void initGameSettings(){
		GameSettings.getInstance().screen_height = getHeight();
		GameSettings.getInstance().screen_width = getWidth();
	}
	
	public void pause(){
		mGamePage.pause();
		mRunning = false;
    }

	public void resume(){
		if(mInit){
			mGamePage.resume();
			mRunning = true;
		}
	}
	
	public void start(){
		mRunning = true;
		mInit = true;
		mThread = new Thread(this);
		mThread.start();
	}

}
