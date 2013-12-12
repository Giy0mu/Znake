package com.korigan.znake.gameobjects.items;

import com.korigan.znake.GameEventListener;
import com.korigan.znake.gameobjects.Field;
import com.korigan.znake.geometry.Shape;

public abstract class Obstacle extends Item{

	public Obstacle(Field field, int x, int y, Shape shape) {
		super(field, x, y, shape);
	}

	@Override
	public void onCollision(GameEventListener e) {
		e.gameOver();
	}

}
