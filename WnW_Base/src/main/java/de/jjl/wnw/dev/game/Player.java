package de.jjl.wnw.dev.game;

public interface Player extends GameObject
{
	public void faceLeft();

	public int getLives();
	
	public void setLives(int lives);

	public boolean isFaceLeft();

	public void setName(String name);
}
