package com.korigan.znake.gameobjects;

import java.util.LinkedList;
import java.util.Random;
import com.korigan.znake.GameSettings;
import com.korigan.znake.GameEventListener;
import com.korigan.znake.gameobjects.items.AppleItem;
import com.korigan.znake.gameobjects.items.Item;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.SystemClock;

public class Field implements GameObject{

	private final Random r;
	private LinkedList<FieldLine> mFieldLineQueue;
	private LinkedList<Item> mItemQueue;
	private LinkedList<Item> mItemToDestroy;
	private long mLastGenerateTime;
	
	public Field(){
		mFieldLineQueue = new LinkedList<FieldLine>();
		
		int startY = (int) (-2*(GameSettings.getInstance().screen_height/10f));
		int heightLine = (int) (GameSettings.getInstance().screen_height/10f);
		for(int i=0; i<12;i++){
			mFieldLineQueue.add(new FieldLine(startY));
			mFieldLineQueue.getLast().move((12-i)*heightLine);
		}
		mItemQueue = new LinkedList<Item>();
		mItemToDestroy = new LinkedList<Item>();
		mLastGenerateTime = SystemClock.elapsedRealtime();
		r = new Random();
	}
	
	private void generateItem(){
		if(SystemClock.elapsedRealtime() - mLastGenerateTime > 3000){
			mLastGenerateTime = SystemClock.elapsedRealtime();
			mItemQueue.add(new AppleItem(this, r.nextInt((int) GameSettings.getInstance().screen_width)));
		}
	}
	
	public void move(Snake snake) {
		generateItem();
		
		for(FieldLine fl: mFieldLineQueue){
			fl.move(snake.getSpeed());
		}
		if(mFieldLineQueue.size()>0){
			while(mFieldLineQueue.getFirst().isOut()){
				mFieldLineQueue.removeFirst();
				mFieldLineQueue.add(mFieldLineQueue.getLast().generateFieldLine(snake));
				
			}
		}
		
		for(Item i: mItemQueue){
			i.move(snake.getSpeed());
		}
		if(mItemQueue.size()>0){
			while(mItemQueue.getFirst().isOut()){
				mItemQueue.removeFirst();
				if(mItemQueue.size()<=0)
					break;
			}
		}
	}

	@Override
	public void draw(Canvas canvas) {
		for(FieldLine fl: mFieldLineQueue){
			fl.draw(canvas);
		}
		for(Item i: mItemQueue){
			i.draw(canvas);
		}
		
	}
	
	public void checkCollisions(Point snakePoint, GameEventListener gameEvent){
		//Check collision with edge
		if(snakePoint.x < 0 || snakePoint.x > GameSettings.getInstance().screen_width){
			gameEvent.gameOver();
			return;
		}
		
		//Check collision with items & obstacles
		for(Item i: mItemQueue){
			if(i.collide(snakePoint)){
				i.onCollision(gameEvent);
			}
		}
		mItemQueue.removeAll(mItemToDestroy);
		mItemToDestroy.clear();
	}
	
	public void destroyItem(Item i){
		mItemToDestroy.add(i);
	}

}
