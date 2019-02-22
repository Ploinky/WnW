package de.jjl.wnw.dev.game;

public class GamePlayer implements Player
{
	private int x;
	
	private int y;
	
	private int width;
	
	private int height;
	
	public GamePlayer(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}

	@Override
	public void setX(int x)
	{
		this.x = x;
	}

	@Override
	public void setY(int y)
	{
		this.y = y;
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	@Override
	public void move(float frameTime)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void faceLeft()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLives()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFaceLeft()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void damage(int damage)
	{
		// TODO Auto-generated method stub
		
	}

}
