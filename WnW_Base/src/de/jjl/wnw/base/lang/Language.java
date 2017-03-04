package de.jjl.wnw.base.lang;

import java.util.Locale;

public enum Language
{
	LANG_ENGLISH(new Locale("en")), LANG_GERMAN(new Locale("de")), LANG_NORWEGIAN(new Locale("no"));

	private Locale locale;

	private Language(Locale locale)
	{
		this.locale = locale;
	}

	public Locale getLocale()
	{
		return locale;
	}

	@Override
	public String toString()
	{
		return Translator.get().translate(name());
	}
}
