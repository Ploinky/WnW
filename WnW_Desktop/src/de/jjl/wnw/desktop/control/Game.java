package de.jjl.wnw.desktop.control;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.desktop.gui.*;
import de.jjl.wnw.desktop.gui.def.*;

/**
 * This is class.
 * 
 * @author johannes.litger
 */
public class Game implements GuiListener
{
	/** Used as display for the game */
	private GUI gui;

	public Game(GUI gui)
	{
		this.gui = gui;
		this.gui.addListener(this);
	}

	/**
	 * Starts the game.
	 */
	public void start()
	{
		gui.setTitle(Const.TITLE);
		gui.setScene(new DefaultMainMenu());
		gui.show();
	}

	@Override
	public void requestHost()
	{
		gui.setScene(new DefaultHostMenu());
	}

	@Override
	public void requestMain()
	{
		gui.setScene(new DefaultMainMenu());
	}
}
