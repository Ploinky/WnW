package de.jjl.wnw.desktop.gui.menu;

import javafx.scene.Parent;
import javafx.scene.Scene;

public abstract class FXMenu extends Scene implements Menu
{

	public FXMenu(Parent parent)
	{
		super(parent);
		init();
	}

	private void init()
	{
	}
}
