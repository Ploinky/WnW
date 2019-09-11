package de.jjl.wnw.dev.rune;

import java.util.*;

import de.jjl.wnw.base.rune.WnWRune;

public class Runes
{
	private Map<Long, WnWRune> runeLongs = new HashMap<>();
	
	private static Runes instance;
		
	private Runes()
	{
	}
	
	public static void init(RuneFactory factory)
	{
		if(instance != null)
		{
			return;
		}
		
		instance = new Runes();
		LoadRunes.load(factory);
	}

	public static WnWRune getRuneForLong(long rune)
	{
		if(instance == null)
		{
			init(new BaseRuneFactory("" + rune, rune));
			throw new IllegalStateException("Runes not initialised!");
		}
		
		return instance.runeLongs.get(rune);
	}
	
	public static WnWRune getRuneForString(String input)
	{
		if(instance == null)
		{
			throw new IllegalStateException("Runes not initialised!");
		}
		
		return instance.runeLongs.get(Long.valueOf(input));
	}
	
	public static void addRuneLong(long l, WnWRune r)
	{
		if(instance == null)
		{
			throw new IllegalStateException("Runes not initialised!");
		}
		
		instance.runeLongs.put(l, r);
	}
}
