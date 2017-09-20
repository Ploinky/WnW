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
	
	public static WnWRune getRuneForLong(long rune)
	{
		if(instance == null)
		{
			instance = new Runes();
			LoadRunes.load();
		}
		
		return instance.runeLongs.get(rune);
	}
	
	public static WnWRune getRuneForString(String input)
	{
		if(instance == null)
		{
			instance = new Runes();
			LoadRunes.load();
		}

		return instance.runeLongs.get(Long.valueOf(input));
	}
	
	public static void addRuneLong(long l, WnWRune r)
	{
		if(instance == null)
		{
			instance = new Runes();
			LoadRunes.load();
		}
		
		if(instance.runeLongs.containsKey(l))
		{
			throw new RuntimeException("Duplicate rune for " + l);
		}
		
		instance.runeLongs.put(l, r);
	}
}
