package de.jjl.wnw.desktop.gui.def;

import java.util.MissingResourceException;

import de.jjl.wnw.base.lang.Translator;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.Label;

public class DefaultLabel extends Label implements InvalidationListener
{
	private String text;

	public DefaultLabel()
	{
		this("");
	}

	public DefaultLabel(String text)
	{
		super(text);

		this.text = text;

		translateText();
	}

	@Override
	public void invalidated(Observable observable)
	{
		translateText();
	}

	private void translateText()
	{
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
	}
}
