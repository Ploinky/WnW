package de.jjl.wnw.base.lang;

import java.util.Locale;
import java.util.ResourceBundle;

public class Translator
{
	private static Translator instance;

	private static Locale currentLocale;

	public Translator get()
	{
		if (instance == null)
		{
			instance = new Translator(Locale.ENGLISH);
		}

		return instance;
	}

	public Translator(Locale locale)
	{
		currentLocale = locale;
	}

	public static String translate(String key)
	{
		ResourceBundle bundleMenu = ResourceBundle.getBundle("de.jjl.wnw.base.lang.menu", currentLocale);
		return bundleMenu.getString(key);
	}
}
