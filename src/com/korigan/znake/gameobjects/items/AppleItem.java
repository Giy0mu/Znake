package com.korigan.znake.gameobjects.items;

import com.korigan.znake.GameEventListener;
import com.korigan.znake.gameobjects.Field;
import com.korigan.znake.geometry.Rect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class AppleItem extends Item{
	
	private final static int SIZE = 50;
	private Paint mApplePaint;
	
	public AppleItem(Field field, int x) {
		super(field, x, -SIZE, new Rect(SIZE, SIZE));
		mApplePaint = new Paint();
		mApplePaint.setColor(Color.RED);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(mPosX+SIZE/2, mPosY+SIZE/2, SIZE/2, mApplePaint);
	}

	@Override
	public void onCollision(GameEventListener e) {
		e.scoreBonus(10);
		mField.destroyItem(this);
	}
	
	

	

}
