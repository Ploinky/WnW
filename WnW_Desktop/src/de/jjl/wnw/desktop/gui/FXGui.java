package de.jjl.wnw.desktop.gui;

import de.jjl.wnw.desktop.game.Game;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FXGui implements GUI
{
	private Stage stage;

	public FXGui(Stage primaryStage)
	{
		stage = primaryStage;
		stage.getIcons().add(new Image(Game.class.getResourceAsStream("/de/jjl/wnw/base/res/1-freepik.jpg")));
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
	public void setScene(JFXFrame frame)
	{
		stage.setScene(new Scene(frame));
	}
}
