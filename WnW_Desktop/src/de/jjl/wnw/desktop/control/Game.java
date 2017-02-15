package de.jjl.wnw.desktop.control;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.desktop.gui.GUI;

/**
 * This is class.
 * 
 * @author johannes.litger
 */
public class Game
{
	/** Used as display for the game */
	private GUI gui;

	public Game(GUI gui)
	{
		this.gui = gui;
	}

	/**
	 * Starts the game.
	 */
	public void start()
	{
		gui.setTitle(Const.TITLE);
		gui.show();
	}
}
