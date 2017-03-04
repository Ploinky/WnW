package de.jjl.wnw.base.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import de.jjl.wnw.base.cfg.Options;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class Translator implements Observable
{
	private static Translator instance;

	private Locale currentLocale;

	private final List<InvalidationListener> listeners;

	public static Translator get()
	{
		if (instance == null)
		{
			instance = new Translator(Options.get().getLanguage().getLocale());
		}

		return instance;
	}

	private Translator(Locale locale)
	{
		currentLocale = locale;
		listeners = new ArrayList<>();
	}

	public String translate(String key)
	{
		ResourceBundle bundleMenu = ResourceBundle.getBundle("de.jjl.wnw.base.lang.menu", currentLocale);
		return bundleMenu.getString(key);
	}

	@Override
	public void addListener(InvalidationListener listener)
	{
		listeners.add(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener)
	{
		listeners.remove(listener);
	}

	public void changeLocale(Locale newLocale)
	{
		this.currentLocale = newLocale;
		invalidate();
	}

	private void invalidate()
	{
		listeners.forEach(l -> l.invalidated(this));
	}
}
