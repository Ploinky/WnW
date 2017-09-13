package de.jjl.wnw.dev.conn.rune;

import java.util.*;

import de.jjl.wnw.base.rune.WnWRune;

public class Runes
{
	private static Map<Long, WnWRune> runes = new HashMap<>();
	
	private Runes()
	{
	}
	
	public static WnWRune getRuneFor(long rune)
	{
		return runes.get(rune);
	}
	
	public static void addRune(long l, WnWRune r)
	{
		if(runes.containsKey(l))
		{
			throw new RuntimeException("Duplicate rune for " + l);
		}
		
		runes.put(l, r);
	}
}
