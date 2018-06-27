package de.jjl.wnw.dev.rune;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DesktopRuneUtil
{
	private Map<Long, Supplier<DesktopRune>> runes;

	private static DesktopRuneUtil instance;

	private static DesktopRuneUtil getInstance()
	{
		if (instance == null)
		{
			instance = new DesktopRuneUtil();
		}

		return instance;
	}

	private DesktopRuneUtil()
	{
		runes = new HashMap<>();

		load();
	}

	private void load()
	{
		runes.put(753l, () -> new DesktopRune("753", 753, 1));
		runes.put(159l, () -> new DesktopRune("159", 159, 1));
		runes.put(14789l, () -> new DesktopRune("14789", 14789, 1));
	}

	public static DesktopRune getRune(long rune)
	{
		return getInstance().runes.get(rune) != null ? getInstance().runes.get(rune).get() : null;
	}
}
