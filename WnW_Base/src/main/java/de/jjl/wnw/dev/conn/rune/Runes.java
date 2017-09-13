package de.jjl.wnw.dev.conn.rune;

import java.util.*;

import de.jjl.wnw.base.rune.WnWRune;

public class Runes
{
	private Map<Long, WnWRune> runeLongs = new HashMap<>();
	
	private Map<String, WnWRune> runeStrings = new HashMap<>();
	
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
		
		return instance.runeStrings.get(input);
	}
	
	public static void addRuneString(String input, WnWRune r)
	{
		if(instance == null)
		{
			instance = new Runes();
			LoadRunes.load();
		}
		
		if(instance.runeStrings.containsKey(input))
		{
			throw new RuntimeException("Duplicate rune for " + input);
		}
		
		instance.runeStrings.put(input, r);
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
