package de.jjl.wnw.desktop.gui.def;

import java.util.MissingResourceException;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.base.lang.Translator;
import javafx.scene.control.Button;

public class DefaultButton extends Button
{
	public DefaultButton()
	{
		this("");
	}

	public DefaultButton(String text)
	{
		super(text);

		try
		{
			setText(Translator.get().translate(text));
		}
		catch (MissingResourceException e)
		{
			// Translation does not exist - nothing to do
			// TODO $Li Mar 3, 2017 Logging!
			// TODO Remove if no longer needed
			System.err.println("No translation found for " + text + "!");
		}
		setFont(Const.FONT_DEFAULT);
		setPrefWidth(250);
	}
}
