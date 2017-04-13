package de.jjl.wnw.base.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import de.jjl.wnw.base.cfg.Settings;
import de.jjl.wnw.base.util.InvalidationListener;
import de.jjl.wnw.base.util.Observable;

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
		String s = "?" + key + "?";

		try
		{
			s = bundle.getString(key);
		}
		catch (MissingResourceException e)
		{
			// TODO $Li Mar 10, 2017 Does this need more handling?!
			System.err.println("Missing resource! Can't find translation for " + key + ".");
		}

		return s;
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
		for(InvalidationListener l : listeners)
		{
			l.invalidated(this);
		}
	}
}
