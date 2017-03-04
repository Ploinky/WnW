package de.jjl.wnw.desktop.gui.def;

import java.util.Locale;

import de.jjl.wnw.base.lang.Translator;
import de.jjl.wnw.desktop.gui.JFXFrame;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
		stage.getIcons().add(new Image(getClass().getResourceAsStream("./../../../base/res/1-freepik.jpg")));
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
		stage.setTitle(Translator.get().translate(title));
	}

	@Override
	public void setScene(JFXFrame root)
	{
		listeners.forEach(root::addListener);
		stage.setScene(new Scene(root));
	}

	@Override
	public void setLocale(Locale l)
	{
		// TODO Auto-generated method stub

	}
}
