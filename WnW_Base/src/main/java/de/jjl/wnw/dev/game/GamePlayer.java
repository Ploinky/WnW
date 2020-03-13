package de.jjl.wnw.dev.game;

import java.io.Serializable;

public class GamePlayer implements Player, Serializable
{
	private static final long serialVersionUID = -3838210747472502302L;

	private boolean faceLeft;

	private int height;

	private int lives;

	private int width;

	/** X-Position in the arena */
	private int x;

	/** Y-Position in the arena */
	private int y;

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

	public void damage(int damage)
	{
		if (damage > lives)
		{
			lives = 0;
			return;
		}

		lives -= damage;
	}

	@Override
	public void faceLeft()
	{
		faceLeft = true;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	@Override
	public int getLives()
	{
		return lives;
	}

	@Override
	public int getWidth()
	{
		return width;
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
	public boolean isFaceLeft()
	{
		return faceLeft;
	}

	@Override
	public void setLives(int lives)
	{
		this.lives = lives;
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
	public void update(float frameTime)
	{
	}
}
