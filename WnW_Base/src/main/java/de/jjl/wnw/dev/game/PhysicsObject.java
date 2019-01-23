package de.jjl.wnw.dev.game;

public interface PhysicsObject
{
	public int getX();

	public int getY();
	
	public void setX(int x);
	
	public void setY(int y);

	public int getWidth();

	public int getHeight();
	
	public void move(float frameTime);
}
