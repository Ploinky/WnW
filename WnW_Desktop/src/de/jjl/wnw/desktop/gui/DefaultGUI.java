package de.jjl.wnw.desktop.gui;

import de.jjl.wnw.desktop.gui.menu.DefaultMainMenu;
import javafx.stage.Stage;

/**
 * Default implementation of the {@code GUI}, written in JavaFX.
 *
 * @author johannes.litger
 */
public class DefaultGUI implements GUI
{
	private Stage stage;

	public DefaultGUI(Stage stage)
	{
		this.stage = stage;
		init();
	}

	private void init()
	{
		goToMain();
	}

	@Override
	public void show()
	{
		stage.show();
	}

	@Override
	public void hide()
	{
		stage.hide();
	}

	@Override
	public void setTitle(String title)
	{
		stage.setTitle(title);
	}

	@Override
	public void goToMain()
	{
		stage.setScene(new DefaultMainMenu());
	}
}
