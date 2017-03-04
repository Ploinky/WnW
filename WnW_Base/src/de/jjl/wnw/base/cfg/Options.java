package de.jjl.wnw.base.cfg;

import de.jjl.wnw.base.lang.Language;

public class Options
{
	private static Options instance;

	private Language lang;

	private Options()
	{
		lang = Language.LANG_ENGLISH;
	}

	public static Options get()
	{
		// TODO $Li Mar 4, 2017 Read options from file?!

		if (instance == null)
		{
			instance = new Options();
		}

		return instance;
	}

	public Language getLanguage()
	{
		return lang;
	}

	public void setLanguage(Language newLang)
	{
		lang = newLang;
	}
}
