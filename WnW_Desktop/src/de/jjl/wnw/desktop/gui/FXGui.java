package de.jjl.wnw.desktop.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXGui implements GUI
{
	private Stage stage;

	public FXGui(Stage primaryStage)
	{
		stage = primaryStage;
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
