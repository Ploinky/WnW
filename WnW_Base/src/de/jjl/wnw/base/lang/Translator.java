package de.jjl.wnw.base.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import de.jjl.wnw.base.cfg.Settings;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class Translator implements Observable
{
	private static Translator instance;

	private Locale currentLocale;

	private final List<InvalidationListener> listeners;

	private ResourceBundle bundle;

	public static Translator get()
	{
		if (instance == null)
		{
			instance = new Translator(Settings.get().getLanguage().getLocale());
		}

		return instance;
	}

	private Translator(Locale locale)
	{
		listeners = new ArrayList<>();
		changeLocale(locale);
	}

	public String translate(String key)
	{
		return bundle.getString(key);
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
		bundle = ResourceBundle.getBundle("de.jjl.wnw.base.lang.menu", currentLocale);
		invalidate();
	}

	private void invalidate()
	{
		listeners.forEach(l -> l.invalidated(this));
	}
}
