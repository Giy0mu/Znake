package com.korigan.znake;

public interface GameEventListener {
	public void gameOver();
	public void gamePause();
	public void scoreBonus(int bonus);
}
