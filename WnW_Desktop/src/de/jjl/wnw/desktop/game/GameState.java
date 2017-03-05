package de.jjl.wnw.desktop.game;

public class GameState
{
	private GameState instance;

	private GameState()
	{

	}

	public GameState get()
	{
		if (instance == null)
		{
			instance = new GameState();
		}

		return instance;
	}
}
