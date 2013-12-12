package com.korigan.znake.gameobjects.items;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.korigan.znake.gameobjects.Field;
import com.korigan.znake.geometry.Rect;

public class RockObstacle extends Obstacle{

	private final static int SIZE = 100;
	private Paint mRockPaint;
	
	public RockObstacle(Field field, int x) {
		super(field, x, -SIZE, new Rect(SIZE, SIZE));
		mRockPaint = new Paint();
		mRockPaint.setColor(Color.GRAY);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawRect(mPosX, mPosY, mPosX+SIZE, mPosY+SIZE, mRockPaint);

	}

	

}
