package de.jjl.wnw.dev.game;

public interface PhysicsObject
{
	public int getX();

	public int getY();

	public int getWidth();

	public int getHeight();
	
	public void move(float frameTime);
}
