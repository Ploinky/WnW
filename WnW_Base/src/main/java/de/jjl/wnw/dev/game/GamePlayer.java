package de.jjl.wnw.dev.game;

public class GamePlayer implements Player
{
	private int x;
	
	private int y;
	
	private int width;
	
	private int height;
	
	private int lives;
	
	private boolean faceLeft;
	
	public GamePlayer(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		// TODO $Li 23.02.2019 This should be desktop and android specific?
		// Or should it be relative to game field size?
		this.width = 61;
		this.height = 100;
		
		lives = 5;
		faceLeft = false;
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
		throw new RuntimeException("Move is not implemented for player <" + this + ">!");
	}

	@Override
	public void faceLeft()
	{
		faceLeft = true;
	}

	@Override
	public int getLives()
	{
		return lives;
	}

	@Override
	public boolean isFaceLeft()
	{
		return faceLeft;
	}

	public void damage(int damage)
	{
		if(damage > lives)
		{
			lives = 0;
			return;
		}
		
		lives -= damage;
	}

}
