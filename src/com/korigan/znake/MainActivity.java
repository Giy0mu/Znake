package com.korigan.znake;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {
	
	private final static float VERSION = 1f;
	public static final String PREFS_NAME = "EDEN_SAVE";
	private DrawView mDrawView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Getting data from sharedpreferences
		GameSettings.save = getSharedPreferences(PREFS_NAME, 0);
		if(GameSettings.save.getFloat("version", -1f) != VERSION)
			eraseSave();
		GameSettings.best_score = GameSettings.save.getInt("best", 0);
		
		mDrawView = new DrawView(this);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(mDrawView);
	}
	
	private void eraseSave(){
		//TODO
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mDrawView.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mDrawView.resume();
	}
	
}
