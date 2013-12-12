package com.korigan.znake.gameobjects.items;

import com.korigan.znake.GameSettings;
import com.korigan.znake.GameEventListener;
import com.korigan.znake.gameobjects.Field;
import com.korigan.znake.gameobjects.GameObject;
import com.korigan.znake.geometry.Shape;

import android.graphics.Point;

abstract public class Item implements GameObject{
	
	protected Field mField;
	protected int mPosX;
	protected int mPosY;
	protected Shape mShape;
	
	public Item(Field field, int x, int y, Shape shape){
		mField = field;
		mPosX = x;
		mPosY = y;
		mShape = shape;
	}
	
	public void move(float speed){
		mPosY += speed;
	}
	
	public boolean isOut(){
		return mPosY >= GameSettings.getInstance().screen_height;
	}
	
	public boolean collide(Point p){
		return mShape.contains(mPosX, mPosY, p);
	}
	
	abstract public void onCollision(GameEventListener e);
}
