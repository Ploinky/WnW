package de.jjl.wnw.desktop.gui;

import de.jjl.wnw.desktop.game.Game;

public abstract class Frame
{
	protected Game game;

	public Frame(Game game)
	{
		this.game = game;
	}
}
