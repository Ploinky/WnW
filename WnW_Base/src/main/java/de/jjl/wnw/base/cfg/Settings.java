package de.jjl.wnw.base.cfg;

import de.jjl.wnw.base.lang.Language;
import javafx.scene.input.KeyCode;

public class Settings
{
	private static Settings instance;

	private Language lang;

	private Settings()
	{
		lang = Language.LANG_ENGLISH;
	}

	public static Settings get()
	{
		// TODO $Li Mar 4, 2017 Read options from file?!

		if (instance == null)
		{
			instance = new Settings();
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

	public static KeyCode getCastKey()
	{
		return KeyCode.SPACE;
	}

	public static KeyCode getShieldKey()
	{
		return KeyCode.CONTROL;
	}

	public static long getChatShowDuration()
	{
		return 3;
	}
}
