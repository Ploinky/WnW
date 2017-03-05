package de.jjl.wnw.desktop.game;

import de.jjl.wnw.desktop.gui.FXGui;
import de.jjl.wnw.desktop.gui.GUI;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is class.
 *
 * @author johannes.litger
 */
public class Game extends Application
{
	private GUI gui;

	public static void main(String[] args)
	{
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		gui = new FXGui(primaryStage);
	}

}
