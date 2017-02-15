package de.jjl.wnw.desktop.start;

import de.jjl.wnw.desktop.control.Game;
import de.jjl.wnw.desktop.gui.GUI;
import de.jjl.wnw.desktop.gui.def.DefaultGUI;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Starts the actual game.
 *
 * @author johannes.litger
 */
public class Starter extends Application
{
	public static void main(String[] args)
	{
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		GUI gui = new DefaultGUI(primaryStage);
		Game game = new Game(gui);
		game.start();
	}

}
