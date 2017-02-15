package de.jjl.wnw.desktop.gui.def;

import de.jjl.wnw.desktop.gui.GUI;
import de.jjl.wnw.desktop.gui.JFXFrame;
import javafx.scene.Scene;
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
	public void setScene(JFXFrame root)
	{
		stage.setScene(new Scene(root));
	}
}
