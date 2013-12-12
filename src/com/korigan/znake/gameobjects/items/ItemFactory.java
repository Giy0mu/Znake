package com.korigan.znake.gameobjects.items;

import java.util.Random;

public class ItemFactory {
	
	private Random r;
	
	public ItemFactory(){
		r = new Random();
	}
	
	/**
	 * Returns a pseudo-randomly generated item
	 * Each item as a chance to appear depending on its rarity
	 */
	public Item getNewItem(){
		int score = r.nextInt(100);
		if(score>0){
//			return new AppleItem()
		}			
		return null;
		
	}
}
