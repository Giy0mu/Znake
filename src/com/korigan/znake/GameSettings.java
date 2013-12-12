package com.korigan.znake;

import android.content.SharedPreferences;

public class GameSettings {
	
	static public int best_score;
	static public SharedPreferences save;
	
	private static GameSettings mThis;
	
	private GameSettings(){
		
	}
	
	static public GameSettings getInstance(){
		if(mThis == null){
			mThis = new GameSettings();
		}
		return mThis;
	}
	
	static public void saveNewScore(int newScore){
		SharedPreferences.Editor editor = save.edit();
		editor.putInt("best", newScore);
		editor.commit();
	}
	
	public float screen_width;
	public float screen_height;
}
