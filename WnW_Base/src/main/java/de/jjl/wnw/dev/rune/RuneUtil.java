package de.jjl.wnw.dev.rune;

import java.util.HashMap;
import java.util.Map;

import de.jjl.wnw.base.rune.WnWRune;
import de.jjl.wnw.dev.game.Player;

public class RuneUtil
{
	private Map<Long, RuneFactory> runes;

	private static RuneUtil instance;

	private static RuneUtil getInstance()
	{
		if (instance == null)
		{
			instance = new RuneUtil();
		}

		return instance;
	}

	private RuneUtil()
	{
		runes = new HashMap<>();

		load();
	}

	private void load()
	{
		runes.put(753l, new BaseRuneFactory("753", 753));
		runes.put(159l, new BaseRuneFactory("159", 159));
		runes.put(14789l, new BaseRuneFactory("14789", 14789));
	}

	public static WnWRune getRune(Player player1, long rune)
	{
		return getInstance().runes.get(rune) != null ? getInstance().runes.get(rune).cre() : null;
	}
}
