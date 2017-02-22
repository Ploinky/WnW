package de.jjl.wnw.desktop.gui.def;

import de.jjl.wnw.desktop.gui.JFXFrame;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Default implementation of the {@code GUI}, written in JavaFX.
 *
 * @author johannes.litger
 */
public class DefaultGUI extends BaseGUI
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
		listeners.forEach(root::addListener);
		stage.setScene(new Scene(root));
	}
}
