package de.jjl.wnw.dev.rune;

import java.util.*;
import java.util.function.Supplier;

import de.jjl.wnw.desktop.game.DesktopPlayer;

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
		runes.put(753l, new DesktopRuneFactory("753", 753));
		runes.put(159l, new DesktopRuneFactory("159", 159));
		runes.put(14789l, new DesktopRuneFactory("14789", 14789));
	}

	public static DesktopRune getRune(DesktopPlayer player, long rune)
	{
		return getInstance().runes.get(rune) != null ? getInstance().runes.get(rune).get() : null;
	}
}
